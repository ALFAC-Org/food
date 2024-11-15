package br.com.alfac.food.infra.cliente.handler;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alfac.food.core.application.cliente.adapters.controller.ControladorCliente;
import br.com.alfac.food.core.application.cliente.dto.ClienteDTO;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.infra.cliente.dto.ClienteRequest;
import br.com.alfac.food.infra.cliente.mapper.ClienteMapper;
import br.com.alfac.food.infra.config.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;


/**
 Classe responsável para demonstração da implementação da inversão de dependencia do repository
 */
@@javax.annotation.processing.Generated("jacoco")
@RestController
@RequestMapping("/api/v2/clientes")
@Tag(name = "Cliente", description = "Métodos para manipulação de clientes")
public class ClienteMemoriaHandler {

    private final ControladorCliente controladorClienteMemoria;
    private final ClienteMapper clienteMapper;

    public ClienteMemoriaHandler(final ControladorCliente controladorClienteMemoria) {
        this.clienteMapper = ClienteMapper.INSTANCE;
        this.controladorClienteMemoria = controladorClienteMemoria;
    }

    @Operation(summary = "Consultar cliente pelo CPF", description = "CPF contém 11 dígitos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
            @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))
            })})
    @GetMapping(value = "/por-cpf/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> consultarCliente(@PathVariable String cpf) throws FoodException {
        return new ResponseEntity<>(controladorClienteMemoria.consultarClientePorCpf(cpf), HttpStatus.OK);
    }

    @Operation(summary = "Consultar cliente pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o cliente pelo id informado"),
            @ApiResponse(responseCode = "404", description = "Retorna cliente não encontrado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))
            })})
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> consultarClientePorId(@PathVariable Long id) throws FoodException {
        return new ResponseEntity<>(controladorClienteMemoria.consultarClientePorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Consultar cliente pelo uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o cliente pelo uuid informado"),
            @ApiResponse(responseCode = "404", description = "Retorna cliente não encontrado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))
            })})
    @GetMapping(value = "/por-uuid/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> consultarClientePorUuid(@PathVariable UUID uuid) throws FoodException {
        return new ResponseEntity<>(controladorClienteMemoria.consultarClientePorUuid(uuid), HttpStatus.OK);
    }

    @Operation(summary = "Cadastrar cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado"),
            @ApiResponse(responseCode = "404", description = "Erro ao cadastrar cliente", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))
            })})
    @PostMapping
    public ResponseEntity<ClienteDTO> cadastrarCliente(@Valid @RequestBody ClienteRequest clienteRequest) throws FoodException {
        ClienteDTO cliente = controladorClienteMemoria.cadastrarCliente(clienteMapper.toDTO(clienteRequest));

        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

}
