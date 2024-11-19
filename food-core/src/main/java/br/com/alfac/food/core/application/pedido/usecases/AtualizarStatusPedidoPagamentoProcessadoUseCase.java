package br.com.alfac.food.core.application.pedido.usecases;

import java.util.Optional;

import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.domain.pagamento.StatusPagamento;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.exception.pedido.PedidoErros;


/**
 * Caso de uso para atualizar o status do pedido conforme retorno do processador de pagamento (APROVADO ou REPROVADO)

 */
public class AtualizarStatusPedidoPagamentoProcessadoUseCase {

    private final RepositorioPedidoGateway pedidoRepository;

    public AtualizarStatusPedidoPagamentoProcessadoUseCase(final RepositorioPedidoGateway pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido executar(final Long idPedido, final StatusPagamento statusPagamento) throws FoodException {
        Optional<Pedido> pedidoOpt = pedidoRepository.consultarPedidoPorId(idPedido);

        if (pedidoOpt.isEmpty()) {
            throw new FoodException(PedidoErros.PEDIDO_NAO_ENCONTRADO);
        }

        Pedido pedido = pedidoOpt.get();

        if (StatusPagamento.CANCELADO.equals(statusPagamento)) {
            pedido.cancelar();
        } else {
            pedido.atualizarStatusRecebido();
        }

        return pedidoRepository.atualizarStatusPedido(pedido.getId(), pedido.getStatus());
    }
}
