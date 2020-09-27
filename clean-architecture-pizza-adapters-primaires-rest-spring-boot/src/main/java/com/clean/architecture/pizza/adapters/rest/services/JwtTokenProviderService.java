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
package com.clean.architecture.pizza.adapters.rest.services;

import com.clean.architecture.pizza.adapters.rest.model.UserAuthenticated;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Classe de service gérant un JWT token
 *
 * @author Yassine AZIMANI
 */
@Service
public class JwtTokenProviderService {

    /** JWT clé secrète */
    @Value("${jwt.secret}")
    private String jwtSecret;

    /** JWT date d'expiration exprimée en millisecondes. */
    @Value("${jwt.expiration}")
    private String jwtExpiration;

    private JwtParser parser = Jwts.parser();

    private static final Logger LOGGER = LogManager.getLogger(JwtTokenProviderService.class);

    /**
     * Génère le JWT Token quand l'utilisateur est trouvé en base de données.
     *
     * @param authentication User connecté
     * @return JWT token
     */
    public String generateToken(Authentication authentication) {
        UserAuthenticated userPrincipal = (UserAuthenticated) authentication.getPrincipal();
        Date now = new Date();
        int jwtExpirationTime = Integer.parseInt(jwtExpiration);
        Date dateJwtExpiration = new Date(now.getTime() + jwtExpirationTime);
        JwtBuilder builder = Jwts.builder();
        builder.setExpiration(dateJwtExpiration);
        builder.setIssuedAt(now);
        builder.setSubject(userPrincipal.getId().toString());
        builder.claim("id", userPrincipal.getUsername());
        builder.signWith(SignatureAlgorithm.HS512, jwtSecret);
        return builder.compact();
    } // generateToken()

    /**
     * Extrait l'identifiant de l'utilisateur du token
     *
     * @param jwtToken JWT Token
     * @return User's ID
     */
    public Integer getUserIdFromToken(String jwtToken) {
        parser.setSigningKey(jwtSecret);
        Claims claims = parser.parseClaimsJws(jwtToken).getBody();
        return Integer.valueOf(claims.getSubject());
    } // getUserIdFromToken()

    /**
     * Contrôle la validité du JWT Token.
     *
     * @param jwtToken JWT Token
     * @return boolean
     */
    public boolean validateToken(String jwtToken) {
        boolean response = false;
        try {
            parser.setSigningKey(jwtSecret);
            parser.parseClaimsJws(jwtToken);
            response = true;
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT Token {}", ex.getMessage());
        } catch (SignatureException ex) {
            LOGGER.error("Invalid JWT Signature {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT Token {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty {}", ex.getMessage());
        }
        return response;
    } // validateToken()

}// JwtTokenProviderService
