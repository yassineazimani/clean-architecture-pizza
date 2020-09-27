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
package com.clean.architecture.pizza.adapters.rest.config;

import com.clean.architecture.pizza.adapters.rest.services.CustomUserDetailsService;
import com.clean.architecture.pizza.adapters.rest.services.JwtTokenProviderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Vérifie le header Authorization contenant le JWT Token et extrait ce dernier pour
 * contrôler sa validité.
 *
 * @author Yassine AZIMANI
 */

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProviderService jwtTokenProviderService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final Logger LOGGER = LogManager.getLogger(JwtAuthenticationFilter.class);

    /** Contrôle le JWT Token puis démarre l'authentification Spring */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwtToken = getJwtTokenFromRequest(request);
            if (StringUtils.hasText(jwtToken) && jwtTokenProviderService.validateToken(jwtToken)) {
                Integer userId = jwtTokenProviderService.getUserIdFromToken(jwtToken);
                UserDetails user = customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to set user authentication security context {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    } // doFilterInternal()

    /**
     * Extrait le JWT Token de la requête client
     *
     * @param request Client request
     * @return Jwt Token
     */
    private String getJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String jwt = null;
        if (StringUtils.hasText(bearerToken) && bearerToken.contains("Bearer")) {
            jwt = bearerToken.substring(7);
        }
        return jwt;
    } // getJwtTokenFromRequest()

}// JwtAuthenticationFilter
