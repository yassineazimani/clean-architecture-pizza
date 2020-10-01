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
package repositories;

import com.clean.architecture.pizza.core.enums.OrderStateEnum;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.exceptions.TransactionException;
import com.clean.architecture.pizza.core.model.OrderDTO;
import com.clean.architecture.pizza.core.model.ProductDTO;
import com.clean.architecture.pizza.core.model.StatsSumOrderTotalByProductsDTO;
import com.clean.architecture.pizza.core.ports.OrderRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.DataBaseHelper;
import utils.MappingEnum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class OrderRepositoryImpl implements OrderRepository {

    private String pathOrderDbFile;

    private ProductRepositoryImpl productRepository;

    private SimpleDateFormat sdf;

    private static final Logger LOGGER = LogManager.getLogger(OrderRepositoryImpl.class);

    public OrderRepositoryImpl() {
        try {
            Properties properties = new DataBaseHelper().getProperties();
            this.pathOrderDbFile = properties.getProperty("path.order.db");
            this.productRepository = new ProductRepositoryImpl();
            this.sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }// OrderRepositoryImpl()

    @Override
    public Optional<OrderDTO> findById(int id) {
        try (Scanner scanner = new Scanner(new File(this.pathOrderDbFile))) {
            final Map<String, Integer> columns = DataBaseHelper.parseHead(scanner);
            while (scanner.hasNext()) {
                List<String> row = DataBaseHelper.parseRow(scanner);
                String columnValue = row.get(columns.get(MappingEnum.ID.getName()));
                if (String.valueOf(id).equals(columnValue)) {
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setId(id);
                    try {
                        orderDTO.setOrderDate(this.sdf.parse(row.get(columns.get(MappingEnum.ORDER_DATE.getName()))));
                    } catch (ParseException pe) {
                        LOGGER.error(pe.getMessage(), pe);
                    }
                    orderDTO.setOrderState(OrderStateEnum.valueOf(Integer.parseInt(row.get(columns.get(MappingEnum.ORDER_STATE.getName())))));
                    orderDTO.setTotal(Double.parseDouble(row.get(columns.get(MappingEnum.TOTAL.getName()))));
                    String tx = row.get(columns.get(MappingEnum.TRANSACTION_CB_ID.getName()));
                    orderDTO.setTransactionCBId("NULL".equalsIgnoreCase(tx) ? null : tx);

                    String productsFromOrder = row.get(columns.get(MappingEnum.PRODUCTS_OF_ORDER.getName()));
                    List<String> productsIds = Arrays.asList(productsFromOrder.split(DataBaseHelper.SEPARATOR_PRODUCTS_ORDER));
                    List<ProductDTO> products = productsIds.stream()
                            .map(coupleProduct -> {
                                String[] tmp = coupleProduct.split(DataBaseHelper.SEPARATOR_PRODUCTS_QUANTITY_ORDER);
                                if (StringUtils.isEmpty(tmp[0])) return null;
                                 ProductDTO p = this.productRepository
                                        .findById(Integer.parseInt(tmp[0]))
                                        .orElse(null);
                                p.setQuantityOrdered(StringUtils.isEmpty(tmp[1]) ? 0 : Integer.parseInt(tmp[1]));
                                return p;
                            }).filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    orderDTO.setProducts(products);

                    return Optional.of(orderDTO);
                }
            }
            return Optional.empty();
        } catch (FileNotFoundException e) {
            LOGGER.error("File {} doesn't exist", DataBaseHelper.DB_FILE, e);
            return Optional.empty(); // Dans la pratique, on remonterait l'exception avec DataBaseException
        }
    }// findById()

    @Override
    public OrderDTO save(OrderDTO order) throws DatabaseException, ArgumentMissingException {
        if (order == null) {
            throw new ArgumentMissingException("Order is null");
        }
        List<String> lines;
        try {
            File file = new File(this.pathOrderDbFile);
            lines = FileUtils.readLines(file, StandardCharsets.UTF_8.name());
            final Map<String, Integer> columns = DataBaseHelper.parseHead(lines.get(0));
            final Map<Integer, String> idxToColumns = DataBaseHelper.inverseMappingHeader(columns);

            StringBuilder sb = new StringBuilder();
            List<Integer> indexes = new ArrayList<>(columns.values());
            String idGenerated = DataBaseHelper.generateId(lines, columns);

            indexes.stream()
                    .sorted(Integer::compare)
                    .forEach(idx -> {
                        if (MappingEnum.ID.getName().equals(idxToColumns.get(idx))) {
                            sb.append(idGenerated);
                            sb.append(DataBaseHelper.SEPARATOR_CSV);
                        } else if (MappingEnum.ORDER_DATE.getName().equals(idxToColumns.get(idx))) {
                            sb.append(this.sdf.format(order.getOrderDate()));
                            sb.append(DataBaseHelper.SEPARATOR_CSV);
                        } else if (MappingEnum.ORDER_STATE.getName().equals(idxToColumns.get(idx))) {
                            sb.append(order.getOrderState().ordinal() + 1);
                            sb.append(DataBaseHelper.SEPARATOR_CSV);
                        } else if (MappingEnum.TOTAL.getName().equals(idxToColumns.get(idx))) {
                            sb.append(order.getTotal());
                            sb.append(DataBaseHelper.SEPARATOR_CSV);
                        } else if (MappingEnum.TRANSACTION_CB_ID.getName().equals(idxToColumns.get(idx))) {
                            sb.append(order.getTransactionCBId());
                            sb.append(DataBaseHelper.SEPARATOR_CSV);
                        } else if (MappingEnum.PRODUCTS_OF_ORDER.getName().equals(idxToColumns.get(idx))) {
                            List<String> productsIds = order.getProducts()
                                    .stream()
                                    .map(product -> product.getId() + DataBaseHelper.SEPARATOR_PRODUCTS_QUANTITY_ORDER + product.getQuantityOrdered())
                                    .collect(Collectors.toList());
                            sb.append(String.join(DataBaseHelper.SEPARATOR_PRODUCTS_ORDER, productsIds));
                            sb.append(DataBaseHelper.SEPARATOR_CSV);
                        }
                    });

            String tmp = sb.toString();
            String lineToAdd = tmp.substring(0, tmp.length() - 1);

            lines.add(lineToAdd);
            FileUtils.writeLines(file, lines, false);
            order.setId(Integer.valueOf(idGenerated));
        } catch (IOException e) {
            LOGGER.error("Error with File {}", DataBaseHelper.DB_FILE, e);
            throw new DatabaseException("Impossible to save order");
        }
        return order;
    }// save()

    @Override
    public OrderDTO update(OrderDTO order) throws DatabaseException, ArgumentMissingException {
        if (order == null) {
            throw new ArgumentMissingException("Order is null");
        }
        List<String> lines;
        try {
            File file = new File(this.pathOrderDbFile);
            lines = FileUtils.readLines(file, StandardCharsets.UTF_8.name());
            final Map<String, Integer> columns = DataBaseHelper.parseHead(lines.get(0));
            final Map<Integer, String> idxToColumns = DataBaseHelper.inverseMappingHeader(columns);
            List<String> updatedLines = lines.
                    stream()
                    .map(value -> {
                        String[] rowSplitted = value.split(DataBaseHelper.SEPARATOR_CSV);
                        if (rowSplitted[columns.get(MappingEnum.ID.getName())].equals(String.valueOf(order.getId()))) {
                            StringBuilder sb = new StringBuilder();
                            List<Integer> indexes = new ArrayList<>(columns.values());
                            indexes.stream()
                                    .sorted(Integer::compare)
                                    .forEach(idx -> {
                                        if (MappingEnum.ID.getName().equals(idxToColumns.get(idx))) {
                                            sb.append(order.getId());
                                            sb.append(DataBaseHelper.SEPARATOR_CSV);
                                        } else if (MappingEnum.ORDER_DATE.getName().equals(idxToColumns.get(idx))) {
                                            sb.append(this.sdf.format(order.getOrderDate()));
                                            sb.append(DataBaseHelper.SEPARATOR_CSV);
                                        } else if (MappingEnum.ORDER_STATE.getName().equals(idxToColumns.get(idx))) {
                                            sb.append(order.getOrderState().ordinal() + 1);
                                            sb.append(DataBaseHelper.SEPARATOR_CSV);
                                        } else if (MappingEnum.TOTAL.getName().equals(idxToColumns.get(idx))) {
                                            sb.append(order.getTotal());
                                            sb.append(DataBaseHelper.SEPARATOR_CSV);
                                        } else if (MappingEnum.TRANSACTION_CB_ID.getName().equals(idxToColumns.get(idx))) {
                                            sb.append(order.getTransactionCBId());
                                            sb.append(DataBaseHelper.SEPARATOR_CSV);
                                        } else if (MappingEnum.PRODUCTS_OF_ORDER.getName().equals(idxToColumns.get(idx))) {
                                            List<String> productsIds = order.getProducts()
                                                    .stream()
                                                    .map(product -> product.getId() + DataBaseHelper.SEPARATOR_PRODUCTS_QUANTITY_ORDER + product.getQuantityOrdered())
                                                    .collect(Collectors.toList());
                                            sb.append(String.join(DataBaseHelper.SEPARATOR_PRODUCTS_ORDER, productsIds));
                                            sb.append(DataBaseHelper.SEPARATOR_CSV);
                                        }
                                    });
                            String result = sb.toString();
                            return result.substring(0, result.length() - 1);
                        }
                        return value;
                    })
                    .collect(Collectors.toList());
            FileUtils.writeLines(file, updatedLines, false);
        } catch (IOException e) {
            LOGGER.error("Error with File {}", DataBaseHelper.DB_FILE, e);
            throw new DatabaseException("Impossible to update order");
        }
        return order;
    }// update()

    @Override
    public List<StatsSumOrderTotalByProductsDTO> getTotalSumByProducts() {
        List<OrderDTO> orders = this.findAll();
        Map<String, Double> sums = new HashMap<>();
        if (orders != null && !orders.isEmpty()){
            for (OrderDTO order : orders) {
                double total = order.getTotal();
                for (ProductDTO product : order.getProducts()) {
                    if (sums.containsKey(product.getName())) {
                        sums.put(product.getName(), sums.get(product.getName()) + total);
                    } else {
                        sums.put(product.getName(), total);
                    }
                }
            }
        }
        List<StatsSumOrderTotalByProductsDTO> stats = new ArrayList<>();
        if(!sums.isEmpty()){
            sums.forEach((productName, total) -> stats.add(new StatsSumOrderTotalByProductsDTO(total, productName)));
        }
        return stats;
    }// getTotalSumByProducts()

    private List<OrderDTO> findAll() {
        List<OrderDTO> results = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(this.pathOrderDbFile))) {
            final Map<String, Integer> columns = DataBaseHelper.parseHead(scanner);
            while (scanner.hasNext()) {
                List<String> row = DataBaseHelper.parseRow(scanner);
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setId(Integer.valueOf(row.get(columns.get(MappingEnum.ID.getName()))));
                try {
                    orderDTO.setOrderDate(this.sdf.parse(row.get(columns.get(MappingEnum.ORDER_DATE.getName()))));
                } catch (ParseException pe) {
                    LOGGER.error(pe.getMessage(), pe);
                }
                orderDTO.setOrderState(OrderStateEnum.valueOf(Integer.parseInt(row.get(columns.get(MappingEnum.ORDER_STATE.getName())))));
                orderDTO.setTotal(Double.parseDouble(row.get(columns.get(MappingEnum.TOTAL.getName()))));
                orderDTO.setTransactionCBId(row.get(columns.get(MappingEnum.TRANSACTION_CB_ID.getName())));

                String productsFromOrder = row.get(columns.get(MappingEnum.PRODUCTS_OF_ORDER.getName()));
                List<String> productsIds = Arrays.asList(productsFromOrder.split(DataBaseHelper.SEPARATOR_PRODUCTS_ORDER));
                List<ProductDTO> products = productsIds.stream()
                        .map(coupleProduct -> {
                            String[] tmp = coupleProduct.split(DataBaseHelper.SEPARATOR_PRODUCTS_QUANTITY_ORDER);
                            if (StringUtils.isEmpty(tmp[0])) return null;
                            ProductDTO p = this.productRepository
                                    .findById(Integer.parseInt(tmp[0]))
                                    .orElse(null);
                            p.setQuantityOrdered(StringUtils.isEmpty(tmp[1]) ? 0 : Integer.parseInt(tmp[1]));
                            return p;
                        }).filter(Objects::nonNull)
                        .collect(Collectors.toList());
                orderDTO.setProducts(products);

                results.add(orderDTO);

            }
            return results;
        } catch (FileNotFoundException e) {
            LOGGER.error("File {} doesn't exist", DataBaseHelper.DB_FILE, e);
            return results; // Dans la pratique, on remonterait l'exception avec DataBaseException
        }
    }// findAll()

    @Override
    public void begin() {
    }

    @Override
    public void commit() throws TransactionException {
    }

    @Override
    public void rollback() {}

}// OrderRepositoryImpl
