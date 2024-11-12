package br.com.alfac.food.core.application.pedido.usecases;

import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.application.pedido.dto.PedidoDTO;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.domain.pedido.StatusPedido;
import br.com.alfac.food.core.exception.FoodException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.PedidoHelper;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PedidoUseCaseTest {

    @Mock
    private RepositorioPedidoGateway pedidoRepository;

    static Stream<StatusPedido> statusProvider() {
        return Stream.of(
                StatusPedido.AGUARDANDO_PAGAMENTO,
                StatusPedido.RECEBIDO,
                StatusPedido.EM_PREPARACAO,
                StatusPedido.PRONTO,
                StatusPedido.FINALIZADO
        );
    }

    @ParameterizedTest
    @MethodSource("statusProvider")
    public void deveListarPedidosPorStatus(StatusPedido status) throws FoodException {
        // Arrange
        List<Pedido> pedidos = List.of(PedidoHelper.criarPedido(status));
        when(pedidoRepository.listarPedidosPorStatus(status)).thenReturn(pedidos);

        // Act
        List<PedidoDTO> execute = new PedidoUseCase(pedidoRepository).listarPedidosPorStatus(status);

        // Assert
        assertThat(execute).isNotNull();
        assertThat(execute).isNotEmpty();
        assertThat(execute).hasSize(pedidos.size());
        assertThat(execute.get(0).getStatusPedido()).isEqualTo(status);
    }
}
