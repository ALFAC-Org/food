package br.com.alfac.food.infra.item.gateways;

import br.com.alfac.food.core.application.item.adapters.gateways.RepositorioItemGateway;
import br.com.alfac.food.core.domain.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;


@Component
public class RepositorioItemGatewayClient implements RepositorioItemGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositorioItemGatewayClient.class);

    private static final String PATH_BUSCAR_CLIENTE = "api/v1/itens/por-id/";

    @Value("${food.foodproduto.url}")
    private String url;

    private WebClient webClient;


    public RepositorioItemGatewayClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Optional<Item> consultarItemPorId(final Long itemId) {


        try {

            Item item = webClient
                    .get()
                    .uri(url.concat(PATH_BUSCAR_CLIENTE).concat(itemId.toString()))
                    .retrieve()
                    .bodyToMono(Item.class)
                    .block();

            return Optional.of(item);

        } catch (WebClientResponseException e) {
            LOGGER.error("Erro ao buscar item  itemId: {}", itemId, e);

        }

        return Optional.empty();
    }
}
