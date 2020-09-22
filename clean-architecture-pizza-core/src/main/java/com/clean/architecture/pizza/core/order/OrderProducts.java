package com.clean.architecture.pizza.core.order;

import com.clean.architecture.pizza.core.enums.MoneyEnum;
import com.clean.architecture.pizza.core.enums.OrderStateEnum;
import com.clean.architecture.pizza.core.exceptions.*;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * Cette classe contient tous les cas d'utilisation
 * permettant de gérer une commande de produits.
 */
public class OrderProducts {

    @Getter
    private Set<ProductDTO> eCommerceCart;

    private OrderDTO currentOrder;

    private OrderRepository orderRepository;

    private ProductRepository productRepository;

    private CoinsInfrastructure coinsInfrastructure;

    private static final Logger LOGGER = LogManager.getLogger(OrderProducts.class);

    public OrderProducts(OrderRepository orderRepository,
                         ProductRepository productRepository,
                         CoinsInfrastructure coinsInfrastructure) {
        this.eCommerceCart = new HashSet<>();
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
        try {
            this.orderRepository.begin();
            checkQuantityAvailableForProductOrdered(product.getId(), product.getQuantityOrdered());
            double totalPriceOrdered = product.getPrice() * product.getQuantityOrdered();
            if (!this.eCommerceCart.contains(product)) {
                this.eCommerceCart.add(product);
            } else {
                this.eCommerceCart = this.eCommerceCart.stream().peek(
                        p -> {
                            if (p.getId().equals(product.getId())) {
                                p.setQuantityOrdered(p.getQuantityOrdered() + product.getQuantityOrdered());
                            }
                        }
                ).collect(Collectors.toSet());
            }
            currentOrder.setTotal(currentOrder.getTotal() + totalPriceOrdered);
            currentOrder.setProducts(new ArrayList<>(this.eCommerceCart));
            if (currentOrder.getId() == null) {
                currentOrder = orderRepository.save(currentOrder);
            } else {
                orderRepository.update(currentOrder);
            }
            this.orderRepository.commit();
        }catch(TransactionException te){
            this.orderRepository.rollback();
            throw new OrderException("Technical error : Impossible to order");
        }
    }// addProduct()

    private void checkQuantityAvailableForProductOrdered(int productId, int quantity) throws OrderException{
        Optional<ProductDTO> optProduct = this.productRepository.findById(productId);
        if(optProduct.isPresent()){
            ProductDTO p = optProduct.get();
            if(p.getQuantityAvailable() < quantity){
                throw new OrderException("The quantity asked is greater than the quantity available");
            }
        }else{
            LOGGER.error("Impossible to check quantity available for product with id = " + productId);
        }
    }// checkQuantityAvailableForProductsOrdered()

    public void removeProduct(int id) throws OrderException {
        try {
            this.orderRepository.begin();
            boolean result = this.eCommerceCart.removeIf(product -> product.getId().equals(id));
            if (!result) {
                throw new OrderException("Product with id " + id + " doesn't exist");
            }
            this.orderRepository.commit();
        }catch(TransactionException te){
            this.orderRepository.rollback();
            throw new OrderException("Technical error : Impossible to remove product from order");
        }
    }// removeProduct()

    public void cancelOrder() throws OrderException, DatabaseException, ArgumentMissingException {
        try {
            this.orderRepository.begin();
            currentOrder.setOrderState(OrderStateEnum.CANCELLED);
            if(currentOrder.getId() == null){
                orderRepository.save(currentOrder);
            }else{
                orderRepository.update(currentOrder);
            }
            this.eCommerceCart.clear();
            this.currentOrder = new OrderDTO();
            this.orderRepository.commit();
        }catch(TransactionException te){
            this.orderRepository.rollback();
            throw new OrderException("Technical error : Impossible to cancel order");
        }
    }// cancelOrder()

    public double getTotal(){
        double total = 0.;
        if(!CollectionUtils.isEmpty(this.eCommerceCart)){
            total = this.eCommerceCart.stream()
                    .map(product -> product.getPrice() * product.getQuantityOrdered())
                    .reduce(0., Double::sum);
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
        try {
            productRepository.begin();
            if (CollectionUtils.isEmpty(this.eCommerceCart)) {
                throw new OrderException("Impossible to update quantity available for products");
            }
            Map<Integer, List<ProductDTO>> productsById = this.eCommerceCart.stream()
                    .collect(Collectors.groupingBy(ProductDTO::getId));
            productsById.forEach((productId, products) -> productRepository.findById(productId)
                    .ifPresent(product -> {
                        product.setQuantityAvailable(product.getQuantityAvailable() - products.size());
                        try {
                            productRepository.update(product);
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                    }));
            productRepository.commit();
        }catch(TransactionException te){
            productRepository.rollback();
        }
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
        try {
            this.orderRepository.begin();
            this.finalizeOrder(currentOrder.getId(), moneyEnum, transactionCBId);
            this.clearECommerceCart();
            this.currentOrder = new OrderDTO();
            this.orderRepository.commit();
        }catch(TransactionException te){
            this.orderRepository.rollback();
            throw new OrderException("Impossible to reduce quantity available");
        }
    }// paymentOrder

    private void finalizeOrder(int orderId, MoneyEnum moneyEnum, String transactionCBId) throws OrderException, DatabaseException, ArgumentMissingException {
        OrderDTO order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Impossible to finalize order because it's impossible to find order with id " + orderId));
        order.setOrderState(OrderStateEnum.SUCCESS);
        order.setProducts(new ArrayList<>(this.eCommerceCart));
        if(moneyEnum == MoneyEnum.CB){
            if(StringUtils.isEmpty(transactionCBId)){
                throw new OrderException("Impossible to finalize order for CB payment");
            }
            order.setTransactionCBId(transactionCBId);
        }
        orderRepository.update(order);
        this.reduceQuantityAvailable();
    }// finalizeOrder()

}// CommandProducts
