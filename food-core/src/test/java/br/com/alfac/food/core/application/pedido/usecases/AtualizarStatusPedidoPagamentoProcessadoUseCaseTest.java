package br.com.alfac.food.core.application.pedido.usecases;

import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.domain.pagamento.StatusPagamento;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.domain.pedido.StatusPedido;
import br.com.alfac.food.core.exception.FoodException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.PedidoHelper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AtualizarStatusPedidoPagamentoProcessadoUseCaseTest {

    @Mock
    private RepositorioPedidoGateway pedidoRepository;

    @InjectMocks
    private AtualizarStatusPedidoPagamentoProcessadoUseCase atualizarStatusPedidoPagamentoProcessadoUseCase;

    @Test
    void deveLancarExcecaoQuandoPedidoNaoEncontrado() throws FoodException {
        // Arrange
        Long pedidoId = 1L;
        when(pedidoRepository.consultarPedidoPorId(pedidoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> atualizarStatusPedidoPagamentoProcessadoUseCase.executar(pedidoId, StatusPagamento.APROVADO))
                .isInstanceOf(FoodException.class)
                .hasMessage("Pedido não encontrado");
    }

    @Test
    void deveAtualizarStatusPedidoPagamentoCancelado() throws FoodException {
        // Arrange
        var pedido = PedidoHelper.criarPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
        var statusPagamento = StatusPagamento.CANCELADO;
        when(pedidoRepository.consultarPedidoPorId(pedido.getId())).thenReturn(Optional.of(pedido));
        when(pedidoRepository.atualizarStatusPedido(pedido.getId(), StatusPedido.CANCELADO)).thenReturn(pedido);

        // Act
        Pedido pedidoAtualizado = atualizarStatusPedidoPagamentoProcessadoUseCase.executar(pedido.getId(), statusPagamento);

        // Assert
        assertThat(pedidoAtualizado.getStatus()).isEqualTo(StatusPedido.CANCELADO);
    }

    @Test
    void deveAtualizarStatusPedidoPagamentoProcessado() throws FoodException {
        // Arrange
        var pedido = PedidoHelper.criarPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
        var statusPagamento = StatusPagamento.APROVADO;
        when(pedidoRepository.consultarPedidoPorId(pedido.getId())).thenReturn(Optional.of(pedido));
        when(pedidoRepository.atualizarStatusPedido(pedido.getId(), StatusPedido.RECEBIDO)).thenReturn(pedido);

        // Act
        Pedido pedidoAtualizado = atualizarStatusPedidoPagamentoProcessadoUseCase.executar(pedido.getId(), statusPagamento);

        // Assert
        assertThat(pedidoAtualizado.getStatus()).isEqualTo(StatusPedido.RECEBIDO);
    }
}
