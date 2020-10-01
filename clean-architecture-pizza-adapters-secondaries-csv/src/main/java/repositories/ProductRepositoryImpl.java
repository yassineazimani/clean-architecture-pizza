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

import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.exceptions.TransactionException;
import com.clean.architecture.pizza.core.model.ProductDTO;
import com.clean.architecture.pizza.core.ports.ProductRepository;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.DataBaseHelper;
import utils.MappingEnum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepository {

    private String pathProductDbFile;

    private CategoryRepositoryImpl categoryRepository;

    private final static Logger LOGGER = LogManager.getLogger(ProductRepositoryImpl.class);

    public ProductRepositoryImpl() {
        try {
            Properties properties = new DataBaseHelper().getProperties();
            this.pathProductDbFile = properties.getProperty("path.product.db");
            this.categoryRepository = new CategoryRepositoryImpl();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }// ProductRepositoryImpl()

    @Override
    public List<ProductDTO> findAllProducts() {
        List<ProductDTO> products = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(this.pathProductDbFile))) {
            final Map<String, Integer> columns = DataBaseHelper.parseHead(scanner);
            while (scanner.hasNext()) {
                List<String> row = DataBaseHelper.parseRow(scanner);
                ProductDTO productDTO = new ProductDTO(
                        Integer.valueOf(row.get(columns.get(MappingEnum.ID.getName()))),
                        row.get(columns.get(MappingEnum.NAME.getName())),
                        row.get(columns.get(MappingEnum.DESC.getName())),
                        Double.valueOf(row.get(columns.get(MappingEnum.PRICE.getName()))),
                        Integer.parseInt(row.get(columns.get(MappingEnum.QUANTITY_AVAILABLE.getName()))),
                        null,
                        0
                );
                int categoryId = Integer.parseInt(row.get(columns.get(MappingEnum.CATEGORY_ID.getName())));
                categoryRepository.findById(categoryId)
                        .ifPresent(productDTO::setCategory);
                products.add(productDTO);
            }
            return products;
        } catch (FileNotFoundException e) {
            LOGGER.error("File {} doesn't exist", DataBaseHelper.DB_FILE, e);
            return products; // Dans la pratique, on remonterait l'exception avec DataBaseException
        }
    }// findAllProducts()

    @Override
    public Optional<ProductDTO> findById(int id) {
        try (Scanner scanner = new Scanner(new File(this.pathProductDbFile))) {
            final Map<String, Integer> columns = DataBaseHelper.parseHead(scanner);
            while (scanner.hasNext()) {
                List<String> row = DataBaseHelper.parseRow(scanner);
                String columnValue = row.get(columns.get(MappingEnum.ID.getName()));
                if (String.valueOf(id).equals(columnValue)) {
                    ProductDTO productDTO = new ProductDTO(
                            Integer.valueOf(row.get(columns.get(MappingEnum.ID.getName()))),
                            row.get(columns.get(MappingEnum.NAME.getName())),
                            row.get(columns.get(MappingEnum.DESC.getName())),
                            Double.valueOf(row.get(columns.get(MappingEnum.PRICE.getName()))),
                            Integer.parseInt(row.get(columns.get(MappingEnum.QUANTITY_AVAILABLE.getName()))),
                            null,
                            0
                    );
                    int categoryId = Integer.parseInt(row.get(columns.get(MappingEnum.CATEGORY_ID.getName())));
                    categoryRepository.findById(categoryId)
                            .ifPresent(productDTO::setCategory);
                    return Optional.of(productDTO);
                }
            }
            return Optional.empty();
        } catch (FileNotFoundException e) {
            LOGGER.error("File {} doesn't exist", DataBaseHelper.DB_FILE, e);
            return Optional.empty(); // Dans la pratique, on remonterait l'exception avec DataBaseException
        }
    }// findById()

    @Override
    public boolean existsByName(String name) {
        try (Scanner scanner = new Scanner(new File(this.pathProductDbFile))) {
            final Map<String, Integer> columns = DataBaseHelper.parseHead(scanner);
            while (scanner.hasNext()) {
                List<String> row = DataBaseHelper.parseRow(scanner);
                String columnValue = row.get(columns.get(MappingEnum.NAME.getName()));
                if (name.equals(columnValue)) {
                    return true;
                }
            }
            return false;
        } catch (FileNotFoundException e) {
            LOGGER.error("File {} doesn't exist", DataBaseHelper.DB_FILE, e);
            return false;  // Dans la pratique, on remonterait l'exception avec DataBaseException
        }
    }// existsByName()

    @Override
    public boolean existsById(int id) {
        try (Scanner scanner = new Scanner(new File(this.pathProductDbFile))) {
            final Map<String, Integer> columns = DataBaseHelper.parseHead(scanner);
            while (scanner.hasNext()) {
                List<String> row = DataBaseHelper.parseRow(scanner);
                String columnValue = row.get(columns.get(MappingEnum.ID.getName()));
                if (String.valueOf(id).equals(columnValue)) {
                    return true;
                }
            }
            return false;
        } catch (FileNotFoundException e) {
            LOGGER.error("File {} doesn't exist", DataBaseHelper.DB_FILE, e);
            return false;  // Dans la pratique, on remonterait l'exception avec DataBaseException
        }
    }// existsById()

    @Override
    public void deleteById(int id) throws DatabaseException {
        List<String> lines;
        try {
            File file = new File(this.pathProductDbFile);
            lines = FileUtils.readLines(file, StandardCharsets.UTF_8.name());
            final Map<String, Integer> columns = DataBaseHelper.parseHead(lines.get(0));
            List<String> updatedLines = lines.
                    stream()
                    .filter(value -> {
                        String[] rowSplitted = value.split(DataBaseHelper.SEPARATOR_CSV);
                        return !(rowSplitted[columns.get(MappingEnum.ID.getName())].equals(String.valueOf(id)));
                    })
                    .collect(Collectors.toList());
            FileUtils.writeLines(file, updatedLines, false);
        } catch (IOException e) {
            LOGGER.error("Error with File {}", DataBaseHelper.DB_FILE, e);
            throw new DatabaseException("Impossible to delete product with id = " + id);
        }
    }// deleteById()

    @Override
    public void save(ProductDTO product) throws DatabaseException, ArgumentMissingException {
        if(product == null){
            throw new ArgumentMissingException("Product is null");
        }
        List<String> lines;
        try {
            File file = new File(this.pathProductDbFile);
            lines = FileUtils.readLines(file, StandardCharsets.UTF_8.name());
            final Map<String, Integer> columns = DataBaseHelper.parseHead(lines.get(0));
            final Map<Integer, String> idxToColumns = DataBaseHelper.inverseMappingHeader(columns);

            String idGenerated = DataBaseHelper.generateId(lines, columns);
            StringBuilder sb = new StringBuilder();
            List<Integer> indexes = new ArrayList<>(columns.values());
            indexes.stream()
                    .sorted(Integer::compare)
                    .forEach(idx -> {
                        if(MappingEnum.ID.getName().equals(idxToColumns.get(idx))){
                            sb.append(idGenerated);
                        }else if(MappingEnum.NAME.getName().equals(idxToColumns.get(idx))){
                            sb.append(product.getName());
                        }else if(MappingEnum.DESC.getName().equals(idxToColumns.get(idx))){
                            sb.append(product.getDescription());
                        }else if(MappingEnum.PRICE.getName().equals(idxToColumns.get(idx))){
                            sb.append(product.getPrice());
                        }else if(MappingEnum.QUANTITY_AVAILABLE.getName().equals(idxToColumns.get(idx))){
                            sb.append(product.getQuantityAvailable());
                        }else if(MappingEnum.CATEGORY_ID.getName().equals(idxToColumns.get(idx)) && product.getCategory() != null){
                            sb.append(product.getCategory().getId());
                        }
                        sb.append(DataBaseHelper.SEPARATOR_CSV);
                    });
            String tmp = sb.toString();
            String lineToAdd = tmp.substring(0, tmp.length()-1);

            lines.add(lineToAdd);
            FileUtils.writeLines(file, lines, false);
        } catch (IOException e) {
            LOGGER.error("Error with File {}", DataBaseHelper.DB_FILE, e);
            throw new DatabaseException("Impossible to save product with name = " + product.getName());
        }
    }// save()

    @Override
    public void update(ProductDTO product) throws DatabaseException, ArgumentMissingException {
        if(product == null){
            throw new ArgumentMissingException("Product is null");
        }
        List<String> lines;
        try {
            File file = new File(this.pathProductDbFile);
            lines = FileUtils.readLines(file, StandardCharsets.UTF_8.name());
            final Map<String, Integer> columns = DataBaseHelper.parseHead(lines.get(0));
            final Map<Integer, String> idxToColumns = DataBaseHelper.inverseMappingHeader(columns);
            List<String> updatedLines = lines.
                    stream()
                    .map(value -> {
                        String[] rowSplitted = value.split(DataBaseHelper.SEPARATOR_CSV);
                        if(rowSplitted[columns.get(MappingEnum.ID.getName())].equals(String.valueOf(product.getId()))){
                            StringBuilder sb = new StringBuilder();
                            List<Integer> indexes = new ArrayList<>(columns.values());
                            indexes.stream()
                                    .sorted(Integer::compare)
                                    .forEach(idx -> {
                                        if(MappingEnum.ID.getName().equals(idxToColumns.get(idx))){
                                            sb.append(product.getId());
                                        }else if(MappingEnum.NAME.getName().equals(idxToColumns.get(idx))){
                                            sb.append(product.getName());
                                        }else if(MappingEnum.DESC.getName().equals(idxToColumns.get(idx))){
                                            sb.append(product.getDescription());
                                        }else if(MappingEnum.PRICE.getName().equals(idxToColumns.get(idx))){
                                            sb.append(product.getPrice());
                                        }else if(MappingEnum.QUANTITY_AVAILABLE.getName().equals(idxToColumns.get(idx))){
                                            sb.append(product.getQuantityAvailable());
                                        }else if(MappingEnum.CATEGORY_ID.getName().equals(idxToColumns.get(idx)) && product.getCategory() != null){
                                            sb.append(product.getCategory().getId());
                                        }
                                        sb.append(DataBaseHelper.SEPARATOR_CSV);
                                    });
                            String result = sb.toString();
                            return result.substring(0, result.length()-1);
                        }
                        return value;
                    })
                    .collect(Collectors.toList());
            FileUtils.writeLines(file, updatedLines, false);
        } catch (IOException e) {
            LOGGER.error("Error with File {}", DataBaseHelper.DB_FILE, e);
            throw new DatabaseException("Impossible to update product with id = " + product.getId());
        }
    }// update()

    @Override
    public void begin() {}

    @Override
    public void commit() throws TransactionException {}

    @Override
    public void rollback() {}

}// ProductRepositoryImpl
