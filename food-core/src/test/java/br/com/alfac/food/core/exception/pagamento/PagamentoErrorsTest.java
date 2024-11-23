package br.com.alfac.food.core.exception.pagamento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import br.com.alfac.food.core.exception.FoodError;

public class PagamentoErrorsTest {
    @Test
  void testPagamentoJaRealizado() {
    FoodError error = PagamentoErro.PAGAMENTO_JA_REALIZADO;
    assertEquals("PGTO-001", error.getErrorCode());
    assertEquals("Pagamento já realizado", error.getErrorMessage());
  }

  @Test
  void testStatusInvalidoAprovacao() {
    FoodError error = PagamentoErro.STATUS_INVALIDO_APROVACAO;
    assertEquals("PGTO-002", error.getErrorCode());
    assertEquals("Status atual do pagamento não permite aprovação", error.getErrorMessage());
  }

  @Test
  void testPedidoIdObrigatorio() {
    FoodError error = PagamentoErro.PEDIDO_ID_OBRIGATORIO;
    assertEquals("PGTO-003", error.getErrorCode());
    assertEquals("Id do pedido é obrigatório para registrar o pagamento", error.getErrorMessage());
  }

  @Test
  void testStatusObrigatorio() {
    FoodError error = PagamentoErro.STATUS_OBRIGATORIO;
    assertEquals("PGTO-004", error.getErrorCode());
    assertEquals("Status do pagamento é obrigatório", error.getErrorMessage());
  }

  @Test
  void testIdObrigatorio() {
    FoodError error = PagamentoErro.ID_OBRIGATORIO;
    assertEquals("PGTO-005", error.getErrorCode());
    assertEquals("Id do pagamento é obrigatório", error.getErrorMessage());
  }

  @Test
  void testDataPagamentoObrigatorio() {
    FoodError error = PagamentoErro.DATA_PAGAMENTO_OBRIGATORIO;
    assertEquals("PGTO-006", error.getErrorCode());
    assertEquals("Data do pagamento é obrigatório", error.getErrorMessage());
  }

  @Test
  void testPagamentoNaoEncontrado() {
    FoodError error = PagamentoErro.PAGAMENTO_NAO_ENCONTRADO;
    assertEquals("PGTO-007", error.getErrorCode());
    assertEquals("Pagamento não encontrado", error.getErrorMessage());
  }

  @Test
  void testStatusInvalidoCancelamento() {
    FoodError error = PagamentoErro.STATUS_INVALIDO_CANCELAMENTO;
    assertEquals("PGTO-008", error.getErrorCode());
    assertEquals("Status atual do pagamento não permite cancelamento", error.getErrorMessage());
  }
}
