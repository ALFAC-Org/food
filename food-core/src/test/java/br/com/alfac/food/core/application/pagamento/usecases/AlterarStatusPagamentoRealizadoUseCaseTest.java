package br.com.alfac.food.core.application.pagamento.usecases;

import br.com.alfac.food.core.application.pagamento.adapters.gateways.RepositorioPagamentoGateway;
import br.com.alfac.food.core.application.pagamento.adapters.mapper.PagamentoMapper;
import br.com.alfac.food.core.domain.pagamento.Pagamento;
import br.com.alfac.food.core.domain.pagamento.StatusConfirmacaoPagamento;
import br.com.alfac.food.core.domain.pagamento.StatusPagamento;
import br.com.alfac.food.core.exception.FoodException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.PagamentoHelper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlterarStatusPagamentoRealizadoUseCaseTest {

    @Mock
    private RepositorioPagamentoGateway pagamentoRepository;

    @InjectMocks
    private AlterarStatusPagamentoRealizadoUseCase alterarStatusPagamentoRealizadoUseCase;

    @Test
    public void deveLancarExcecaoQuandoPagamentoNaoEncontrado() throws FoodException {
        // Act & Assert
        assertThatThrownBy(() -> alterarStatusPagamentoRealizadoUseCase.executar(1L, StatusConfirmacaoPagamento.APROVADO))
                .isInstanceOf(FoodException.class)
                .hasMessage("Pagamento não encontrado");
    }

    @Test
    public void deveAlterarStatusPagamentoAprovado() throws FoodException {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.now();
        Pagamento pagamento = PagamentoHelper.criarPagamento(1L, StatusPagamento.PENDENTE, 1L, dateTime);
        var pagamentoId = pagamento.getId();
        when(pagamentoRepository.buscarPorId(pagamentoId))
                .thenReturn(Optional.of(pagamento));
        Pagamento pagamentoAtualizado = PagamentoHelper.criarPagamento(1L, StatusPagamento.PENDENTE, 1L, dateTime);
        pagamentoAtualizado.aprovar();
        var pagamentoEntityDTO = PagamentoMapper.toPagamentoEntityDTO(pagamentoAtualizado);
        when(pagamentoRepository.atualizar(pagamentoEntityDTO))
                .thenReturn(pagamento);

        // Act
        alterarStatusPagamentoRealizadoUseCase.executar(pagamentoId, StatusConfirmacaoPagamento.APROVADO);

        // Assert
        assertThat(pagamento.getStatusPagamento())
                .isEqualTo(StatusPagamento.APROVADO);
    }

    @Test
    public void deveAlterarStatusPagamentoCancelado() throws FoodException {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.now();
        Pagamento pagamento = PagamentoHelper.criarPagamento(1L, StatusPagamento.PENDENTE, 1L, dateTime);
        var pagamentoId = pagamento.getId();
        when(pagamentoRepository.buscarPorId(pagamentoId))
                .thenReturn(Optional.of(pagamento));
        Pagamento pagamentoAtualizado = PagamentoHelper.criarPagamento(1L, StatusPagamento.PENDENTE, 1L, dateTime);
        pagamentoAtualizado.cancelar();
        var pagamentoEntityDTO = PagamentoMapper.toPagamentoEntityDTO(pagamentoAtualizado);
        when(pagamentoRepository.atualizar(pagamentoEntityDTO))
                .thenReturn(pagamento);

        // Act
        alterarStatusPagamentoRealizadoUseCase.executar(pagamentoId, StatusConfirmacaoPagamento.REPROVADO);

        // Assert
        assertThat(pagamento.getStatusPagamento())
                .isEqualTo(StatusPagamento.CANCELADO);
    }
}
