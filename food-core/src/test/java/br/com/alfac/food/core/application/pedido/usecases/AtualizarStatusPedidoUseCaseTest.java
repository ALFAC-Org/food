package br.com.alfac.food.core.application.pedido.usecases;

import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.domain.pedido.StatusPedido;
import br.com.alfac.food.core.exception.FoodException;
import org.junit.jupiter.api.BeforeEach;
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
public class AtualizarStatusPedidoUseCaseTest {

    @Mock
    private RepositorioPedidoGateway pedidoRepository;

    @InjectMocks
    private ConsultarPedidoPorIdUseCase consultarPedidoPorIdUseCase;

    @InjectMocks
    private AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;

    @Test
    void deveLancarExcecaoQuandoPedidoNaoEncontrado() throws FoodException {
        // Arrange
        Long pedidoId = 1L;
        when(pedidoRepository.consultarPedidoPorId(pedidoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> atualizarStatusPedidoUseCase.executar(pedidoId))
                .isInstanceOf(FoodException.class)
                .hasMessage("Pedido não encontrado");
    }

    @Test
    void deveLancarExcecaoQuandoPedidoNaoPago() throws FoodException {
        // Arrange
        var pedido = PedidoHelper.criarPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
        when(pedidoRepository.consultarPedidoPorId(pedido.getId())).thenReturn(Optional.of(pedido));

        // Act & Assert
        assertThatThrownBy(() -> atualizarStatusPedidoUseCase.executar(pedido.getId()))
                .isInstanceOf(FoodException.class)
                .hasMessage("Pedido não pago.");
    }

    @Test
    void deveAtualizarStatusPedido() throws FoodException {
        // Arrange
        var pedido = PedidoHelper.criarPedido(StatusPedido.RECEBIDO);
        var pedidoAtualizado = PedidoHelper.criarPedido(StatusPedido.EM_PREPARACAO);
        when(pedidoRepository.consultarPedidoPorId(pedido.getId())).thenReturn(Optional.of(pedido));
        when(pedidoRepository.atualizarStatusPedido(pedido.getId(), StatusPedido.EM_PREPARACAO)).thenReturn(pedidoAtualizado);

        // Act
        var result = atualizarStatusPedidoUseCase.executar(pedido.getId());

        // Assert
        assertThat(result)
                .isEqualTo(pedidoAtualizado)
                .extracting("status").isEqualTo(StatusPedido.EM_PREPARACAO);
    }
}
