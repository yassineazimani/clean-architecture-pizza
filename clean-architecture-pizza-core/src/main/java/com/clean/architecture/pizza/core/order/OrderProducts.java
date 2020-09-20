package com.clean.architecture.pizza.core.order;

import com.clean.architecture.pizza.core.enums.MoneyEnum;
import com.clean.architecture.pizza.core.enums.OrderStateEnum;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.CoinsInfrastructureException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.exceptions.OrderException;
import com.clean.architecture.pizza.core.model.OrderDTO;
import com.clean.architecture.pizza.core.model.ProductDTO;
import com.clean.architecture.pizza.core.ports.CoinsInfrastructure;
import com.clean.architecture.pizza.core.ports.OrderRepository;
import com.clean.architecture.pizza.core.ports.ProductRepository;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Cette classe contient tous les cas d'utilisation
 * permettant de gérer une commande de produits.
 */
public class OrderProducts {

    @Getter
    private final List<ProductDTO> eCommerceCart;

    private OrderDTO currentOrder;

    private OrderRepository orderRepository;

    private ProductRepository productRepository;

    private CoinsInfrastructure coinsInfrastructure;

    private static final Logger LOGGER = LogManager.getLogger(OrderProducts.class);

    public OrderProducts(OrderRepository orderRepository,
                         ProductRepository productRepository,
                         CoinsInfrastructure coinsInfrastructure) {
        this.eCommerceCart = new ArrayList<>();
        this.currentOrder = new OrderDTO();
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.coinsInfrastructure = coinsInfrastructure;
    }// CommandProducts()

    /**
     * Ajoute un produit dans le panier.
     * @param product Produit
     * @throws ArgumentMissingException Exception se produisant si le produit est null
     */
    public void addProduct(ProductDTO product) throws ArgumentMissingException, OrderException, DatabaseException {
        if(product == null){
            throw new ArgumentMissingException("Product argument is null");
        }
        this.eCommerceCart.add(product);
        if(currentOrder.getId() == null){
            currentOrder.setTotal(currentOrder.getTotal() + product.getPrice());
            currentOrder = orderRepository.save(currentOrder);
        }else{
            this.updateTotalOrder(currentOrder.getId(), product.getPrice());
        }
    }// addProduct()

    public void removeProduct(int id) throws OrderException {
        boolean result = this.eCommerceCart.removeIf(product -> product.getId().equals(id));
        if(!result){
            throw new OrderException("Product with id " + id + " doesn't exist");
        }
    }// removeProduct()

    public void cancelOrder() throws OrderException, DatabaseException, ArgumentMissingException {
        if(currentOrder.getId() == null){
            currentOrder.setOrderState(OrderStateEnum.CANCELLED);
            orderRepository.save(currentOrder);
        }else{
            this.updateStateOrder(currentOrder.getId(), OrderStateEnum.CANCELLED);
        }
        this.eCommerceCart.clear();
        this.currentOrder = new OrderDTO();
    }// cancelOrder()

    public double getTotal(){
        double total = 0.;
        if(!CollectionUtils.isEmpty(this.eCommerceCart)){
            total = this.eCommerceCart.stream()
                    .map(product -> product.getPrice())
                    .reduce(0., (p1, p2) -> p1 + p2);
        }
        return total;
    }// getTotal()

    public void clearECommerceCart(){
        this.eCommerceCart.clear();
    }// clearECommerceCart()

    /**
     * Lorsqu'une commande est finalisée, il est nécessaire de réduire
     * le nombre de produits restants.
     * @throws OrderException
     */
    public void reduceQuantityAvailable() throws OrderException{
        if(CollectionUtils.isEmpty(this.eCommerceCart)){
            throw new OrderException("Impossible to update quantity available for products");
        }
        Map<Integer, List<ProductDTO>> productsById = this.eCommerceCart.stream()
                .collect(Collectors.groupingBy(ProductDTO::getId));
        productsById.forEach((productId, products) -> {
            productRepository.findById(productId)
                    .ifPresent(product -> {
                        product.setQuantityAvailable(product.getQuantityAvailable() - products.size());
                        try {
                            productRepository.update(product);
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                    });
        });
    }// reduceQuantityAvailable()

    public void paymentOrder(MoneyEnum moneyEnum) throws OrderException, DatabaseException, ArgumentMissingException {
        this.paymentOrder(moneyEnum, null);
    }// paymentOrder()

    public void paymentOrder(MoneyEnum moneyEnum, String transactionCBId) throws OrderException, DatabaseException, ArgumentMissingException {
        if(CollectionUtils.isEmpty(this.eCommerceCart)){
            throw new OrderException("ECommerce cart is empty");
        }
        if(moneyEnum == null){
            throw new OrderException("MoneyEnum is null");
        }
        if(moneyEnum == MoneyEnum.COINS){
            try {
                coinsInfrastructure.takeMoney(this.getTotal());
            }catch(CoinsInfrastructureException cie){
                LOGGER.error(cie.getMessage(), cie);
            }
        }
        this.finalizeOrder(currentOrder.getId(), moneyEnum, transactionCBId);
        this.clearECommerceCart();
        this.currentOrder = new OrderDTO();
    }// paymentOrder

    private void updateTotalOrder(int orderId, double price) throws OrderException, DatabaseException, ArgumentMissingException {
        OrderDTO order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Impossible to update order because it's impossible to find order with id " + orderId));
        order.setTotal(order.getTotal() + price);
        orderRepository.update(order);
    }// updateTotalOrder()

    private void updateStateOrder(int orderId, OrderStateEnum state) throws OrderException, DatabaseException, ArgumentMissingException {
        OrderDTO order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Impossible to update order because it's impossible to find order with id " + orderId));
        order.setOrderState(state);
        orderRepository.update(order);
    }// updateStateOrder()

    private void finalizeOrder(int orderId, MoneyEnum moneyEnum, String transactionCBId) throws OrderException, DatabaseException, ArgumentMissingException {
        OrderDTO order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Impossible to finalize order because it's impossible to find order with id " + orderId));
        order.setOrderState(OrderStateEnum.SUCCESS);
        order.setProducts(this.eCommerceCart);
        if(moneyEnum != null && moneyEnum == MoneyEnum.CB){
            if(StringUtils.isEmpty(transactionCBId)){
                throw new OrderException("Impossible to finalize order for CB payment");
            }
            order.setTransactionCBId(transactionCBId);
        }
        orderRepository.update(order);
        this.reduceQuantityAvailable();
    }// finalizeOrder()

}// CommandProducts
