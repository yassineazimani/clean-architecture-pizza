package com.clean.architecture.pizza.adapters.primaries.cli.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.clean.architecture.pizza.core.ports.PasswordEncoder;
import org.apache.commons.lang3.StringUtils;

public class BCryptImpl implements PasswordEncoder {

    @Override
    public String encode(String rawPassword) {
        if(StringUtils.isEmpty(rawPassword)){
            return "";
        }
        return BCrypt.withDefaults().hashToString(12, rawPassword.toCharArray());
    }// encode()

    public boolean isEquals(String rawPassword, String encodedPassword){
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }// isEquals()

}// BCryptImpl
