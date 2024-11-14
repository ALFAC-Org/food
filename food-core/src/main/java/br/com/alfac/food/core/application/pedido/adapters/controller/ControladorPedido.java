package br.com.alfac.food.core.application.pedido.adapters.controller;

import br.com.alfac.food.core.application.cliente.adapters.gateways.RepositorioClienteGateway;
import br.com.alfac.food.core.application.cliente.usecases.ConsultarClientePorIdUseCase;
import br.com.alfac.food.core.application.item.adapters.gateways.RepositorioItemGateway;
import br.com.alfac.food.core.application.item.usecases.ConsultarItemPorIdUseCase;
import br.com.alfac.food.core.application.pagamento.adapters.gateways.PagamentoClientGateway;
import br.com.alfac.food.core.application.pagamento.adapters.gateways.RepositorioPagamentoGateway;
import br.com.alfac.food.core.application.pagamento.usecases.CriarPagamentoPendenteUseCase;
import br.com.alfac.food.core.application.pagamento.usecases.CriarQrCodePagamento;
import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.application.pedido.adapters.presenter.PedidoPresenter;
import br.com.alfac.food.core.application.pedido.dto.PedidoCriadoDTO;
import br.com.alfac.food.core.application.pedido.dto.PedidoDTO;
import br.com.alfac.food.core.application.pedido.usecases.*;
import br.com.alfac.food.core.domain.pagamento.Pagamento;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.domain.pedido.StatusPedido;
import br.com.alfac.food.core.exception.FoodException;

import java.util.List;
import java.util.Map;

public class ControladorPedido {

    private final ConsultarClientePorIdUseCase consultarClientePorIdUseCase;
    private final CriarPedidoUseCase criarPedidoUseCase;
    private final CriarPagamentoPendenteUseCase criarPagamentoPendenteUseCase;
    private final CriarQrCodePagamento criarQrCodePagamento;
    private final ListarPedidosOrdenadosUseCase listarPedidosOrdenadosUseCase;
    private final ConsultarPedidoPorIdUseCase consultarPedidoPorIdUseCase;
    private final AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;
    private final ListarPedidosPorStatusUseCase listarPedidosPorStatusUseCase;
    private final RepositorioPedidoGateway repositorioPedidoGateway;

    public ControladorPedido(final ConsultarClientePorIdUseCase consultarClientePorIdUseCase,
                             final CriarPedidoUseCase criarPedidoUseCase,
                             final CriarPagamentoPendenteUseCase criarPagamentoPendenteUseCase,
                             final CriarQrCodePagamento criarQrCodePagamento,
                             final ListarPedidosOrdenadosUseCase listartPedidosOrdenadosUseCase,
                             final ConsultarPedidoPorIdUseCase consultarPedidoPorIdUseCase,
                             final AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase,
                             final ListarPedidosPorStatusUseCase listarPedidosPorStatusUseCase,
                             final RepositorioPedidoGateway repositorioPedidoGateway) {
        this.consultarClientePorIdUseCase = consultarClientePorIdUseCase;
        this.criarPedidoUseCase = criarPedidoUseCase;
        this.criarPagamentoPendenteUseCase = criarPagamentoPendenteUseCase;
        this.criarQrCodePagamento = criarQrCodePagamento;
        this.listarPedidosOrdenadosUseCase = listartPedidosOrdenadosUseCase;
        this.consultarPedidoPorIdUseCase = consultarPedidoPorIdUseCase;
        this.atualizarStatusPedidoUseCase = atualizarStatusPedidoUseCase;
        this.listarPedidosPorStatusUseCase = listarPedidosPorStatusUseCase;
        this.repositorioPedidoGateway = repositorioPedidoGateway;
    }

    public PedidoCriadoDTO criarPedido(PedidoDTO pedidoDTO) throws FoodException {
        Pedido pedido = criarPedidoUseCase.executar(pedidoDTO);

        Pagamento pagamento = criarPagamentoPendenteUseCase.executar(pedido.getId());

        String qrCodeParaPagamento = criarQrCodePagamento.executar(pagamento.getId());

        return PedidoPresenter.mapearParaPedidoCriadoDTO(pedido, qrCodeParaPagamento);
    }

    public List<PedidoDTO> listarPedidos() {
        List<Pedido> pedidos = listarPedidosOrdenadosUseCase.executar();

        return PedidoPresenter.mapearParaPedidoDTO(pedidos);
    }

    public PedidoDTO consultarPedidoPorId(final Long id) throws FoodException {
        Pedido pedido = consultarPedidoPorIdUseCase.executar(id);

        return PedidoPresenter.mapearParaPedidoDTO(pedido);
    }

    public PedidoDTO atualizarStatusPedido(final Long id) throws FoodException {
        Pedido pedido = atualizarStatusPedidoUseCase.executar(id);

        return PedidoPresenter.mapearParaPedidoDTO(pedido);
    }

    public List<PedidoDTO> listarPedidosPorStatus(final StatusPedido status) {
        List<Pedido> pedidos = listarPedidosPorStatusUseCase.executar(status);

        return PedidoPresenter.mapearParaPedidoDTO(pedidos);
    }
}
