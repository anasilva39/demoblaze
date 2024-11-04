Feature: Compra de Monitor
  Scenario: Fluxo de compra de um monitor
    Given que estou na página inicial
    When acesso a categoria de Monitores
    And adiciono um monitor ao carrinho
    And vou para o carrinho
    And finalizo a compra preenchendo os dados
    Then a compra é realizada com sucesso
