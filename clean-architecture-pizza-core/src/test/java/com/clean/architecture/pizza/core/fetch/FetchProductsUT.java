package com.clean.architecture.pizza.core.fetch;

import com.clean.architecture.pizza.core.model.ProductDTO;
import com.clean.architecture.pizza.core.ports.ProductRepository;
import com.clean.architecture.pizza.core.stub.ProductsStub;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class FetchProductsUT {

    private FetchProducts fetchProducts;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void setUp(){
        this.fetchProducts = new FetchProducts(productRepository);
    }// setUp()

    @Test
    public void find_all_should_return_empty_list_when_no_products_are_available(){
        Map<String, List<ProductDTO>> products = this.fetchProducts.findAll();
        Assertions.assertThat(products).isEmpty();
    }// find_all_should_return_empty_list_when_no_products_are_available()

    @Test
    public void find_all_should_return_empty_list_when_no_pizza_no_drinks_no_desserts_are_available(){
        ProductDTO pizza = ProductsStub.getPizza4Fromages(0);
        ProductDTO drink = ProductsStub.getOrangina(0);
        ProductDTO dessert = ProductsStub.getLemonPie(0);
        Mockito.when(productRepository.findAllProducts())
                .thenReturn(Stream.of(pizza, drink, dessert).collect(Collectors.toList()));
        Map<String, List<ProductDTO>> products = this.fetchProducts.findAll();
        Assertions.assertThat(products).isEmpty();
    }// find_all_should_return_empty_list_when_no_pizza_no_drinks_no_desserts_are_available()

    @Test
    public void find_all_should_return_pizza_when_pizza_is_available(){
        ProductDTO productExpected = ProductsStub.getPizza4Fromages(5);
        Mockito.when(productRepository.findAllProducts())
                .thenReturn(Collections.singletonList(productExpected));
        Map<String, List<ProductDTO>> products = this.fetchProducts.findAll();
        Assertions.assertThat(products).isNotEmpty();
        Assertions.assertThat(products).hasSize(1);
        List<ProductDTO> pizzas = products.get("Pizzas");
        Assertions.assertThat(pizzas).hasSize(1);
        ProductDTO pizza = pizzas.get(0);
        Assertions.assertThat(pizza.getId()).isEqualTo(1);
        Assertions.assertThat(pizza.getName()).isEqualTo("Pizza 4 fromages");
        Assertions.assertThat(pizza.getPrice()).isEqualTo(8.90);
        Assertions.assertThat(pizza.getDescription()).isEqualTo("Pizza incontournable de toute pizzeria qui se respecte ! Son comté, son bleu, sa chèvre et son emmental lui donnent un goût exquis !");
        Assertions.assertThat(pizza.getQuantityAvailable()).isGreaterThan(0);
        Assertions.assertThat(pizza.getCategory().getId()).isEqualTo(1);
        Assertions.assertThat(pizza.getCategory().getName()).isEqualTo("Pizzas");
    }// find_all_should_return_pizza_when_pizza_is_available()

    @Test
    public void find_all_should_return_pizza_mushrooms_when_pizza_mushrooms_is_available_and_pizza_4_fromages_is_unavailable(){
        ProductDTO pizza4Fromages = ProductsStub.getPizza4Fromages(0);
        ProductDTO pizzaMushrooms = ProductsStub.getPizzaMushRooms(2);
        Mockito.when(productRepository.findAllProducts())
                .thenReturn(Stream.of(pizza4Fromages, pizzaMushrooms).collect(Collectors.toList()));
        Map<String, List<ProductDTO>> products = this.fetchProducts.findAll();
        Assertions.assertThat(products).isNotEmpty();
        Assertions.assertThat(products).hasSize(1);
        List<ProductDTO> pizzas = products.get("Pizzas");
        Assertions.assertThat(pizzas).hasSize(1);
        ProductDTO pizza = pizzas.get(0);
        Assertions.assertThat(pizza.getId()).isEqualTo(1);
        Assertions.assertThat(pizza.getName()).isEqualTo("Pizza aux champignons");
        Assertions.assertThat(pizza.getPrice()).isEqualTo(7.90);
        Assertions.assertThat(pizza.getDescription()).isEqualTo("Pizza aux champignons de Paris");
        Assertions.assertThat(pizza.getQuantityAvailable()).isGreaterThan(0);
        Assertions.assertThat(pizza.getCategory().getId()).isEqualTo(1);
        Assertions.assertThat(pizza.getCategory().getName()).isEqualTo("Pizzas");
    }// find_all_should_return_pizza_mushrooms_when_pizza_mushrooms_is_available_and_pizza_4_fromages_is_unavailable()

    @Test
    public void find_all_should_return_pizza_with_orangina_when_pizza_and_orangina_are_available(){
        ProductDTO pizzaExpected = ProductsStub.getPizza4Fromages(5);
        ProductDTO drinkExpected = ProductsStub.getOrangina(2);
        Mockito.when(productRepository.findAllProducts())
                .thenReturn(Stream.of(pizzaExpected, drinkExpected).collect(Collectors.toList()));
        Map<String, List<ProductDTO>> products = this.fetchProducts.findAll();
        Assertions.assertThat(products).isNotEmpty();
        Assertions.assertThat(products).hasSize(2);
        List<ProductDTO> pizzas = products.get("Pizzas");
        Assertions.assertThat(pizzas).hasSize(1);
        ProductDTO pizza = pizzas.get(0);
        Assertions.assertThat(pizza.getId()).isEqualTo(1);
        Assertions.assertThat(pizza.getName()).isEqualTo("Pizza 4 fromages");
        Assertions.assertThat(pizza.getPrice()).isEqualTo(8.90);
        Assertions.assertThat(pizza.getDescription()).isEqualTo("Pizza incontournable de toute pizzeria qui se respecte ! Son comté, son bleu, sa chèvre et son emmental lui donnent un goût exquis !");
        Assertions.assertThat(pizza.getQuantityAvailable()).isGreaterThan(0);
        Assertions.assertThat(pizza.getCategory().getId()).isEqualTo(1);
        Assertions.assertThat(pizza.getCategory().getName()).isEqualTo("Pizzas");
        List<ProductDTO> drinks = products.get("Boissons");
        Assertions.assertThat(drinks).hasSize(1);
        ProductDTO orangina = drinks.get(0);
        Assertions.assertThat(orangina.getId()).isEqualTo(1400);
        Assertions.assertThat(orangina.getName()).isEqualTo("Orangina");
        Assertions.assertThat(orangina.getPrice()).isEqualTo(2.90);
        Assertions.assertThat(orangina.getDescription()).isEqualTo("Découvrez la recette secouée et rafraichissante d’Orangina au goût incomparable. Une formule unique grâce à un subtil mélange de jus, de pulpe et d’huile essentielle d’orange pour un véritable moment de plaisir.");
        Assertions.assertThat(orangina.getQuantityAvailable()).isGreaterThan(0);
        Assertions.assertThat(orangina.getCategory().getId()).isEqualTo(2);
        Assertions.assertThat(orangina.getCategory().getName()).isEqualTo("Boissons");
    }// find_all_should_return_pizza_with_orangina_when_pizza_and_orangina_are_available()

    @Test
    public void find_all_should_return_sorted_products_by_id_category_asc_when_products_are_available(){
        ProductDTO pizzaExpected = ProductsStub.getPizza4Fromages(5);
        ProductDTO drinkExpected = ProductsStub.getOrangina(2);
        ProductDTO dessertExpected = ProductsStub.getLemonPie(1);
        Mockito.when(productRepository.findAllProducts())
                .thenReturn(Stream.of(drinkExpected, dessertExpected, pizzaExpected).collect(Collectors.toList()));
        Map<String, List<ProductDTO>> products = this.fetchProducts.findAll();
        Assertions.assertThat(products).isNotEmpty();
        Assertions.assertThat(products).hasSize(3);
        List<ProductDTO> pizzas = products.get("Pizzas");
        Assertions.assertThat(pizzas).hasSize(1);
        ProductDTO pizza = pizzas.get(0);
        Assertions.assertThat(pizza.getId()).isEqualTo(1);
        Assertions.assertThat(pizza.getName()).isEqualTo("Pizza 4 fromages");
        Assertions.assertThat(pizza.getPrice()).isEqualTo(8.90);
        Assertions.assertThat(pizza.getDescription()).isEqualTo("Pizza incontournable de toute pizzeria qui se respecte ! Son comté, son bleu, sa chèvre et son emmental lui donnent un goût exquis !");
        Assertions.assertThat(pizza.getQuantityAvailable()).isGreaterThan(0);
        Assertions.assertThat(pizza.getCategory().getId()).isEqualTo(1);
        Assertions.assertThat(pizza.getCategory().getName()).isEqualTo("Pizzas");
        List<ProductDTO> drinks = products.get("Boissons");
        Assertions.assertThat(drinks).hasSize(1);
        ProductDTO orangina = drinks.get(0);
        Assertions.assertThat(orangina.getId()).isEqualTo(1400);
        Assertions.assertThat(orangina.getName()).isEqualTo("Orangina");
        Assertions.assertThat(orangina.getPrice()).isEqualTo(2.90);
        Assertions.assertThat(orangina.getDescription()).isEqualTo("Découvrez la recette secouée et rafraichissante d’Orangina au goût incomparable. Une formule unique grâce à un subtil mélange de jus, de pulpe et d’huile essentielle d’orange pour un véritable moment de plaisir.");
        Assertions.assertThat(orangina.getQuantityAvailable()).isGreaterThan(0);
        Assertions.assertThat(orangina.getCategory().getId()).isEqualTo(2);
        Assertions.assertThat(orangina.getCategory().getName()).isEqualTo("Boissons");
        List<ProductDTO> desserts = products.get("Desserts");
        Assertions.assertThat(desserts).hasSize(1);
        ProductDTO lemonPie = desserts.get(0);
        Assertions.assertThat(lemonPie.getId()).isEqualTo(2000);
        Assertions.assertThat(lemonPie.getName()).isEqualTo("Tarte aux citrons");
        Assertions.assertThat(lemonPie.getPrice()).isEqualTo(4.90);
        Assertions.assertThat(lemonPie.getDescription()).isEqualTo("Goûtez à cette merveilleuse tarte avec son arôme acidulé et une bonne pâte tirée d'une recette de notre grand-mère");
        Assertions.assertThat(lemonPie.getQuantityAvailable()).isGreaterThan(0);
    }// find_all_should_return_pizza_with_orangina_when_pizza_and_orangina_are_available()

    @Test
    public void find_by_id_with_id_equals_to_1_should_return_pizza_4_fromages(){
        Mockito.when(productRepository.findById(1))
                .thenReturn(Optional.of(ProductsStub.getPizza4Fromages(2)));
        Optional<ProductDTO> optPizza4fromages = fetchProducts.findById(1);
        Assertions.assertThat(optPizza4fromages).isPresent();
        optPizza4fromages.ifPresent(pizza -> {
            Assertions.assertThat(pizza.getId()).isEqualTo(1);
            Assertions.assertThat(pizza.getName()).isEqualTo("Pizza 4 fromages");
            Assertions.assertThat(pizza.getPrice()).isEqualTo(8.90);
            Assertions.assertThat(pizza.getDescription()).isEqualTo("Pizza incontournable de toute pizzeria qui se respecte ! Son comté, son bleu, sa chèvre et son emmental lui donnent un goût exquis !");
            Assertions.assertThat(pizza.getQuantityAvailable()).isGreaterThan(0);
            Assertions.assertThat(pizza.getCategory().getId()).isEqualTo(1);
            Assertions.assertThat(pizza.getCategory().getName()).isEqualTo("Pizzas");
        });
    }// find_by_id_with_id_equals_to_1_should_return_pizza_4_fromages()

    @Test
    public void find_by_id_with_id_unknown_should_return_empty_optional(){
        Mockito.when(productRepository.findById(2))
                .thenReturn(Optional.empty());
        Optional<ProductDTO> optPizza4fromages = fetchProducts.findById(2);
        Assertions.assertThat(optPizza4fromages).isNotPresent();
    }// find_by_id_with_id_unknown_should_return_empty_optional()

}// FetchProductsUT
