package br.com.alfac.food.core.application.pagamento.usecases;

import br.com.alfac.food.core.application.pagamento.adapters.gateways.RepositorioPagamentoGateway;
import br.com.alfac.food.core.domain.pagamento.Pagamento;
import br.com.alfac.food.core.exception.FoodException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.PagamentoHelper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsultarPagementoPorPedidoIdUseCaseTest {

    @Mock
    private RepositorioPagamentoGateway pagamentoRepository;

    @InjectMocks
    private ConsultarPagementoPorPedidoIdUseCase consultarPagementoPorPedidoIdUseCase;

    @Test
    public void deveLançarExcecaoQuandoPagamentoNaoEncontrado() throws FoodException {
//        Arrange
        when(pagamentoRepository.buscarPorPedidoId(1L))
                .thenReturn(Optional.empty());

//        Act & Assert
        assertThatThrownBy(() -> consultarPagementoPorPedidoIdUseCase.executar(1L))
                .isInstanceOf(FoodException.class)
                .hasMessage("Pagamento não encontrado");
    }

    @Test
    public void deveConsultarPagamentoPorPedidoId() throws FoodException {
//        Arrange
        Optional<Pagamento> pagamento = Optional.of(PagamentoHelper.criarPagamento());
        when(pagamentoRepository.buscarPorPedidoId(pagamento.get().getId()))
                .thenReturn(Optional.of(pagamento.get()));

//        Act
        var pagamentoRetornado = consultarPagementoPorPedidoIdUseCase.executar(pagamento.get().getId());

//        Assert
        assertThat(pagamentoRetornado)
                .isNotNull()
                .isEqualTo(pagamento.get());
    }
}
