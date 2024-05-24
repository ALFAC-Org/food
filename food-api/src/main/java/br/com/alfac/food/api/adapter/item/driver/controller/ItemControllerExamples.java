package br.com.alfac.food.api.adapter.item.driver.controller;

import org.springframework.web.bind.annotation.RequestBody;

import br.com.alfac.food.api.adapter.item.dto.ItemRequest;
import br.com.alfac.food.api.config.exception.ApiError;
import br.com.alfac.food.core.application.item.dto.ItemDTO;
import br.com.alfac.food.core.exception.FoodException;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;

import org.springframework.http.MediaType;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface ItemControllerExamples {

  @Operation(summary = "Cadastrar item", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = {
    @ExampleObject(name = "Lanche", value = """
            {
                "nome": "Novo Lanche",
                "preco": 10.50,
                "categoria": "LANCHE"
              }

                                        """),
    @ExampleObject(name = "Complemento", value = """
            {
                "nome": "Novo Complemento",
                "preco": 10.50,
                "categoria": "COMPLEMENTO"
              }

                                        """),
    @ExampleObject(name = "Acompanhamento", value = """
            {
                "nome": "Novo Acompanhamento",
                "preco": 10.50,
                "categoria": "ACOMPANHAMENTO"
              }

                                        """),
    @ExampleObject(name = "Bebida", value = """
            {
                "nome": "Nova Bebida",
                "preco": 10.50,
                "categoria": "BEBIDA"
              }

                                        """),
    @ExampleObject(name = "Sobremesa", value = """
            {
                "nome": "Nova Sobremesa",
                "preco": 10.50,
                "categoria": "SOBREMESA"
              }

                                        """)

})))

@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Item cadastrado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Erro no cadastro do item", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))
    }) })
  ItemDTO cadastrarItem(@RequestBody ItemRequest itemRequest) throws FoodException;
}