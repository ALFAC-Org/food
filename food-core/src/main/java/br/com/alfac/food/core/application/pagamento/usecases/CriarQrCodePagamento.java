package br.com.alfac.food.core.application.pagamento.usecases;

import br.com.alfac.food.core.application.pagamento.adapters.gateways.PagamentoClientGateway;
import br.com.alfac.food.core.exception.FoodException;

public class CriarQrCodePagamento {


    private final PagamentoClientGateway pagamentoClientGateway;

    public CriarQrCodePagamento(final PagamentoClientGateway pagamentoClientGateway) {
        this.pagamentoClientGateway = pagamentoClientGateway;
    }


    public String executar(final Long idPedido) throws FoodException {
        return pagamentoClientGateway.gerarQrCode(idPedido);
    }
}
