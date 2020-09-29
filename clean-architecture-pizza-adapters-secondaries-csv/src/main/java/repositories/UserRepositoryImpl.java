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

import com.clean.architecture.pizza.core.exceptions.TransactionException;
import com.clean.architecture.pizza.core.model.UserDTO;
import com.clean.architecture.pizza.core.ports.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.DataBaseHelper;
import utils.MappingEnum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class UserRepositoryImpl implements UserRepository {

    private String pathUserDbFile;

    private final static Logger LOGGER = LogManager.getLogger(UserRepositoryImpl.class);

    public UserRepositoryImpl() {
        try {
            Properties properties = new DataBaseHelper().getProperties();
            this.pathUserDbFile = properties.getProperty("path.user.db");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }// UserRepositoryImpl()

    @Override
    public Optional<UserDTO> findById(int id) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(this.pathUserDbFile));
            final Map<String, Integer> columns = DataBaseHelper.parseHead(scanner);
            while (scanner.hasNext()) {
                List<String> row = DataBaseHelper.parseRow(scanner);
                String columnValue = row.get(columns.get(MappingEnum.ID.getName()));
                if(columnValue != null && String.valueOf(id).equals(columnValue)){
                    UserDTO userDTO = new UserDTO(
                            Integer.valueOf(row.get(columns.get(MappingEnum.ID.getName()))),
                            row.get(columns.get(MappingEnum.PASSWORD.getName()))
                    );
                    return Optional.of(userDTO);
                }
            }
            return Optional.empty();
        } catch (FileNotFoundException e) {
            LOGGER.error("File {} doesn't exist", DataBaseHelper.DB_FILE, e);
            return Optional.empty(); // Dans la pratique, on remonterait l'exception avec DataBaseException
        } finally{
            if(scanner != null){
                scanner.close();
            }
        }
    }// findById()

    @Override
    public void begin() {}

    @Override
    public void commit() throws TransactionException {}

    @Override
    public void rollback() {}

}// UserRepositoryImpl
