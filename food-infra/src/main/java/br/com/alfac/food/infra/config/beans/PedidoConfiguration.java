package br.com.alfac.food.infra.config.beans;

import br.com.alfac.food.core.application.pedido.adapters.controller.ControladorPedido;
import br.com.alfac.food.infra.cliente.gateways.RepositorioClienteGatewayMemoria;
import br.com.alfac.food.infra.item.gateways.RepositorioItemGatewayImpl;
import br.com.alfac.food.infra.pagamento.client.PagamentoClientGatewayImpl;
import br.com.alfac.food.infra.pagamento.gateway.RepositorioPagamentoGatewayImpl;
import br.com.alfac.food.infra.pedido.gateways.RepositorioPedidoGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoConfiguration {

    @Bean
    public ControladorPedido pedidoService(final RepositorioPedidoGatewayImpl repositorioPedidoGateway,
                                           final RepositorioClienteGatewayMemoria repositorioClienteGateway,
                                           final RepositorioItemGatewayImpl repositorioItemGateway,
                                           final RepositorioPagamentoGatewayImpl repositorioPagamentoGateway,
                                           final PagamentoClientGatewayImpl pagamentoClientGateway) {
        return new ControladorPedido(repositorioPedidoGateway, repositorioClienteGateway, repositorioItemGateway, repositorioPagamentoGateway, pagamentoClientGateway);
    }
}
