package br.com.alfac.food.infra.pagamento.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alfac.food.core.application.pagamento.adapters.controller.ControladorPagamento;
import br.com.alfac.food.core.application.pagamento.dto.PagamentoResponse;
import br.com.alfac.food.core.exception.FoodException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/pagamentos")
@Tag(name = "Pagamento", description = "Métodos para manipulação de pagamentos")
public class PagamentoController {

    private final ControladorPagamento controladorPagamento;

    public PagamentoController(final ControladorPagamento controladorPagamento) {
        this.controladorPagamento = controladorPagamento;
    }
    
    @Operation(summary = "Consultar status do pedido por pedido id")
    @GetMapping(value = "/status-pagamento/{pedido_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagamentoResponse> consultarStatusPedidoPorPedidoId(@PathVariable Long pedido_id) throws FoodException {
        PagamentoResponse pagamentoResponse = controladorPagamento.consultarPagamentoPorPedidoId(pedido_id);
        
        return new ResponseEntity<>(pagamentoResponse, HttpStatus.OK);
    }
}
