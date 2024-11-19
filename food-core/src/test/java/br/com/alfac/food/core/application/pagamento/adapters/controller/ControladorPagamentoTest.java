package br.com.alfac.food.core.application.pagamento.adapters.controller;

import br.com.alfac.food.core.application.pagamento.dto.PagamentoResponse;
import br.com.alfac.food.core.application.pagamento.usecases.ConsultarPagementoPorPedidoIdUseCase;
import br.com.alfac.food.core.exception.FoodException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.PagamentoHelper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControladorPagamentoTest {

    @Mock
    ConsultarPagementoPorPedidoIdUseCase consultarPagementoPorPedidoIdUseCase;

    @InjectMocks
    ControladorPagamento controladorPagamento;

    @Test
    void deveConsultarPagamentoPorPedidoId() throws FoodException {
//        Arrange
        when(consultarPagementoPorPedidoIdUseCase.executar(anyLong())).thenReturn(PagamentoHelper.criarPagamento());

//        Act
        var result = controladorPagamento.consultarPagamentoPorPedidoId(1L);

//        Assert
        var pagamentoRetornado = assertThat(result);
        pagamentoRetornado
                .isInstanceOf(PagamentoResponse.class);
    }
}
