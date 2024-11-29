package br.com.alfac.food.core.application.pedido.usecases;


import br.com.alfac.food.core.application.item.adapters.gateways.RepositorioItemGateway;
import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.domain.pedido.Pedido;
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
public class ConsultarPedidoPorIdUseCaseTest {

    @Mock
    private RepositorioPedidoGateway pedidoRepository;

    @Mock
    private RepositorioItemGateway repositorioItemGateway;

    @InjectMocks
    private ConsultarPedidoPorIdUseCase consultarPedidoPorIdUseCase;

    @Test
    void deveLancarExcecaoQuandoPedidoNaoEncontrado() throws FoodException {
        // Arrange
        Long pedidoId = 1L;
        when(pedidoRepository.consultarPedidoPorId(pedidoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> consultarPedidoPorIdUseCase.executar(pedidoId))
                .isInstanceOf(FoodException.class)
                .hasMessage("Pedido não encontrado");
    }

    @Test
    void deveRetornarPedidoQuandoEncontrado() throws FoodException {
        // Arrange
        Pedido pedido = PedidoHelper.criarPedido();
        Long pedidoId = pedido.getId();
        when(pedidoRepository.consultarPedidoPorId(pedidoId)).thenReturn(Optional.of(pedido));

        // Act
        Pedido result = consultarPedidoPorIdUseCase.executar(pedidoId);

        // Assert
        assertThat(result).isEqualTo(pedido);
    }
}