package br.com.alfac.food.core.application.pedido.usecases;

import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.domain.pedido.StatusPedido;

import java.util.List;

public class ListarPedidosPorStatusUseCase {
    private final RepositorioPedidoGateway pedidoRepository;

    public ListarPedidosPorStatusUseCase(final RepositorioPedidoGateway pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> executar(final StatusPedido status) {
        return pedidoRepository.listarPedidosPorStatus(status);
    }
}
