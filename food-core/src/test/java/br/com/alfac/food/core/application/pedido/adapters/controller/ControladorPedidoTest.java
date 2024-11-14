package br.com.alfac.food.core.application.pedido.adapters.controller;

import br.com.alfac.food.core.application.cliente.usecases.ConsultarClientePorIdUseCase;
import br.com.alfac.food.core.application.pagamento.usecases.CriarPagamentoPendenteUseCase;
import br.com.alfac.food.core.application.pagamento.usecases.CriarQrCodePagamento;
import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.application.pedido.dto.PedidoCriadoDTO;
import br.com.alfac.food.core.application.pedido.usecases.*;
import br.com.alfac.food.core.domain.pedido.StatusPedido;
import br.com.alfac.food.core.exception.FoodException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.PagamentoHelper;
import utils.PedidoDTOHelper;
import utils.PedidoHelper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ControladorPedidoTest {

    @Mock
    private CriarPedidoUseCase criarPedidoUseCase;

    @Mock
    private ConsultarClientePorIdUseCase consultarClientePorIdUseCase;

    @Mock
    private CriarPagamentoPendenteUseCase criarPagamentoPendenteUseCase;

    @Mock
    private CriarQrCodePagamento criarQrCodePagamento;

    @Mock
    private ListarPedidosOrdenadosUseCase listarPedidosOrdenadosUseCase;

    @Mock
    private ConsultarPedidoPorIdUseCase consultarPedidoPorIdUseCase;

    @Mock
    private AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;

    @Mock
    private ListarPedidosPorStatusUseCase listarPedidosPorStatusUseCase;

    @Mock
    private RepositorioPedidoGateway repositorioPedidoGateway;

    @InjectMocks
    private ControladorPedido controladorPedido;

    @Mock
    private FoodException foodException;

    @Nested
    class CriarPedido {
        @Test
        public void deveCriarPedido() throws FoodException {
//            Arrange
            var pedido = PedidoHelper.criarPedido();
            var pagamento = PagamentoHelper.criarPagamento();
            var pedidoDTO = PedidoDTOHelper.criarPedidoDTO();
            when(criarPedidoUseCase.executar(pedidoDTO)).thenReturn(pedido);
            when(criarPagamentoPendenteUseCase.executar(anyLong())).thenReturn(pagamento);
            when(criarQrCodePagamento.executar(anyLong())).thenReturn("qrCode");

//            Act
            var result = controladorPedido.criarPedido(pedidoDTO);

//            Assert
            assertThat(result)
                    .isNotNull()
                    .isInstanceOf(PedidoCriadoDTO.class);
        }

        @Test
        public void deveLancarExcecaoAoCriarPedido() throws FoodException {
            // Arrange
            var pedidoDTO = PedidoDTOHelper.criarPedidoDTO();
            when(criarPedidoUseCase.executar(pedidoDTO)).thenThrow(foodException);
            when(foodException.getMessage()).thenReturn("Erro ao criar pedido");

            // Act
            assertThatThrownBy(() -> controladorPedido.criarPedido(pedidoDTO))
                    .isInstanceOf(FoodException.class)
                    .hasMessage("Erro ao criar pedido");
        }
    }

    @Nested
    class ListarPedidos {
        @Test
        public void deveListarPedidos() throws FoodException {
            // Arrange
            var pedidos = List.of(PedidoHelper.criarPedido());
            when(listarPedidosOrdenadosUseCase.executar()).thenReturn(pedidos);

            // Act
            var result = controladorPedido.listarPedidos();

            // Assert
            assertThat(result)
                    .isNotNull()
                    .isNotEmpty();
        }
    }

    @Nested
    class ConsultarPedidoPorId {
        @Test
        public void deveConsultarPedidoPorId() throws FoodException {
            // Arrange
            var pedido = PedidoHelper.criarPedido();
            when(consultarPedidoPorIdUseCase.executar(anyLong())).thenReturn(pedido);

            // Act
            var result = controladorPedido.consultarPedidoPorId(1L);

            // Assert
            assertThat(result)
                    .isNotNull();
        }

        @Test
        public void deveLancarExcecaoAoConsultarPedidoPorId() throws FoodException {
            // Arrange
            when(consultarPedidoPorIdUseCase.executar(anyLong())).thenThrow(foodException);
            when(foodException.getMessage()).thenReturn("Erro ao consultar pedido");

            // Act
            assertThatThrownBy(() -> controladorPedido.consultarPedidoPorId(1L))
                    .isInstanceOf(FoodException.class)
                    .hasMessage("Erro ao consultar pedido");
        }
    }

    @Nested
    class AtualizarStatusPedido {
        @Test
        public void deveAtualizarStatusPedido() throws FoodException {
            // Arrange
            var pedido = PedidoHelper.criarPedido();
            when(atualizarStatusPedidoUseCase.executar(anyLong())).thenReturn(pedido);

            // Act
            var result = controladorPedido.atualizarStatusPedido(1L);

            // Assert
            assertThat(result)
                    .isNotNull();
        }
    }

    @Nested
    class ListarPedidosPorStatus {
        @Test
        public void deveListarPedidosPorStatus() throws FoodException {
            // Arrange
            var pedidos = List.of(PedidoHelper.criarPedido());
            var status = StatusPedido.AGUARDANDO_PAGAMENTO;
            when(listarPedidosPorStatusUseCase.executar(status)).thenReturn(pedidos);

            // Act
            var result = controladorPedido.listarPedidosPorStatus(status);

            // Assert
            assertThat(result)
                    .isNotNull()
                    .isNotEmpty();
        }
    }
}
