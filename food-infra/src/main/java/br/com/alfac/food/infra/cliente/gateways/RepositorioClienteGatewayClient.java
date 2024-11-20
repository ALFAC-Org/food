package br.com.alfac.food.infra.cliente.gateways;

import br.com.alfac.food.core.application.cliente.adapters.gateways.RepositorioClienteGateway;
import br.com.alfac.food.core.domain.cliente.Cliente;
import br.com.alfac.food.infra.cliente.client.FoodClienteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RepositorioClienteGatewayClient implements RepositorioClienteGateway {

    private final FoodClienteClient foodClienteClient;


    public RepositorioClienteGatewayClient(FoodClienteClient foodClienteClient) {
        this.foodClienteClient = foodClienteClient;
    }

    @Override
    public Optional<Cliente> consultarClientePorId(Long id) {

        return foodClienteClient.buscarClientePorId(id);
    }
}