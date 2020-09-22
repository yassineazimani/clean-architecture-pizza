package clean.architecture.pizza.adapters.secondaries.hibernate.repositories;

import clean.architecture.pizza.adapters.secondaries.hibernate.config.AbstractRepository;
import clean.architecture.pizza.adapters.secondaries.hibernate.entities.*;
import clean.architecture.pizza.adapters.secondaries.hibernate.mappers.OrderMapper;
import com.clean.architecture.pizza.core.enums.OrderStateEnum;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.OrderDTO;
import com.clean.architecture.pizza.core.ports.OrderRepository;

import java.util.Optional;

public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {

    @Override
    public Optional<OrderDTO> findById(int id) {
        Order order = this.entityManager.find(Order.class, id);
        if(order == null){
            return Optional.empty();
        }
        OrderDTO orderDTO = OrderMapper.INSTANCE.toDto(order);
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
        order.getProducts()
                .stream()
                .forEach(product -> {
                    OrderHasProducts ohp = new OrderHasProducts(new OrderHasProductsId(order.getId(), product.getId()));
                    ohp.setQuantity(product.getQuantityOrdered());
                    super.entityManager.merge(ohp);
                });
        return order;
    }// update()

}// OrderRepositoryImpl
