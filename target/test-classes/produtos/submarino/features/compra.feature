Feature:
  1- Entre em um grande portal de comércio online - Submarino
  2- Faça uma busca por um produto
  3- Valide o retorno da busca
  4- Escolha um produto na lista
  5- Adicione o carrinho
  6- Valide o produto no carrinho

  Scenario: Cadastro de novos usuários passando pela introducao
    Given O usuario acessa a pagina inicial do submarino e busca pelo produto "teste"
    When O usuario adiciona um produto no carrinho
    Then O usuario valida que o produto esta no carrinho



