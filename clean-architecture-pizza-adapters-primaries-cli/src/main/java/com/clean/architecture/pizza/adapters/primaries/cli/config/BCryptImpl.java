package com.clean.architecture.pizza.adapters.primaries.cli.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.clean.architecture.pizza.core.ports.PasswordEncoder;
import org.apache.commons.lang3.StringUtils;

/**
 * Implémentation du port PasswordEncoder à l'aide de
 * l'algorithme BCrypt.
 */
public class BCryptImpl implements PasswordEncoder {

    /**
     * Encode un mot de passe avec l'algorithme BCrypt
     * @param rawPassword Mot de passe clair à encoder
     * @return mot de passe encodé
     */
    @Override
    public String encode(String rawPassword) {
        if(StringUtils.isEmpty(rawPassword)){
            return "";
        }
        return BCrypt.withDefaults().hashToString(12, rawPassword.toCharArray());
    }// encode()

    /**
     * Compare un mot de passe en clair avec un mot de passe
     * encodé en BCrypt.
     * @param rawPassword Mot de passe en clair
     * @param encodedPassword Mot de passe encodé en BCrypt
     * @return true si les deux mots de passes sont identiques sinon false
     */
    public boolean isEquals(String rawPassword, String encodedPassword){
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }// isEquals()

}// BCryptImpl
