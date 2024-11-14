package br.com.alfac.food.core.application.pedido.adapters.controller;

import br.com.alfac.food.core.application.pagamento.adapters.controller.ControladorRecebimentoPagamento;
import br.com.alfac.food.core.application.pagamento.usecases.AlterarStatusPagamentoRealizadoUseCase;
import br.com.alfac.food.core.application.pedido.usecases.AtualizarStatusPedidoPagamentoProcessadoUseCase;
import br.com.alfac.food.core.domain.pagamento.StatusConfirmacaoPagamento;
import br.com.alfac.food.core.exception.FoodException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.PagamentoHelper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControladorRecebimentoPagamentoTest {

    @Mock
    private AlterarStatusPagamentoRealizadoUseCase alterarStatusPagamentoRealizadoUseCase;

    @Mock
    private AtualizarStatusPedidoPagamentoProcessadoUseCase atualizarStatusPedidoPagamentoProcessadoUseCase;

    @InjectMocks
    private ControladorRecebimentoPagamento controladorRecebimentoPagamento;

    @Test
    void deveExecutarRetornoPagamento() throws FoodException {
        // Arrange
        var pagamento = PagamentoHelper.criarPagamento();
        when(alterarStatusPagamentoRealizadoUseCase.executar(anyLong(), any(StatusConfirmacaoPagamento.class))).thenReturn(pagamento);

        // Act
        controladorRecebimentoPagamento.executarRetornoPagamento(1L, StatusConfirmacaoPagamento.APROVADO);

        // Assert
        verify(atualizarStatusPedidoPagamentoProcessadoUseCase).executar(pagamento.getPedidoId(), pagamento.getStatusPagamento());
    }
}
