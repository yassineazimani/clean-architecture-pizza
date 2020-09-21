package clean.architecture.pizza.adapters.secondaries.hibernate.repositories;

import clean.architecture.pizza.adapters.secondaries.hibernate.config.AbstractRepository;
import clean.architecture.pizza.adapters.secondaries.hibernate.entities.Category;
import clean.architecture.pizza.adapters.secondaries.hibernate.entities.Order;
import clean.architecture.pizza.adapters.secondaries.hibernate.entities.OrderState;
import clean.architecture.pizza.adapters.secondaries.hibernate.entities.Product;
import clean.architecture.pizza.adapters.secondaries.hibernate.mappers.OrderMapper;
import com.clean.architecture.pizza.core.enums.OrderStateEnum;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.OrderDTO;
import com.clean.architecture.pizza.core.ports.OrderRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return OrderMapper.INSTANCE.toDto(entity);
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
        List<Product> productList = this.entityManager.createQuery("SELECT p FROM Product p WHERE id in :ids", Product.class)
                .setParameter("ids", order.getProducts().stream().map(p -> p.getId()).collect(Collectors.toList()))
                .getResultList();
        o.setProducts(new HashSet<>(productList));
        super.update(o);
        return order;
    }// update()

}// OrderRepositoryImpl
