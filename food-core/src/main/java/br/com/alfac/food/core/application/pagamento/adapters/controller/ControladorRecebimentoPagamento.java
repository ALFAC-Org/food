package br.com.alfac.food.core.application.pagamento.adapters.controller;

import br.com.alfac.food.core.application.pagamento.usecases.AlterarStatusPagamentoRealizadoUseCase;
import br.com.alfac.food.core.application.pedido.usecases.AtualizarStatusPedidoPagamentoProcessadoUseCase;
import br.com.alfac.food.core.domain.pagamento.Pagamento;
import br.com.alfac.food.core.domain.pagamento.StatusConfirmacaoPagamento;
import br.com.alfac.food.core.exception.FoodException;

public class ControladorRecebimentoPagamento {
    AlterarStatusPagamentoRealizadoUseCase alterarStatusPagamentoRealizadoUseCase;
    AtualizarStatusPedidoPagamentoProcessadoUseCase atualizarStatusPedidoPagamentoProcessadoUseCase;

    public ControladorRecebimentoPagamento(
            final AlterarStatusPagamentoRealizadoUseCase alterarStatusPagamentoRealizadoUseCase,
            final AtualizarStatusPedidoPagamentoProcessadoUseCase atualizarStatusPedidoPagamentoProcessadoUseCase
    ) {
        this.alterarStatusPagamentoRealizadoUseCase = alterarStatusPagamentoRealizadoUseCase;
        this.atualizarStatusPedidoPagamentoProcessadoUseCase = atualizarStatusPedidoPagamentoProcessadoUseCase;
    }

    public void executarRetornoPagamento(final Long idPagamento, final StatusConfirmacaoPagamento statusConfirmacaoPagamento) throws FoodException {
        Pagamento pagamento = this.alterarStatusPagamentoRealizadoUseCase.executar(idPagamento, statusConfirmacaoPagamento);

        atualizarStatusPedidoPagamentoProcessadoUseCase.executar(pagamento.getPedidoId(), pagamento.getStatusPagamento());
    }
}
