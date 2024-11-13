package br.com.alfac.food.core.application.pagamento.usecases;

import br.com.alfac.food.core.application.pagamento.adapters.gateways.RepositorioPagamentoGateway;
import br.com.alfac.food.core.exception.FoodException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.PagamentoHelper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CriarPagamentoPendenteUseCaseTest {

    @Mock
    private RepositorioPagamentoGateway pagamentoRepository;

    @InjectMocks
    private CriarPagamentoPendenteUseCase criarPagamentoPendenteUseCase;

    @Test
    public void deveCriarPagamentoPendente() throws FoodException {
//        Arrange
        var pagamento = PagamentoHelper.criarPagamento();
        when(pagamentoRepository.criar(any()))
                .thenReturn(pagamento);

//        Act
        var pagamentoRetornado = criarPagamentoPendenteUseCase.executar(pagamento.getPedidoId());

//        Assert
        assert pagamentoRetornado.equals(pagamento);
    }
}
