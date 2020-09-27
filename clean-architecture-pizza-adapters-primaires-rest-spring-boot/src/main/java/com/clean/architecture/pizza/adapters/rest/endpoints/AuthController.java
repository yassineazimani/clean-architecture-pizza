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
package com.clean.architecture.pizza.adapters.rest.endpoints;

import com.clean.architecture.pizza.adapters.rest.model.LoginRequestDTO;
import com.clean.architecture.pizza.adapters.rest.services.JwtTokenProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur d'authentification de l'utilisateur
 *
 * @author Yassine AZIMANI
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private JwtTokenProviderService tokenProviderService;

    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(JwtTokenProviderService tokenProviderService, AuthenticationManager authenticationManager) {
        this.tokenProviderService = tokenProviderService;
        this.authenticationManager = authenticationManager;
    }// AuthController()

    /**
     * Connecte l'administrateur
     *
     * @param loginRequest Paramètres de connexion
     * @return Token généré
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getId(), loginRequest.getPassword()));
        Map<String, String> tokenResponse = new HashMap<>();
        tokenResponse.put("Bearer", this.authenticationAndGetToken(authentication));
        return ResponseEntity.ok(tokenResponse);
    } // signIn()

    /**
     * Connecte l'utilisateur au contexte de sécurité Spring (s'il existe dans la base de données)
     * et génère le token JWT pour ce dernier.
     *
     * @param authentication authentication
     * @return JWT Token
     */
    private String authenticationAndGetToken(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProviderService.generateToken(authentication);
    }// authenticationAndGetToken()

}// AuthController
