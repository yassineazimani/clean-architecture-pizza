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

import com.clean.architecture.pizza.adapters.rest.model.ResponseHttpDTO;
import com.clean.architecture.pizza.core.admin.stats.StatsOrders;
import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Contrôleur gérant les produits
 */
@RestController
@RequestMapping("/api/admin/stats")
public class StatsController {

    /**
     * Statistiques
     */
    private StatsOrders statsOrders;

    @Autowired
    public StatsController(StatsOrders statsOrders) {
        this.statsOrders = statsOrders;
    }// StatsController()

    /**
     * Récupère les totaux de commandes par produit.
     * (Global, applicable pour toute la durée de la vie de l'entreprise).
     * @return
     */
    @GetMapping("/admin/sumTotalByProducts")
    public ResponseEntity<?> getSumTotalOrdersByProducts(){
        try {
            return ResponseEntity.ok(new ResponseHttpDTO(this.statsOrders.getSumTotalOrdersByProducts(), null));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }// getSumTotalOrdersByProducts()

}// StatsController
