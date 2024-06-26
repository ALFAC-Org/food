package br.com.alfac.food.config;

import br.com.alfac.food.core.application.pagamento.ports.PagamentoClient;
import br.com.alfac.food.core.application.pagamento.ports.PagamentoService;
import br.com.alfac.food.core.application.pagamento.services.PagamentoServiceImpl;
import br.com.alfac.food.core.application.pedido.ports.StatusPedidoPagamentoService;
import br.com.alfac.food.core.application.pedido.services.StatusPedidoPagamentoServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.alfac.food.core.application.cliente.ports.ClienteRepository;
import br.com.alfac.food.core.application.cliente.ports.ClienteService;
import br.com.alfac.food.core.application.cliente.services.ClienteServiceImpl;
import br.com.alfac.food.core.application.item.ports.ItemRepository;
import br.com.alfac.food.core.application.item.ports.ItemService;
import br.com.alfac.food.core.application.item.services.ItemServiceImpl;
import br.com.alfac.food.core.application.pedido.ports.PedidoRepository;
import br.com.alfac.food.core.application.pedido.ports.PedidoService;
import br.com.alfac.food.core.application.pedido.services.PedidoServiceImpl;

@Configuration
public class BeanConfiguration {

    @Bean
    public ClienteService clienteService(ClienteRepository clienteRepository) {
        return new ClienteServiceImpl(clienteRepository);
    }
    @Bean
    public PedidoService pedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, ItemRepository itemRepository) {
        return new PedidoServiceImpl(pedidoRepository, clienteRepository, itemRepository);
    }
    @Bean
    public ItemService itemService(ItemRepository itemRepository) {
        return new ItemServiceImpl(itemRepository);
    }

    @Bean
    public PagamentoService pagamentoService(PagamentoClient pagamentoClient, PedidoService pedidoService, StatusPedidoPagamentoService statusPedidoPagamentoService) {
        return new PagamentoServiceImpl(pagamentoClient, pedidoService, statusPedidoPagamentoService);
    }

    @Bean
    public StatusPedidoPagamentoService statusPedidoPagamentoService(final PedidoRepository pedidoRepository) {
        return new StatusPedidoPagamentoServiceImpl(pedidoRepository);
    }
}
