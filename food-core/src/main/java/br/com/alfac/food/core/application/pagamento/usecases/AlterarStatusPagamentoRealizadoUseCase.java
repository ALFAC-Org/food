package br.com.alfac.food.core.application.pagamento.usecases;

import br.com.alfac.food.core.application.pagamento.adapters.gateways.RepositorioPagamentoGateway;
import br.com.alfac.food.core.application.pagamento.adapters.mapper.PagamentoMapper;
import br.com.alfac.food.core.application.pagamento.dto.PagamentoResponse;
import br.com.alfac.food.core.domain.pagamento.Pagamento;
import br.com.alfac.food.core.domain.pagamento.StatusConfirmacaoPagamento;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.exception.pagamento.PagamentoErro;

import java.util.Optional;

public class AlterarStatusPagamentoRealizadoUseCase {

    private final RepositorioPagamentoGateway pagamentoRepository;

    public AlterarStatusPagamentoRealizadoUseCase(final RepositorioPagamentoGateway pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public Pagamento executar(final Long idPagamento, final StatusConfirmacaoPagamento statusConfirmacaoPagamento) throws FoodException {
        Optional<Pagamento> pagamentoOpt = pagamentoRepository.buscarPorId(idPagamento);

        Pagamento pagamento = pagamentoOpt.orElseThrow(() -> new FoodException(PagamentoErro.PAGAMENTO_NAO_ENCONTRADO));

        if (StatusConfirmacaoPagamento.APROVADO.equals(statusConfirmacaoPagamento)) {
            pagamento.aprovar();
        } else {
            pagamento.cancelar();
        }

        PagamentoResponse pagamentoEntityAtualizado = PagamentoMapper.toPagamentoEntityDTO(pagamento);

        return pagamentoRepository.atualizar(pagamentoEntityAtualizado);
    }
}
