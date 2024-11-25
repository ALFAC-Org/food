# language: pt
Funcionalidade: API - Pagamento

  @smoke
  Cenário: Buscar um pagamento existente
    Dado que um pagamento já foi registrado
    Quando requisitar a busca do pagamento
    Então o pagamento é exibido com sucesso



