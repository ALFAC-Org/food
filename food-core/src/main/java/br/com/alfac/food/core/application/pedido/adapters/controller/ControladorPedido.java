package br.com.alfac.food.core.application.pedido.adapters.controller;

import br.com.alfac.food.core.application.cliente.adapters.gateways.RepositorioClienteGateway;
import br.com.alfac.food.core.application.item.adapters.gateways.RepositorioItemGateway;
import br.com.alfac.food.core.application.pagamento.adapters.gateways.PagamentoClientGateway;
import br.com.alfac.food.core.application.pagamento.adapters.gateways.RepositorioPagamentoGateway;
import br.com.alfac.food.core.application.pagamento.usecases.CriarPagamentoPendenteUserCase;
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

public class ControladorPedido {

    private final CriarPedidoUserCase criarPedidoUserCase;
    private final CriarPagamentoPendenteUserCase criarPagamentoPendenteUserCase;
    private final CriarQrCodePagamento criarQrCodePagamento;
    private final RepositorioPedidoGateway repositorioPedidoGateway;

    public ControladorPedido(final RepositorioPedidoGateway repositorioPedidoGateway, final RepositorioClienteGateway clienteRepository,
                             final RepositorioItemGateway itemRepository,
                             final RepositorioPagamentoGateway repositorioPagamentoGateway,
                             final PagamentoClientGateway pagamentoClientGateway) {
        this.repositorioPedidoGateway = repositorioPedidoGateway;
        this.criarPedidoUserCase = new CriarPedidoUserCase(repositorioPedidoGateway, clienteRepository, itemRepository);
        this.criarPagamentoPendenteUserCase = new CriarPagamentoPendenteUserCase(repositorioPagamentoGateway);
        this.criarQrCodePagamento = new CriarQrCodePagamento(pagamentoClientGateway);
    }

    public PedidoCriadoDTO criarPedido(PedidoDTO pedidoDTO) throws FoodException {

        Pedido pedido = criarPedidoUserCase.executar(pedidoDTO);

        Pagamento pagamento = criarPagamentoPendenteUserCase.executar(pedido.getId());

        String qrCodeParaPagamento = criarQrCodePagamento.executar(pagamento.getId());

        return PedidoPresenter.mapearParaPedidoCriadoDTO(pedido, qrCodeParaPagamento);
    }

    public List<PedidoDTO> listarPedidos() {
        ListarPedidosOrdenadosUseCase listarPedidosOrdenadosUseCase = new ListarPedidosOrdenadosUseCase(this.repositorioPedidoGateway);
        List<Pedido> pedidos = listarPedidosOrdenadosUseCase.executar();
        return PedidoPresenter.mapearParaPedidoDTO(pedidos);
    }

    public PedidoDTO consultarPedidoPorId(final Long id) throws FoodException {

        ConsultarPedidoPorIdUseCase consultarPedidoPorIdUseCase = new ConsultarPedidoPorIdUseCase(this.repositorioPedidoGateway);

        Pedido pedido = consultarPedidoPorIdUseCase.executar(id);

        return PedidoPresenter.mapearParaPedidoDTO(pedido);

    }

    public PedidoDTO atualizarStatusPedido(final Long id) throws FoodException {
        AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase = new AtualizarStatusPedidoUseCase(this.repositorioPedidoGateway);
        Pedido pedido = atualizarStatusPedidoUseCase.executar(id);

        return PedidoPresenter.mapearParaPedidoDTO(pedido);
    }

    public List<PedidoDTO> listarPedidosPorStatus(final StatusPedido status) {
        ListarPedidosPorStatusUseCase listarPedidosPorStatusUseCase = new ListarPedidosPorStatusUseCase(this.repositorioPedidoGateway);
        List<Pedido> pedidos = listarPedidosPorStatusUseCase.executar(status);
        return PedidoPresenter.mapearParaPedidoDTO(pedidos);


    }
}
