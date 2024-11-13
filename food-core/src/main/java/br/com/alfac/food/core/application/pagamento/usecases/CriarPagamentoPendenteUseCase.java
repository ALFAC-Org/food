package br.com.alfac.food.core.application.pagamento.usecases;

import br.com.alfac.food.core.application.pagamento.adapters.gateways.RepositorioPagamentoGateway;
import br.com.alfac.food.core.application.pagamento.adapters.mapper.PagamentoMapper;
import br.com.alfac.food.core.domain.pagamento.Pagamento;
import br.com.alfac.food.core.exception.FoodException;

public class CriarPagamentoPendenteUseCase {

    private final RepositorioPagamentoGateway repositorioPagamentoGateway;

    public CriarPagamentoPendenteUseCase(final RepositorioPagamentoGateway repositorioPagamentoGateway) {
        this.repositorioPagamentoGateway = repositorioPagamentoGateway;
    }

    public Pagamento executar(final Long idPedido) throws FoodException {
        Pagamento pagamento = new Pagamento(idPedido);

        return repositorioPagamentoGateway.criar(PagamentoMapper.toPagamentoEntityDTO(pagamento));
    }
}
