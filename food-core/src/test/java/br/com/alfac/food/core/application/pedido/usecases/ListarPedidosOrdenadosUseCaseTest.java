package br.com.alfac.food.core.application.pedido.usecases;

import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.domain.pedido.StatusPedido;
import br.com.alfac.food.core.exception.FoodException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.PedidoHelper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarPedidosOrdenadosUseCaseTest {

    private ListarPedidosOrdenadosUseCase listarPedidosOrdenadosUseCase;

    @Mock
    private RepositorioPedidoGateway pedidoRepository;

    @BeforeEach
    public void setUp() {
        listarPedidosOrdenadosUseCase = new ListarPedidosOrdenadosUseCase(pedidoRepository);
    }

    @Test
    public void deveListarPedidosOrdenados() throws FoodException {
        List<Pedido> pedidos = List.of(
                PedidoHelper.criarPedido(StatusPedido.PRONTO),
                PedidoHelper.criarPedido(StatusPedido.EM_PREPARACAO),
                PedidoHelper.criarPedido(StatusPedido.RECEBIDO),
                PedidoHelper.criarPedido(StatusPedido.CANCELADO),
                PedidoHelper.criarPedido(StatusPedido.FINALIZADO)
        );

        when(pedidoRepository.listarPedidos()).thenReturn(pedidos);

        List<Pedido> pedidosOrdenados = listarPedidosOrdenadosUseCase.executar();

        assertThat(pedidosOrdenados)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);

        assertThat(pedidosOrdenados.get(0).getStatus()).isEqualTo(StatusPedido.PRONTO);
        assertThat(pedidosOrdenados.get(1).getStatus()).isEqualTo(StatusPedido.EM_PREPARACAO);
        assertThat(pedidosOrdenados.get(2).getStatus()).isEqualTo(StatusPedido.RECEBIDO);
    }
}
