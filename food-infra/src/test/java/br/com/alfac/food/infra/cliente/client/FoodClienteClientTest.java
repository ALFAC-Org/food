package br.com.alfac.food.infra.cliente.client;

import br.com.alfac.food.core.domain.cliente.Cliente;
import br.com.alfac.food.infra.utils.ClienteHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FoodClienteClientTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private ResponseSpec responseSpec;

    @InjectMocks
    private FoodClienteClient foodClienteClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        foodClienteClient = new FoodClienteClient(webClient);
        ReflectionTestUtils.setField(foodClienteClient, "url", "http://localhost:8080");

    }

    @Test
    void buscarClientePorId_DeveRetornarCliente_QuandoClienteExistir() {
        // Arrange
        Long clienteId = 1L;
        Cliente clienteMock = ClienteHelper.criarClienteComId(clienteId);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Cliente.class)).thenReturn(Mono.just(clienteMock));

        // Act
        Optional<Cliente> resultado = foodClienteClient.buscarClientePorId(clienteId);

        // Assert
        assertEquals(true, resultado.isPresent());
        assertEquals(clienteId, resultado.get().getId());
        assertEquals(clienteMock.getNome(), resultado.get().getNome());
        assertEquals(clienteMock.getEmail(), resultado.get().getEmail());
        assertEquals(clienteMock.getUuid(), resultado.get().getUuid());

        // Verifica se a URI foi construída corretamente
        ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);
        verify(requestHeadersUriSpec).uri(uriCaptor.capture());
        assertEquals("http://localhost:8080/api/v1/clientes/1", uriCaptor.getValue());
    }

    @Test
    void buscarClientePorId_DeveRetornarOptionalVazio_QuandoClienteNaoExistir() {
        // Arrange
        Long clienteId = 2L;

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Cliente.class)).thenThrow(WebClientResponseException.create(404, "Not Found", null, null, null));

        // Act
        Optional<Cliente> resultado = foodClienteClient.buscarClientePorId(clienteId);

        // Assert
        assertFalse(resultado.isPresent());
    }
}