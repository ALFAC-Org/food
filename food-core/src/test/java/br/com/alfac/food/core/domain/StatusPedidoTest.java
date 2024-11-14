package br.com.alfac.food.core.domain;

import br.com.alfac.food.core.domain.pedido.StatusPedido;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusPedidoTest {

    @Test
    public void deveConterValoresEsperados() {
        assertEquals(6, StatusPedido.values().length);
        assertEquals(StatusPedido.AGUARDANDO_PAGAMENTO, StatusPedido.valueOf("AGUARDANDO_PAGAMENTO"));
        assertEquals(StatusPedido.RECEBIDO, StatusPedido.valueOf("RECEBIDO"));
        assertEquals(StatusPedido.EM_PREPARACAO, StatusPedido.valueOf("EM_PREPARACAO"));
        assertEquals(StatusPedido.PRONTO, StatusPedido.valueOf("PRONTO"));
        assertEquals(StatusPedido.FINALIZADO, StatusPedido.valueOf("FINALIZADO"));
        assertEquals(StatusPedido.CANCELADO, StatusPedido.valueOf("CANCELADO"));
    }

    @Test
    public void deveRetornarProximoStatus() {
        assertEquals(StatusPedido.RECEBIDO, StatusPedido.AGUARDANDO_PAGAMENTO.getProximoStatus());
        assertEquals(StatusPedido.EM_PREPARACAO, StatusPedido.RECEBIDO.getProximoStatus());
        assertEquals(StatusPedido.PRONTO, StatusPedido.EM_PREPARACAO.getProximoStatus());
        assertEquals(StatusPedido.FINALIZADO, StatusPedido.PRONTO.getProximoStatus());
        assertEquals(StatusPedido.FINALIZADO, StatusPedido.FINALIZADO.getProximoStatus());
    }

    @Test
    public void deveRetornarDescricao() {
        assertEquals("Aguardando Pagamento", StatusPedido.AGUARDANDO_PAGAMENTO.getDescricao());
        assertEquals("Recebido", StatusPedido.RECEBIDO.getDescricao());
        assertEquals("Em preparação", StatusPedido.EM_PREPARACAO.getDescricao());
        assertEquals("Pronto", StatusPedido.PRONTO.getDescricao());
        assertEquals("Finalizado", StatusPedido.FINALIZADO.getDescricao());
        assertEquals("CANCELADO", StatusPedido.CANCELADO.getDescricao());
    }
}
