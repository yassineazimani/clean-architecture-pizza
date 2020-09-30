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
package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class DataBaseHelper {

    private final static Logger LOGGER = LogManager.getLogger(DataBaseHelper.class);

    public final static String SEPARATOR_CSV = ";";

    public final static String SEPARATOR_PRODUCTS_ORDER = ":";

    public final static String DB_FILE = "db.csv";

    public static void createFile(String path){
    }// createFile()

    public static void deleteFile(String path){
    }// deleteFile()

    public static Map<String, Integer> parseHead(Scanner scanner){
        Map<String, Integer> head = new HashMap<>();
        if(scanner != null && scanner.hasNext()){
            String strColumns = scanner.nextLine();
            String[] tabColumns = strColumns.split(SEPARATOR_CSV);
            for(int i = 0; i < tabColumns.length; ++i){
                head.put(tabColumns[i], i);
            }
        }
        return head;
    }// parseHead()

    public static Map<String, Integer> parseHead(String line){
        Map<String, Integer> head = new HashMap<>();
        if(line != null && !line.isEmpty()){
            String[] tabColumns = line.split(SEPARATOR_CSV);
            for(int i = 0; i < tabColumns.length; ++i){
                head.put(tabColumns[i], i);
            }
        }
        return head;
    }// parseHead()

    public static Map<Integer, String> inverseMappingHeader(Map<String, Integer> mapping){
        Map<Integer, String> result = new HashMap<>();
        if(mapping == null){
            return result;
        }
        mapping.forEach((columnName, idx) -> result.put(idx, columnName));
        return result;
    }// inverseMappingHeader()

    public static List<String> parseRow(Scanner scanner){
        if(scanner == null){
            return Collections.emptyList();
        }
        String row = scanner.nextLine();
        String[] rowSplitted = row.split(SEPARATOR_CSV);
        return Arrays.asList(rowSplitted);
    }// parseRow()

    /**
     * Get properties from file properties
     *
     * @return properties
     * @throws IOException
     */
    public Properties getProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream =
                     getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if(inputStream != null)
                properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("Impossible to get application.properties", e);
            throw e;
        }
        return properties;
    } // getProperties()

    public static String generateId(List<String> lines, Map<String, Integer> columns){
        List<String> values = lines.subList(1, lines.size());
        Integer maxId = values.stream().map(value -> {
            String[] tmp = value.split(DataBaseHelper.SEPARATOR_CSV);
            return Integer.valueOf(tmp[columns.get(MappingEnum.ID.getName())]);
        })
        .max(Integer::compare)
        .orElse(0);
        return String.valueOf(maxId + 1);
    }// generateId()

}// DataBaseHelper
