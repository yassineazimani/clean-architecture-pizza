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
