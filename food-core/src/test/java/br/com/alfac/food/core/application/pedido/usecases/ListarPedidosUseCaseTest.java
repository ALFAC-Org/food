package br.com.alfac.food.core.application.pedido.usecases;

import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.exception.FoodException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.PedidoHelper;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarPedidosUseCaseTest {

    @Mock
    private RepositorioPedidoGateway pedidoRepository;

    @Test
    public void deveListarPedidos() throws FoodException {
        // Arrange
        List<Pedido> pedidos = PedidoHelper.criarPedidos(2);
        when(pedidoRepository.listarPedidos()).thenReturn(pedidos);

        // Act
        List<Pedido> execute = new ListarPedidosUseCase(pedidoRepository).executar();

        // Assert
        assertThat(execute).isNotNull();
        assertThat(execute).isNotEmpty();
        assertThat(execute).hasSize(pedidos.size());
    }
}
