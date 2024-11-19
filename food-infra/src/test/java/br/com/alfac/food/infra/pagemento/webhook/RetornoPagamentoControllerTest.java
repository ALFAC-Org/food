package br.com.alfac.food.infra.pagemento.webhook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.alfac.food.core.application.pagamento.adapters.controller.ControladorRecebimentoPagamento;
import br.com.alfac.food.core.domain.pagamento.StatusConfirmacaoPagamento;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.exception.pagamento.PagamentoErro;
import br.com.alfac.food.infra.pagamento.webhook.RetornoPagamentoController;
import br.com.alfac.food.infra.pagamento.webhook.dto.RetornoPagamentoRequest;

public class RetornoPagamentoControllerTest {

    @Mock
    private ControladorRecebimentoPagamento controladorRecebimentoPagamento;

    @InjectMocks
    private RetornoPagamentoController retornoPagamentoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsultarPedidoPorStatus_Success() throws FoodException {
        RetornoPagamentoRequest request = new RetornoPagamentoRequest(123L, StatusConfirmacaoPagamento.APROVADO);
        doNothing().when(controladorRecebimentoPagamento).executarRetornoPagamento(123L,StatusConfirmacaoPagamento.APROVADO);

        ResponseEntity<Void> response = retornoPagamentoController.consultarPedidoPorStatus(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testConsultarPedidoPorStatus_FoodException() throws FoodException {
        RetornoPagamentoRequest request = new RetornoPagamentoRequest(123L, StatusConfirmacaoPagamento.APROVADO);
        doThrow(new FoodException(PagamentoErro.PAGAMENTO_NAO_ENCONTRADO)).when(controladorRecebimentoPagamento).executarRetornoPagamento(123L, StatusConfirmacaoPagamento.APROVADO);

        try {
            retornoPagamentoController.consultarPedidoPorStatus(request);
        } catch (FoodException e) {
            assertEquals("Pagamento não encontrado", e.getMessage());
        }
    }
}