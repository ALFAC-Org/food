package br.com.alfac.food.core.application.pedido.usecases;

import br.com.alfac.food.core.application.pedido.dto.PedidoDTO;
import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.application.pedido.adapters.mappers.PedidoMapper;
import br.com.alfac.food.core.domain.pedido.*;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.exception.pedido.PedidoErros;

import java.util.List;
import java.util.Optional;

public class PedidoUseCase {

    private final RepositorioPedidoGateway pedidoRepository;


    public PedidoUseCase(
            final RepositorioPedidoGateway pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoDTO> listarPedidosPorStatus(final StatusPedido status) {

        List<Pedido> pedidos = pedidoRepository.listarPedidosPorStatus(status);

        return PedidoMapper.mapearParaListaPedidoDTO(pedidos);
    }
}
