package br.com.alfac.food.core.application.cliente.usecases;

import java.util.Optional;

import br.com.alfac.food.core.application.cliente.adapters.gateways.RepositorioClienteGateway;
import br.com.alfac.food.core.domain.cliente.Cliente;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.exception.cliente.ClienteError;

public class ConsultarClientePorIdUseCase {
    private final RepositorioClienteGateway clienteRepository;

    public ConsultarClientePorIdUseCase(final RepositorioClienteGateway clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente execute(Long id) throws FoodException {
        Optional<Cliente> clienteOpt = clienteRepository.consultarClientePorId(id);
        return clienteOpt.orElseThrow(() -> new FoodException(ClienteError.CLIENTE_NAO_ENCONTRADO));
    }
}