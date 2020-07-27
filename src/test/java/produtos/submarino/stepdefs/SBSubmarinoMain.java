package produtos.submarino.stepdefs;

import cucumber.api.java.en.Given;
import support.Core;

import static org.junit.Assert.assertEquals;
import static produtos.submarino.objetos.SubmarinoMain.submarinoMainSearchBarInput;
import static produtos.submarino.objetos.SubmarinoMain.submarinoMainSearchButton;
import static produtos.submarino.objetos.SubmarinoSearchResults.submarinoSearchResultsSearchResultsTitle;

public class SBSubmarinoMain extends Core {

    @Given("O usuario acessa a pagina inicial do submarino e busca pelo produto {string}")
    public void oUsuarioAcessaAPaginaInicialDoSubmarinoEBuscaPeloProduto(String arg0) {
        navigate("https://www.submarino.com.br/");
        send(submarinoMainSearchBarInput, arg0);
        click(submarinoMainSearchButton);
        assertEquals(returnText(submarinoSearchResultsSearchResultsTitle), arg0);
    }
}
