package br.com.alfac.food.infra.cliente.gateways;

import br.com.alfac.food.core.application.cliente.adapters.gateways.RepositorioClienteGateway;
import br.com.alfac.food.core.domain.cliente.Cliente;
import br.com.alfac.food.infra.cliente.mapper.ClienteEntityMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RepositorioClienteGatewayMemoria implements RepositorioClienteGateway {

    private final List<Cliente> clientes;
    private final ClienteEntityMapper clienteEntityMapper;

    public RepositorioClienteGatewayMemoria() {
        this.clientes = new ArrayList<>();
        this.clienteEntityMapper = ClienteEntityMapper.INSTANCE;
    }


    @Override
    public Optional<Cliente> consultarClientePorId(final Long id) {
        Optional<Cliente> clienteEntityOpt = clientes.stream().filter(cliente -> cliente.getId().equals(id)).findFirst();
        return montarCliente(clienteEntityOpt);
    }


    private Optional<Cliente> montarCliente(Optional<Cliente> clienteEntityOpt) {
        Optional<Cliente> clienteOpt = Optional.empty();

        if (clienteEntityOpt.isPresent()) {
            Cliente cliente = clienteEntityOpt.get();


            clienteOpt = Optional.of(cliente);
        }

        return clienteOpt;
    }
}