package br.com.alfac.food.infra.item.gateways;


import br.com.alfac.food.core.domain.item.CategoriaItem;
import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.infra.utils.ItemHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class RepositorioItemGatewayClientTest {

    @InjectMocks
    private RepositorioItemGatewayClient subject;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subject = new RepositorioItemGatewayClient(webClient);
        ReflectionTestUtils.setField(subject, "url", "http://localhost:8080");
    }

    @Test
    void consultarItemPorId_ItemExists_ReturnsItem() {
        Long itemId = 1L;
        Item expectedItem = ItemHelper.criarItem(CategoriaItem.ACOMPANHAMENTO);
        expectedItem.setId(itemId);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Item.class)).thenReturn(Mono.just(expectedItem));

        Optional<Item> result = subject.consultarItemPorId(itemId);

        assertTrue(result.isPresent());
        assertEquals(expectedItem, result.get());
    }

    @Test
    void consultarItemPorId_ItemNotFound_ReturnsEmpty() {
        Long itemId = 1L;

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Item.class)).thenThrow(WebClientResponseException.create(404, "Not Found", null, null, null));

        Optional<Item> result = subject.consultarItemPorId(itemId);

        assertFalse(result.isPresent());
    }
}