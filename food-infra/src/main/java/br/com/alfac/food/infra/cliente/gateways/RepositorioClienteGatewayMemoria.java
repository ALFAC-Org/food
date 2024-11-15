package br.com.alfac.food.infra.cliente.gateways;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.alfac.food.core.application.cliente.adapters.gateways.RepositorioClienteGateway;
import br.com.alfac.food.core.domain.cliente.Cliente;
import br.com.alfac.food.infra.cliente.mapper.ClienteEntityMapper;
import br.com.alfac.food.infra.cliente.persistence.ClienteEntity;

@javax.annotation.processing.Generated("jacoco")
@Component
public class RepositorioClienteGatewayMemoria implements RepositorioClienteGateway {

    private final List<ClienteEntity> clientes;
    private final ClienteEntityMapper clienteEntityMapper;

    public RepositorioClienteGatewayMemoria() {
        this.clientes = new ArrayList<>();
        this.clienteEntityMapper = ClienteEntityMapper.INSTANCE;
    }

    @Override
    public Optional<Cliente> consultarClientePorCPF(String cpf){

        Optional<ClienteEntity> clienteEntityOpt = clientes.stream().filter(cliente -> cliente.getCpf().equals(cpf)).findFirst();

        return montarCliente(clienteEntityOpt);
    }

    @Override
    public Optional<Cliente> consultarClientePorUuId(final UUID id) {
        Optional<ClienteEntity> clienteEntityOpt = clientes.stream().filter(cliente -> cliente.getUuid().equals(id)).findFirst();
        return montarCliente(clienteEntityOpt);
    }

    @Override
    public Optional<Cliente> consultarClientePorId(final Long id) {
        Optional<ClienteEntity> clienteEntityOpt =  clientes.stream().filter(cliente -> cliente.getId().equals(id)).findFirst();
        return montarCliente(clienteEntityOpt);
    }

    @Override
    public Cliente cadastrarCliente(Cliente cliente){
        ClienteEntity clienteEntity = clienteEntityMapper.toEntity(cliente);
        clienteEntity.setUuid(UUID.randomUUID());
        clienteEntity.setId((long) clientes.size() + 1);
        clientes.add(clienteEntity);

        return clienteEntityMapper.toDomain(clienteEntity);
    }

    private Optional<Cliente> montarCliente(Optional<ClienteEntity> clienteEntityOpt) {
        Optional<Cliente> clienteOpt = Optional.empty();

        if (clienteEntityOpt.isPresent()) {
            ClienteEntity clienteEntity = clienteEntityOpt.get();

            Cliente cliente = clienteEntityMapper.toDomain(clienteEntity);

            clienteOpt = Optional.of(cliente);
        }

        return clienteOpt;
    }
}