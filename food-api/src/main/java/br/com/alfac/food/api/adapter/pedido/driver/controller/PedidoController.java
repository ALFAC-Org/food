package br.com.alfac.food.api.adapter.pedido.driver.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.alfac.food.api.adapter.cliente.mapper.ClienteMapper;
import br.com.alfac.food.api.adapter.pedido.dto.PedidoRequest;
import br.com.alfac.food.api.adapter.pedido.mapper.PedidoMapper;
import br.com.alfac.food.core.application.pedido.dto.PedidoDTO;
import br.com.alfac.food.core.application.pedido.ports.PedidoService;
import br.com.alfac.food.core.domain.pedido.Pedido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    public PedidoController(final PedidoService pedidoService, PedidoMapper pedidoMapper) {
        this.pedidoService = pedidoService;
        this.pedidoMapper = pedidoMapper;
    }

    @GetMapping
    public String listarPedidos() {
        return new String();
    }

    @Operation(summary = "Registrar Pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido registrado"),
            @ApiResponse(responseCode = "422", description = "Erro ao registrar pedido", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Pedido.class))
            })})
    @PostMapping
    public void registrarPedido(@RequestBody PedidoRequest pedidoRequest) {
        pedidoService.registrarPedido(pedidoMapper.toDTO(pedidoRequest));
    }

}
