package produtos.submarino.stepdefs;

import cucumber.api.java.en.When;
import support.Core;

import static produtos.submarino.objetos.SubmarinoSearchResults.*;

public class SDSubmarinoSearchResults extends Core {
    public static String productChosen = "";

    @When("O usuario adiciona um produto no carrinho")
    public void oUsuarioAdicionaUmProdutoNoCarrinho() {
        click(submarinoSearchResultsFirstProductDiv);
        productChosen = returnText(submarinoSearchResultsProductChosenTitle);
        click(submarinoSearchResultsBuyButton);
    }
}
