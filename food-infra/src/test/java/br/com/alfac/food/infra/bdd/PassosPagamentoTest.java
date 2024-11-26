package br.com.alfac.food.infra.bdd;

import br.com.alfac.food.core.application.pagamento.dto.PagamentoResponse;
import br.com.alfac.food.core.domain.pagamento.StatusPagamento;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Component
public class PassosPagamentoTest {

    private Response response;

    @Value("${server.port}")
    private String APPLICATION_PORT;

    private String FULL_ENDPOINT_PAGAMENTO;

    private PagamentoResponse pagamentoResponse;

    @PostConstruct
    public void init() {
        FULL_ENDPOINT_PAGAMENTO = "http://localhost:" + APPLICATION_PORT + "/api/v1/pagamentos";
    }

    @Dado("que um pagamento já foi registrado")
    public void pagamentoJaPublicado() {
        pagamentoResponse = new PagamentoResponse(14L, StatusPagamento.APROVADO, 1L, LocalDateTime.now());
    }

    @Quando("requisitar a busca do pagamento")
    public void requisitarBuscarPagamento() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .header("auth", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6ImJkMGNiMDhhMjI2MjEwNmQ5N2NiMzNmNjYxODNkOWViIn0.eyJ3aG8iOiJBVVRIRU5USUNBVEVEIiwiaWQiOjF9.wMrEvM1KWTe7hcRGLBhajx0jXIPANcL68z70VvBoSOVyHV7mVR28LoSSdgqkN_xvpxuxODIee0q4BpOpBNalKw")
                .get(FULL_ENDPOINT_PAGAMENTO + "/status-pagamento/{pedido_id}", pagamentoResponse.id().toString());
    }

    @Então("o pagamento é exibido com sucesso")
    public void pagamentoExibidoComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/PagamentoResponseSchema.json"));
    }


}
