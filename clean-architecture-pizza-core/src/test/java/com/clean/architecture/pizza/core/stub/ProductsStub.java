package com.clean.architecture.pizza.core.stub;

import com.clean.architecture.pizza.core.model.CategoryDTO;
import com.clean.architecture.pizza.core.model.ProductDTO;

public class ProductsStub {

    public static ProductDTO getPizza4Fromages(int quantityAvailable){
        ProductDTO pizza = new ProductDTO();
        pizza.setId(1);
        pizza.setName("Pizza 4 fromages");
        pizza.setPrice(8.90);
        pizza.setDescription("Pizza incontournable de toute pizzeria qui se respecte ! Son comté, son bleu, sa chèvre et son emmental lui donnent un goût exquis !");
        pizza.setQuantityAvailable(quantityAvailable);
        pizza.setCategory(new CategoryDTO(1, "Pizzas"));
        return pizza;
    }// getPizza4Fromages()

    public static ProductDTO getPizzaMushRooms(int quantityAvailable){
        ProductDTO pizza = new ProductDTO();
        pizza.setId(1);
        pizza.setName("Pizza aux champignons");
        pizza.setPrice(7.90);
        pizza.setDescription("Pizza aux champignons de Paris");
        pizza.setQuantityAvailable(quantityAvailable);
        pizza.setCategory(new CategoryDTO(1, "Pizzas"));
        return pizza;
    }// getPizzaMushRooms()

    public static ProductDTO getOrangina(int quantityAvailable){
        ProductDTO orangina = new ProductDTO();
        orangina.setId(1400);
        orangina.setName("Orangina");
        orangina.setPrice(2.90);
        orangina.setDescription("Découvrez la recette secouée et rafraichissante d’Orangina au goût incomparable. Une formule unique grâce à un subtil mélange de jus, de pulpe et d’huile essentielle d’orange pour un véritable moment de plaisir.");
        orangina.setQuantityAvailable(quantityAvailable);
        orangina.setCategory(new CategoryDTO(2, "Boissons"));
        return orangina;
    }// getOrangina()

    public static ProductDTO getLemonPie(int quantityAvailable){
        ProductDTO lemonPie = new ProductDTO();
        lemonPie.setId(2000);
        lemonPie.setName("Tarte aux citrons");
        lemonPie.setPrice(4.90);
        lemonPie.setDescription("Goûtez à cette merveilleuse tarte avec son arôme acidulé et une bonne pâte tirée d'une recette de notre grand-mère");
        lemonPie.setQuantityAvailable(quantityAvailable);
        lemonPie.setCategory(new CategoryDTO(3, "Desserts"));
        return lemonPie;
    }// getLemonPie()

}
