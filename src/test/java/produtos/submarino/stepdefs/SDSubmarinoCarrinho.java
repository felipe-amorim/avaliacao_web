package produtos.submarino.stepdefs;

import cucumber.api.java.en.Then;
import support.Core;

import static org.junit.Assert.assertEquals;
import static produtos.submarino.objetos.SubmarinoCarrinho.*;

public class SDSubmarinoCarrinho extends Core {
    @Then("O usuario valida que o produto esta no carrinho")
    public void oUsuarioValidaQueOProdutoEstaNoCarrinho() {
        assertEquals(returnText(submarinoCarrinhoPageTitle),"Meu carrinho");
        assertEquals(returnText(submarinoCarrinhoProductTitle), SDSubmarinoSearchResults.productChosen);
        evidence("Tirando evidencia final");
    }
}
