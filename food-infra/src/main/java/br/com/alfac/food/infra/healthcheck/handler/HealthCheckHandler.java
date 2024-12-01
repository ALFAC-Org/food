package br.com.alfac.food.infra.healthcheck.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.infra.config.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/health-check")
@Tag(name = "HealthCheck", description = "Métodos para obter dados da aplicação")
public class HealthCheckHandler {

  @Value("${application.version}")
  private String applicationVersion;

  @Value("${application.database.version}")
  private String databaseVersion;

  public HealthCheckHandler() {
  }

  @Operation(summary = "Retorna o health check da aplicação", description = "Retorna se a aplicação está funcionando")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
      @ApiResponse(responseCode = "500", description = "Aplicação não está funcionando", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))
      }) })
  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, String>> consultarHealthCheck() throws FoodException {
    Map<String, String> versions = new HashMap<>();
    versions.put("status", "Aplicação está funcionando. Veja as versões atuais.");
    versions.put("application-version", applicationVersion);
    versions.put("database-version", databaseVersion);
    versions.put("ambiente", "producao");
    return new ResponseEntity<>(versions, HttpStatus.OK);
  }
}
