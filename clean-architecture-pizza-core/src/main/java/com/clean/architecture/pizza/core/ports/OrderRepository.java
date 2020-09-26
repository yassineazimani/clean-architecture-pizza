package com.clean.architecture.pizza.core.ports;

import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.OrderDTO;
import com.clean.architecture.pizza.core.model.StatsSumOrderTotalByProductsDTO;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends ManagementTransaction{

    Optional<OrderDTO> findById(int id);

    OrderDTO save(OrderDTO order) throws DatabaseException, ArgumentMissingException;

    OrderDTO update(OrderDTO order) throws DatabaseException, ArgumentMissingException;

    List<StatsSumOrderTotalByProductsDTO> getTotalSumByProducts();

}// OrderRepository
