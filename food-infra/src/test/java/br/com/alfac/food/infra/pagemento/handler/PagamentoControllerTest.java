package br.com.alfac.food.infra.pagemento.handler;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.alfac.food.core.application.pagamento.adapters.controller.ControladorPagamento;
import br.com.alfac.food.core.application.pagamento.dto.PagamentoResponse;
import br.com.alfac.food.core.domain.pagamento.StatusPagamento;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.exception.pagamento.PagamentoErro;
import br.com.alfac.food.infra.pagamento.handler.PagamentoController;

@ExtendWith(MockitoExtension.class)
public class PagamentoControllerTest {

    @Mock
    private ControladorPagamento controladorPagamento;

    @InjectMocks
    private PagamentoController pagamentoController;

    @Test
    public void testConsultarStatusPedidoPorPedidoId() throws FoodException {
        PagamentoResponse pagamentoResponse = new PagamentoResponse(1L, StatusPagamento.APROVADO, 1L, LocalDateTime.now());

        when(controladorPagamento.consultarPagamentoPorPedidoId(anyLong())).thenReturn(pagamentoResponse);

        ResponseEntity<PagamentoResponse> response = pagamentoController.consultarStatusPedidoPorPedidoId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagamentoResponse, response.getBody());
    }

    @Test
    public void testConsultarStatusPedidoPorPedidoIdNotFound() throws FoodException {
        when(controladorPagamento.consultarPagamentoPorPedidoId(anyLong())).thenThrow(new FoodException(PagamentoErro.PAGAMENTO_NAO_ENCONTRADO));

        try {
            pagamentoController.consultarStatusPedidoPorPedidoId(1L);
        } catch (FoodException e) {
            assertEquals("Pagamento não encontrado", e.getMessage());
        }
    }
}