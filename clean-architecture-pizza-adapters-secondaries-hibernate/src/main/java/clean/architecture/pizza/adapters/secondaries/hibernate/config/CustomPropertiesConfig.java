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
package clean.architecture.pizza.adapters.secondaries.hibernate.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration personnalisée du fichier de configuration Persistence.xml
 */
public class CustomPropertiesConfig {

    /**
     * Mise à jour de la configuration du fichier Persistence.xml à
     * l'aide de variables d'environnement.
     * @return clés/valeurs mises à jour à partir des variables d'environnement.
     */
    public static Map<String, Object> getCustomPersistenceConfiguration(){
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();
        env.forEach((key, value) -> {
            if (key.contains("PASSWORD_DB")) {
                configOverrides.put("javax.persistence.jdbc.password", value);
            }
            if(key.contains("USER_DB")){
                configOverrides.put("javax.persistence.jdbc.user", value);
            }
        });
        return configOverrides;
    }// getCustomPersistenceConfiguration()

}// CustomPropertiesConfig
