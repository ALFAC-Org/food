package br.com.alfac.food.core.application.pedido.usecases;

import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.exception.pedido.PedidoErros;

import java.util.Optional;

public class ConsultarPedidoPorIdUseCase {
    private final RepositorioPedidoGateway pedidoRepository;

    public ConsultarPedidoPorIdUseCase(final RepositorioPedidoGateway pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido executar(Long id) throws FoodException {
        Optional<Pedido> pedidoOpt = pedidoRepository.consultarPedidoPorId(id);
        return pedidoOpt.orElseThrow(() -> new FoodException(PedidoErros.PEDIDO_NAO_ENCONTRADO));
    }
}
