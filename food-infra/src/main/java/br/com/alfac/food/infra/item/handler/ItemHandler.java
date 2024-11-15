package br.com.alfac.food.infra.item.handler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alfac.food.core.application.item.adapters.controller.ControladorItem;
import br.com.alfac.food.core.application.item.dto.ItemDTO;
import br.com.alfac.food.core.domain.item.CategoriaItem;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.infra.config.exception.ApiError;
import br.com.alfac.food.infra.item.dto.ItemRequest;
import br.com.alfac.food.infra.item.mapper.ItemMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Generated;

@Generated("coverageIgnore")
@RestController
@RequestMapping("/api/v1/itens")
@Tag(name = "Item", description = "Métodos para manipulação de itens (LANCHE, COMPLEMENTO, ACOMPANHAMENTO, BEBIDA, SOBREMESA...)")
public class ItemHandler implements BaseItemHandler {

    private final ControladorItem controladorItem;
    private final ItemMapper itemMapper;

    public ItemHandler(final ControladorItem controladorItem) {
        this.itemMapper = ItemMapper.INSTANCE;
        this.controladorItem = controladorItem;
    }

    @Operation(summary = "Consultar todos os itens")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
        @ApiResponse(responseCode = "404", description = "Nenhum item cadastrado", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))
        })})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemDTO>> consultarItens() throws FoodException {
        return new ResponseEntity<>(controladorItem.consultarItens(), HttpStatus.OK);
    }

    @Operation(summary = "Consultar itens por categoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
        @ApiResponse(responseCode = "404", description = "Nenhum item cadastrado", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))
        })})
    @GetMapping(value = "por-categoria/{categoria}/itens", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemDTO>> consultarItensPorCategoria(@PathVariable CategoriaItem categoria) throws FoodException {
        return new ResponseEntity<>(controladorItem.consultarItensPorCategoria(categoria), HttpStatus.OK);
    }

    @Operation(summary = "Consultar item por Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
        @ApiResponse(responseCode = "404", description = "Nenhum item cadastrado", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))
        })})
    @GetMapping(value = "por-id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDTO> consultarPorId(@PathVariable Long id) throws FoodException {
        return new ResponseEntity<>(controladorItem.consultarItemPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Criar novo item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Operação bem sucedida"),
        @ApiResponse(responseCode = "400", description = "Erro ao cadastrar item", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))
        })})
    @PostMapping
    public ResponseEntity<ItemDTO> cadastrarItem(@RequestBody ItemRequest itemRequest) throws FoodException {
        ItemDTO itemDTO = controladorItem.cadastrarItem(itemMapper.toDTO(itemRequest));

        return new ResponseEntity<>(itemDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar item por id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
        @ApiResponse(responseCode = "404", description = "Nenhum item cadastrado", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))
        })})
    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> atualizarItem(@PathVariable Long id, @RequestBody ItemRequest itemRequest) throws FoodException {
        ItemDTO itemDTO = controladorItem.atualizarItem(id, itemMapper.toDTO(itemRequest));
        return new ResponseEntity<>(itemDTO, HttpStatus.OK);
    }

    @Operation(summary = "Deletar item por id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
        @ApiResponse(responseCode = "404", description = "Nenhum item cadastrado", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))
        })})
    @DeleteMapping("/{id}")
    public ResponseEntity<ItemDTO> excluirItem(@PathVariable Long id) throws FoodException {
        return new ResponseEntity<>(controladorItem.excluirItem(id), HttpStatus.OK);
    }
}
