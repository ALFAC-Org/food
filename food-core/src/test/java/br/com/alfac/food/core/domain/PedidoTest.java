package br.com.alfac.food.core.domain;

import br.com.alfac.food.core.domain.cliente.Cliente;
import br.com.alfac.food.core.domain.pedido.Combo;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.domain.pedido.StatusPedido;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.exception.pedido.PedidoErros;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import utils.ComboHelper;
import utils.PedidoHelper;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    private Cliente cliente;
    private Pedido pedido;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        pedido = new Pedido(cliente);
    }

    @Nested
    class PedidoConstrutor {
        @Test
        public void deveLancarExcecaoQuandoIdForNulo() {
            FoodException exception = assertThrows(FoodException.class, () -> {
                new Pedido(null, new Cliente(), StatusPedido.AGUARDANDO_PAGAMENTO, new ArrayList<>(), LocalDateTime.now());
            });
            assertEquals("Id é obrigatório.", exception.getMessage());
        }

        @Test
        public void deveLancarExcecaoQuandoStatusForNulo() {
            FoodException exception = assertThrows(FoodException.class, () -> {
                new Pedido(1L, new Cliente(), null, new ArrayList<>(), LocalDateTime.now());
            });
            assertEquals("Status é obrigatório.", exception.getMessage());
        }

        @Test
        public void deveLancarExcecaoQuandoDataCadastroForNula() {
            FoodException exception = assertThrows(FoodException.class, () -> {
                new Pedido(1L, new Cliente(), StatusPedido.AGUARDANDO_PAGAMENTO, new ArrayList<>(), null);
            });
            assertEquals("Data de cadastro é obrigatória.", exception.getMessage());
        }
    }

    @Nested
    class atualizarStatus {
        @Test
        public void deveLancarExcecaoQuandoTentaAtualizarPedidoCancelado() throws FoodException {
            pedido = PedidoHelper.criarPedidoCancelado();
            FoodException exception = assertThrows(FoodException.class, pedido::atualizarStatus);
            assertEquals("Status do pedido cancelado não permite alteração.", exception.getMessage());
        }

        @Test
        public void deveLancarExcecaoQuandoTentaAtualizarPedidoFinalizado() throws FoodException {
            pedido = PedidoHelper.criarPedidoFinalizado();
            FoodException exception = assertThrows(FoodException.class, pedido::atualizarStatus);
            assertEquals("Status do pedido já finalizado não permite alteração.", exception.getMessage());
        }

        @Test
        public void deveAtualizarStatusDeRecebidoParaEmPreparacao() throws FoodException {
            pedido = PedidoHelper.criarPedido(StatusPedido.RECEBIDO);
            pedido.atualizarStatus();
            assertEquals(StatusPedido.EM_PREPARACAO, pedido.getStatus());
        }

        @Test
        public void deveAtualizarStatusDeEmPreparacaoParaPronto() throws FoodException {
            pedido = PedidoHelper.criarPedido(StatusPedido.EM_PREPARACAO);
            pedido.atualizarStatus();
            assertEquals(StatusPedido.PRONTO, pedido.getStatus());
        }

        @Test
        public void deveAtualizarStatusDeProntoParaFinalizado() throws FoodException {
            pedido = PedidoHelper.criarPedido(StatusPedido.EM_PREPARACAO);
            pedido.atualizarStatus();
            assertEquals(StatusPedido.PRONTO, pedido.getStatus());
        }
    }

    @Nested
    class atualizarStatusRecebido {
        @Test
        public void deveLancarExcecaoQuandoTentaAtualizarPedidoCancelado() throws FoodException {
            pedido = PedidoHelper.criarPedidoCancelado();
            FoodException exception = assertThrows(FoodException.class, pedido::atualizarStatusRecebido);
            assertEquals("Status do pedido cancelado não permite alteração.", exception.getMessage());
        }

        @Test
        public void deveLancarExcecaoQuandoTentaAtualizarPedidoPago() throws FoodException {
            pedido = PedidoHelper.criarPedidoRecebido();
            FoodException exception = assertThrows(FoodException.class, pedido::atualizarStatusRecebido);
            assertEquals("Pagamento já realizado", exception.getMessage());
        }

        @Test
        public void deveAtualizarStatusParaRecebido() throws FoodException {
            pedido = PedidoHelper.criarPedido();
            pedido.atualizarStatusRecebido();
            assertEquals(StatusPedido.RECEBIDO, pedido.getStatus());
        }
    }

    @Nested
    class cancelar {
        @Test
        public void deveLancarExcecaoQuandoTentaCancelarPedidoFinalizado() throws FoodException {
            pedido = PedidoHelper.criarPedidoFinalizado();
            FoodException exception = assertThrows(FoodException.class, pedido::cancelar);
            assertEquals("Status do pedido já finalizado não permite alteração.", exception.getMessage());
        }

        @Test
        public void deveCancelarPedido() throws FoodException {
            pedido = PedidoHelper.criarPedido();
            pedido.cancelar();
            assertEquals(StatusPedido.CANCELADO, pedido.getStatus());
        }
    }

    @Nested
    class adicionaCombo {
        @Test
        public void deveLancarExcecaoQuandoAdicionaComboNulo() {
            FoodException exception = assertThrows(FoodException.class, () -> pedido.adicionaCombo(null));
            assertEquals("Não é possível adicionar combo nulo.", exception.getMessage());
        }

        @Test
        public void deveAdicionarComboAoPedido() throws FoodException {
            Combo combo = new Combo();
            pedido.adicionaCombo(combo);
            assertTrue(pedido.getCombos().contains(combo));
        }
    }

    @Nested
    class getCliente {
        @Test
        public void deveRetornarCliente() {
            assertEquals(cliente, pedido.getCliente());
        }
    }

    @Nested
    class getCombos {
        @Test
        public void deveRetornarCombos() {
            assertEquals(new ArrayList<Combo>(), pedido.getCombos());
        }
    }

    @Nested
    class getId {
        @Test
        public void deveRetornarId() throws FoodException {
            var pedido = PedidoHelper.criarPedido();
            assertEquals(1L, pedido.getId());
        }
    }

    @Nested
    class getStatus {
        @Test
        public void deveRetornarStatus() throws FoodException {
            var pedido = PedidoHelper.criarPedido();
            assertEquals(StatusPedido.AGUARDANDO_PAGAMENTO, pedido.getStatus());
        }
    }

    @Nested
    class getValorTotal {
        @Test
        public void deveRetornarValorTotal() throws FoodException {
            var pedido = PedidoHelper.criarPedido();
            assertEquals(42, pedido.getValorTotal().intValue());
        }

        @Test
        public void deveRetornarValorTotalQuandoPedidoNaoTemCombos() throws FoodException {
            var pedido = PedidoHelper.criarPedido(1L, null, StatusPedido.AGUARDANDO_PAGAMENTO, new ArrayList<>(), LocalDateTime.now());
            assertEquals(0, pedido.getValorTotal().intValue());
        }
    }

    @Nested
    class getDataCadastro {
        @Test
        public void deveRetornarDataCadastro() throws FoodException {
            var clock = Clock.fixed(Instant.parse("2024-11-13T20:47:55.915060Z"), ZoneId.systemDefault());

            LocalDateTime dataCadastro = LocalDateTime.now(clock);
            var pedido = new Pedido(1L, null, StatusPedido.AGUARDANDO_PAGAMENTO, List.of(ComboHelper.criarCombo()), dataCadastro);
            assertEquals(dataCadastro, pedido.getDataCadastro());
        }
    }
}