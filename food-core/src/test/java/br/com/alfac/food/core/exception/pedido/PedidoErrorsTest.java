package br.com.alfac.food.core.exception.pedido;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import br.com.alfac.food.core.exception.StatusCode;

public class PedidoErrorsTest {
    @Test
  void testPedidoNaoEncontrado() {
    assertEquals("PEDIDO-001", PedidoErros.PEDIDO_NAO_ENCONTRADO.getErrorCode());
    assertEquals("Pedido não encontrado", PedidoErros.PEDIDO_NAO_ENCONTRADO.getErrorMessage());
    assertEquals(StatusCode.NOT_FOUND.getCode(), PedidoErros.PEDIDO_NAO_ENCONTRADO.getStatusCode());
  }

  @Test
  void testStatusPedidoJaFinalizado() {
    assertEquals("PEDIDO-002", PedidoErros.STATUS_PEDIDO_JA_FINALIZADO.getErrorCode());
    assertEquals("Status do pedido já finalizado não permite alteração.", PedidoErros.STATUS_PEDIDO_JA_FINALIZADO.getErrorMessage());
  }

  @Test
  void testPedidoNaoPago() {
    assertEquals("PEDIDO-003", PedidoErros.PEDIDO_NAO_PAGO.getErrorCode());
    assertEquals("Pedido não pago.", PedidoErros.PEDIDO_NAO_PAGO.getErrorMessage());
  }

  @Test
  void testPedidoComboNull() {
    assertEquals("PEDIDO-004", PedidoErros.PEDIDO_COMBO_NULL.getErrorCode());
    assertEquals("Não é possível adicionar combo nulo.", PedidoErros.PEDIDO_COMBO_NULL.getErrorMessage());
  }

  @Test
  void testDataCadastroObrigatorio() {
    assertEquals("PEDIDO-005", PedidoErros.DATA_CADASTRO_OBRIGATORIO.getErrorCode());
    assertEquals("Data de cadastro é obrigatória.", PedidoErros.DATA_CADASTRO_OBRIGATORIO.getErrorMessage());
  }

  @Test
  void testIdObrigatorio() {
    assertEquals("PEDIDO-006", PedidoErros.ID_OBRIGATORIO.getErrorCode());
    assertEquals("Id é obrigatório.", PedidoErros.ID_OBRIGATORIO.getErrorMessage());
  }

  @Test
  void testStatusObrigatorio() {
    assertEquals("PEDIDO-007", PedidoErros.STATUS_OBRIGATORIO.getErrorCode());
    assertEquals("Status é obrigatório.", PedidoErros.STATUS_OBRIGATORIO.getErrorMessage());
  }

  @Test
  void testStatusInvalidoCancelamento() {
    assertEquals("PEDIDO-008", PedidoErros.STATUS_INVALIDO_CANCELAMENTO.getErrorCode());
    assertEquals("Status atual do pedido não permite cancelamento.", PedidoErros.STATUS_INVALIDO_CANCELAMENTO.getErrorMessage());
  }

  @Test
  void testStatusPedidoCancelado() {
    assertEquals("PEDIDO-009", PedidoErros.STATUS_PEDIDO_CANCELADO.getErrorCode());
    assertEquals("Status do pedido cancelado não permite alteração.", PedidoErros.STATUS_PEDIDO_CANCELADO.getErrorMessage());
  }
}
