package br.com.alfac.food.infra.pedido.handler;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.alfac.food.core.application.pedido.adapters.controller.ControladorPedido;
import br.com.alfac.food.core.application.pedido.dto.PedidoDTO;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.exception.pedido.PedidoErros;

@ExtendWith(MockitoExtension.class)
public class PedidoHandlerTest {

    @Mock
    private ControladorPedido controladorPedido;

    @InjectMocks
    private PedidoHandler pedidoHandler;

    @Test
    public void testListarPedidos() {
        PedidoDTO pedido1 = new PedidoDTO();
        PedidoDTO pedido2 = new PedidoDTO();
        List<PedidoDTO> expectedPedidos = Arrays.asList(pedido1, pedido2);

        when(controladorPedido.listarPedidos()).thenReturn(expectedPedidos);

        List<PedidoDTO> actualPedidos = pedidoHandler.listarPedidos();

        assertEquals(expectedPedidos, actualPedidos);
    }

    @Test
    public void testConsultarPedidoPorId() throws FoodException {
        Long pedidoId = 1L;
        PedidoDTO expectedPedido = new PedidoDTO();

        when(controladorPedido.consultarPedidoPorId(pedidoId)).thenReturn(expectedPedido);

        ResponseEntity<PedidoDTO> response = pedidoHandler.consultarPedidoPorId(pedidoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPedido, response.getBody());
    }

    @Test
    public void testConsultarPedidoPorIdNotFound() throws FoodException {
        Long pedidoId = 1L;

        when(controladorPedido.consultarPedidoPorId(pedidoId)).thenThrow(new FoodException(PedidoErros.PEDIDO_NAO_ENCONTRADO));

        FoodException exception = assertThrows(FoodException.class, () -> {
            pedidoHandler.consultarPedidoPorId(pedidoId);
        });

        assertEquals("Pedido não encontrado", exception.getMessage());
    }
}
