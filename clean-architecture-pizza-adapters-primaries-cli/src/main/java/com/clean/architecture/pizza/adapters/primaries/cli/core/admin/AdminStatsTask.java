package com.clean.architecture.pizza.adapters.primaries.cli.core.admin;

import com.clean.architecture.pizza.core.admin.stats.StatsOrders;
import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import com.clean.architecture.pizza.core.model.StatsSumOrderTotalByProductsDTO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * Cette classe gère l'administration des statistiques
 * de l'application.
 */
public class AdminStatsTask {

    private StatsOrders statsOrders;

    public AdminStatsTask(StatsOrders statsOrders) {
        this.statsOrders = statsOrders;
    }// AdminStatsTask()

    /**
     * Exécution du choix sélectionné par l'utilisateur
     * @param choice choix
     * @return boolean déterminant si le retour au menu principal doit être effectué
     */
    public boolean run(int choice) {
        boolean backToMainMenu = false;
        switch (choice) {
            case 1:
                getSumOrderTotalByProducts();
                break;
            case 2:
                backToMainMenu = true;
                break;
        }
        return backToMainMenu;
    }// run()

    /**
     * Affichage de la somme totale pour chaque produit
     * vendu (de manière globale)
     */
    public void getSumOrderTotalByProducts(){
        List<StatsSumOrderTotalByProductsDTO> stats;
        try {
            stats = this.statsOrders.getSumTotalOrdersByProducts();
            if(CollectionUtils.isNotEmpty(stats)){
                stats.forEach(stat -> System.out.println("Product " + stat.getName() + " => " + stat.getTotal() + " €"));
                System.out.println();
            }else{
                System.out.println("No statistics available");
            }
        } catch (AuthenticationException e) {
            System.err.println("You don't have the rights to get statistics");
        }
    }// getSumOrderTotalByProducts()

}// AdminStatsTask
