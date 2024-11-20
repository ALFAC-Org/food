package br.com.alfac.food.infra.cliente.client;

import br.com.alfac.food.core.domain.cliente.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;

@Component
public class FoodClienteClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FoodClienteClient.class);

    private static final String PATH_BUSCAR_CLIENTE = "/api/v1/clientes/";

    @Value("${food.foodcliente.url}")
    private String url;

    private final WebClient webClient;


    @Autowired
    public FoodClienteClient(
            final WebClient webClient) {
        this.webClient = webClient;
    }

    public Optional<Cliente> buscarClientePorId(final Long clienteId) {

        try {

            Cliente cliente = webClient
                    .get()
                    .uri(url.concat(PATH_BUSCAR_CLIENTE).concat(clienteId.toString()))
                    .retrieve()
                    .bodyToMono(Cliente.class)
                    .block();

            return Optional.of(cliente);

        } catch (WebClientResponseException e) {
            LOGGER.error("Erro ao buscar cliente  clienteId: {}", clienteId, e);

        }

        return Optional.empty();
    }
}
