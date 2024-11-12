package br.com.alfac.food.core.application.pedido.usecases;

import br.com.alfac.food.core.application.cliente.usecases.ConsultarClientePorIdUseCase;
import br.com.alfac.food.core.application.item.usecases.ConsultarItemPorIdUseCase;
import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.application.pedido.adapters.mappers.PedidoMapper;
import br.com.alfac.food.core.application.pedido.dto.PedidoDTO;
import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.exception.FoodException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import utils.PedidoHelper;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CriarPedidoUseCaseTest {

    @Mock
    private RepositorioPedidoGateway repositorioPedidoGateway;

    @Mock
    private ConsultarClientePorIdUseCase consultarClientePorIdUseCase;

    @Mock
    private ConsultarItemPorIdUseCase consultarItemPorIdUseCase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void deveCriarPedidoComCliente() throws FoodException {
        // Arrange
        PedidoDTO pedidoDTO = criarPedidoDTO(true);

        // Act
        Pedido execute = new CriarPedidoUseCase(
                repositorioPedidoGateway,
                consultarClientePorIdUseCase,
                consultarItemPorIdUseCase
        )
                .executar(pedidoDTO);

        // Assert
        var pedidoRetornado = assertThat(execute)
                .isInstanceOf(Pedido.class);

        pedidoRetornado
                .extracting(p -> p.getCliente().getId())
                .isEqualTo(pedidoDTO.getClienteId());
    }

    @Test
    void deveCriarPedidoSemCliente() throws FoodException {
        // Arrange
        PedidoDTO pedidoDTO = criarPedidoDTO(false);

        // Act
        Pedido execute = new CriarPedidoUseCase(
                repositorioPedidoGateway,
                consultarClientePorIdUseCase,
                consultarItemPorIdUseCase
        )
                .executar(pedidoDTO);

        // Assert
        var pedidoRetornado = assertThat(execute)
                .isInstanceOf(Pedido.class);

        pedidoRetornado
                .extracting(Pedido::getCliente)
                .isNull();
    }

    PedidoDTO criarPedidoDTO(boolean comCliente) throws FoodException {
        var pedidoComCliente = comCliente ? PedidoHelper.criarPedidoComCliente() : PedidoHelper.criarPedido();
        var pedidoDTO = PedidoMapper.mapearParaPedidoDTO(pedidoComCliente);
        var combo = pedidoComCliente.getCombos().get(0);
        var lanche = combo.getLanche();
        var lancheComplementos = lanche.getComplementos();

        Map<Long, Item> itemMap = new HashMap<>();
        itemMap.put(combo.getAcompanhamento().getId(), combo.getAcompanhamento());
        itemMap.put(lanche.getId(), lanche);
        itemMap.put(combo.getBebida().getId(), combo.getBebida());
        itemMap.put(combo.getSobremesa().getId(), combo.getSobremesa());
        itemMap.put(lancheComplementos.get(0).getId(), lancheComplementos.get(0));

        if (comCliente) {
            when(consultarClientePorIdUseCase.execute(any(Long.class)))
                    .thenReturn(pedidoComCliente.getCliente());
        }

        when(repositorioPedidoGateway.registrarPedido(any(Pedido.class)))
                .thenReturn(pedidoComCliente);

        when(consultarItemPorIdUseCase.executar(any(Long.class)))
                .thenAnswer(invocation -> {
                    Long id = invocation.getArgument(0);

                    return itemMap.get(id);
                });
        when(consultarClientePorIdUseCase.execute(any(Long.class)))
                .thenReturn(pedidoComCliente.getCliente());

        when(repositorioPedidoGateway.registrarPedido(any(Pedido.class)))
                .thenReturn(pedidoComCliente);

        when(consultarItemPorIdUseCase.executar(any(Long.class)))
                .thenAnswer(invocation -> {
                    Long id = invocation.getArgument(0);

                    return itemMap.get(id);
                });

        return pedidoDTO;
    }

}