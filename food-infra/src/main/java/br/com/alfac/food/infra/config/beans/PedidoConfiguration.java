package br.com.alfac.food.infra.config.beans;

import br.com.alfac.food.core.application.cliente.usecases.ConsultarClientePorIdUseCase;
import br.com.alfac.food.core.application.item.usecases.ConsultarItemPorIdUseCase;
import br.com.alfac.food.core.application.pagamento.usecases.CriarPagamentoPendenteUseCase;
import br.com.alfac.food.core.application.pagamento.usecases.CriarQrCodePagamento;
import br.com.alfac.food.core.application.pedido.adapters.controller.ControladorPedido;
import br.com.alfac.food.core.application.pedido.usecases.*;
import br.com.alfac.food.infra.cliente.gateways.RepositorioClienteGatewayClient;
import br.com.alfac.food.infra.item.gateways.RepositorioItemGatewayClient;
import br.com.alfac.food.infra.pagamento.client.PagamentoClientGatewayImpl;
import br.com.alfac.food.infra.pagamento.gateway.RepositorioPagamentoGatewayImpl;
import br.com.alfac.food.infra.pedido.gateways.RepositorioPedidoGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoConfiguration {


    @Bean
    public ControladorPedido pedidoService(final RepositorioPedidoGatewayImpl repositorioPedidoGateway,
                                           final RepositorioClienteGatewayClient repositorioClienteMySQLGateway,
                                           final RepositorioItemGatewayClient repositorioItemGateway,
                                           final RepositorioPagamentoGatewayImpl repositorioPagamentoGateway,
                                           final PagamentoClientGatewayImpl pagamentoClientGateway) {
        ConsultarClientePorIdUseCase consultarClientePorIdUseCase = new ConsultarClientePorIdUseCase(repositorioClienteMySQLGateway);
        ConsultarItemPorIdUseCase consultarItemPorIdUseCase = new ConsultarItemPorIdUseCase(repositorioItemGateway);
        CriarPedidoUseCase criarPedidoUseCase = new CriarPedidoUseCase(repositorioPedidoGateway, consultarClientePorIdUseCase, consultarItemPorIdUseCase);
        CriarPagamentoPendenteUseCase criarPagamentoPendenteUseCase = new CriarPagamentoPendenteUseCase(repositorioPagamentoGateway);
        CriarQrCodePagamento criarQrCodePagamento = new CriarQrCodePagamento(pagamentoClientGateway);
        ListarPedidosOrdenadosUseCase listarPedidosOrdenadosUseCase = new ListarPedidosOrdenadosUseCase(repositorioPedidoGateway);
        ConsultarPedidoPorIdUseCase consultarPedidoPorIdUseCase = new ConsultarPedidoPorIdUseCase(repositorioPedidoGateway, repositorioItemGateway);
        AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase = new AtualizarStatusPedidoUseCase(repositorioPedidoGateway);
        ListarPedidosPorStatusUseCase listarPedidosPorStatusUseCase = new ListarPedidosPorStatusUseCase(repositorioPedidoGateway);

        return new ControladorPedido(
                consultarClientePorIdUseCase,
                criarPedidoUseCase,
                criarPagamentoPendenteUseCase,
                criarQrCodePagamento,
                listarPedidosOrdenadosUseCase,
                consultarPedidoPorIdUseCase,
                atualizarStatusPedidoUseCase,
                listarPedidosPorStatusUseCase,
                repositorioPedidoGateway
        );
    }
}
