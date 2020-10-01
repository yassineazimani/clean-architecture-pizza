/*
 * Copyright 2020 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package clean.architecture.pizza.adapters.secondaries.hibernate.repositories;

import clean.architecture.pizza.adapters.secondaries.hibernate.config.AbstractRepository;
import clean.architecture.pizza.adapters.secondaries.hibernate.entities.*;
import clean.architecture.pizza.adapters.secondaries.hibernate.mappers.OrderMapper;
import clean.architecture.pizza.adapters.secondaries.hibernate.mappers.ProductMapper;
import clean.architecture.pizza.adapters.secondaries.hibernate.projections.ProductQuantityProjection;
import com.clean.architecture.pizza.core.enums.OrderStateEnum;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.OrderDTO;
import com.clean.architecture.pizza.core.model.ProductDTO;
import com.clean.architecture.pizza.core.model.StatsSumOrderTotalByProductsDTO;
import com.clean.architecture.pizza.core.ports.OrderRepository;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {

    @Override
    public Optional<OrderDTO> findById(int id) {
        Order order = this.entityManager.find(Order.class, id);
        if(order == null){
            return Optional.empty();
        }
        try {
            this.entityManager.refresh(order);
        }catch(EntityNotFoundException e){
            return Optional.empty();
        }
        List<ProductQuantityProjection> projections = this.entityManager.createQuery("SELECT new clean.architecture.pizza.adapters.secondaries.hibernate.projections.ProductQuantityProjection(ohp.id.productid, ohp.quantity) FROM OrderHasProducts ohp WHERE ohp.id.orderid = :orderid", ProductQuantityProjection.class)
                .setParameter("orderid", id)
                .getResultList();
        List<Integer> productsIds = projections.stream().map(p -> p.getProductId()).collect(Collectors.toList());
        List<ProductDTO> products = this.entityManager.createQuery("SELECT p FROM Product p WHERE p.id in :ids", Product.class)
                .setParameter("ids", productsIds)
                .getResultStream()
                .map(product -> {
                    ProductDTO productDTO = ProductMapper.INSTANCE.toDto(product);
                    Optional<ProductQuantityProjection> opt = projections.stream()
                            .filter(proj -> proj.getProductId() == product.getId())
                            .findAny();
                    if(opt.isPresent()){
                        productDTO.setQuantityOrdered(opt.get().getQuantity());
                        return productDTO;
                    }else{
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        OrderDTO orderDTO = OrderMapper.INSTANCE.toDto(order);
        orderDTO.setProducts(products);
        orderDTO.setOrderState(OrderStateEnum.valueOf(order.getOrderState().getId()));
        return Optional.of(orderDTO);
    }// findById()

    @Override
    public OrderDTO save(OrderDTO order) throws ArgumentMissingException, DatabaseException {
        if(order == null){
            throw new ArgumentMissingException("Order is null");
        }
        Order entity = OrderMapper.INSTANCE.toEntity(order);
        OrderState state = new OrderState();
        state.setId(order.getOrderState().ordinal() + 1);
        state.setName(order.getOrderState().toString());
        entity.setOrderState(state);
        super.save(entity);
        order.getProducts()
                .stream()
                .forEach(product -> {
                    OrderHasProducts ohp = new OrderHasProducts(new OrderHasProductsId(entity.getId(), product.getId()));
                    ohp.setQuantity(product.getQuantityOrdered());
                    super.entityManager.persist(ohp);
                    super.entityManager.flush();
                });
        OrderDTO orderPersisted = OrderMapper.INSTANCE.toDto(entity);
        orderPersisted.setProducts(order.getProducts());
        return orderPersisted;
    }// save()

    @Override
    public OrderDTO update(OrderDTO order) throws ArgumentMissingException, DatabaseException {
        if(order == null){
            throw new ArgumentMissingException("Order is null");
        }
        Order o = this.entityManager.find(Order.class, order.getId());
        o.setOrderDate(order.getOrderDate());
        o.setTotal(order.getTotal());
        o.setTransactionCBId(order.getTransactionCBId());
        OrderState state = this.entityManager.find(OrderState.class, order.getOrderState().ordinal() + 1);
        o.setOrderState(state);
        super.update(o);
        List<OrderHasProducts> listOrderHasProducts = this.entityManager.createQuery("SELECT ohp FROM OrderHasProducts ohp WHERE ohp.id.orderid = :orderid")
                .setParameter("orderid", order.getId())
                .getResultList();
        listOrderHasProducts.forEach(ohp -> super.entityManager.remove(ohp));
        order.getProducts()
                .stream()
                .forEach(product -> {
                    OrderHasProducts ohp = new OrderHasProducts(new OrderHasProductsId(order.getId(), product.getId()));
                    ohp.setQuantity(product.getQuantityOrdered());
                    super.entityManager.persist(ohp);
                });
        return order;
    }// update()

    @Override
    public List<StatsSumOrderTotalByProductsDTO> getTotalSumByProducts() {
        List<Object[]> results = this.entityManager
                .createNativeQuery("select sum(total) as total, p.name from ordercmd o inner join order_has_products ohp on o.id = ohp.orderid inner join product p on ohp.productid = p.id group by p.name order by total")
                .getResultList();
        return results.stream()
                .map(result -> new StatsSumOrderTotalByProductsDTO(((BigDecimal) result[0]).doubleValue(), String.valueOf(result[1])))
                .collect(Collectors.toList());
    }// getTotalSumByProducts()

}// OrderRepositoryImpl
