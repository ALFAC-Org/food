package br.com.alfac.food.infra.config.beans;

import br.com.alfac.food.core.application.pagamento.adapters.controller.ControladorRecebimentoPagamento;
import br.com.alfac.food.core.application.pagamento.adapters.controller.ControladorPagamento;
import br.com.alfac.food.core.application.pagamento.adapters.gateways.RepositorioPagamentoGateway;
import br.com.alfac.food.core.application.pagamento.usecases.AlterarStatusPagamentoRealizadoUseCase;
import br.com.alfac.food.core.application.pagamento.usecases.ConsultarPagementoPorPedidoIdUseCase;
import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.application.pedido.usecases.AtualizarStatusPedidoPagamentoProcessadoUseCase;
import br.com.alfac.food.infra.pagamento.gateway.RepositorioPagamentoGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagamentoConfiguration {

    @Bean
    public ControladorPagamento controladorPagamento(final RepositorioPagamentoGatewayImpl pagamentoRepository) {
        ConsultarPagementoPorPedidoIdUseCase consultarPagementoPorPedidoIdUseCase = new ConsultarPagementoPorPedidoIdUseCase(pagamentoRepository);

        return new ControladorPagamento(consultarPagementoPorPedidoIdUseCase);
    }

    @Bean
    public ControladorRecebimentoPagamento controladoRecebimentoPagamento(
            final RepositorioPedidoGateway pedidoRepository,
             final RepositorioPagamentoGateway pagamentoRepository
    ) {
        AlterarStatusPagamentoRealizadoUseCase alterarStatusPagamentoRealizadoUseCase = new AlterarStatusPagamentoRealizadoUseCase(pagamentoRepository);
        AtualizarStatusPedidoPagamentoProcessadoUseCase atualizarStatusPedidoPagamentoProcessadoUseCase = new AtualizarStatusPedidoPagamentoProcessadoUseCase(pedidoRepository);

        return new ControladorRecebimentoPagamento(alterarStatusPagamentoRealizadoUseCase, atualizarStatusPedidoPagamentoProcessadoUseCase);
    }
}
