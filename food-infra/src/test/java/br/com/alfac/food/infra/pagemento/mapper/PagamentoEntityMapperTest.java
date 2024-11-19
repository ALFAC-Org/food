package br.com.alfac.food.infra.pagemento.mapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import br.com.alfac.food.core.application.pagamento.dto.PagamentoResponse;
import br.com.alfac.food.core.domain.pagamento.Pagamento;
import br.com.alfac.food.core.domain.pagamento.StatusPagamento;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.infra.pagamento.mapper.PagamentoEntityMapper;
import br.com.alfac.food.infra.pagamento.persistence.PagamentoEntity;


public class PagamentoEntityMapperTest {

    @Test
    public void testToEntity() {
        PagamentoResponse pagamentoResponse = new PagamentoResponse(1L, StatusPagamento.APROVADO, 100L, LocalDateTime.of(2023, 10, 1, 12, 0));
        PagamentoEntity pagamentoEntity = PagamentoEntityMapper.toEntity(pagamentoResponse);

        assertEquals(pagamentoResponse.id(), pagamentoEntity.getId());
        assertEquals(pagamentoResponse.pedidoId(), pagamentoEntity.getPedidoId());
        assertEquals(pagamentoResponse.dataPagamento(), pagamentoEntity.getDataPagamento());
        assertEquals(pagamentoResponse.status(), pagamentoEntity.getStatus());
    }
    @Test
    public void testToDTO() {
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setId(1L);
        pagamentoEntity.setPedidoId(100L);
        pagamentoEntity.setDataPagamento(LocalDateTime.of(2023, 10, 1, 12, 0));
        pagamentoEntity.setStatus(StatusPagamento.APROVADO);

        PagamentoResponse pagamentoResponse = PagamentoEntityMapper.toDTO(pagamentoEntity);

        assertEquals(pagamentoEntity.getId(), pagamentoResponse.id());
        assertEquals(pagamentoEntity.getPedidoId(), pagamentoResponse.pedidoId());
        assertEquals(pagamentoEntity.getDataPagamento(), pagamentoResponse.dataPagamento());
        assertEquals(pagamentoEntity.getStatus(), pagamentoResponse.status());
    }

    @Test
    public void testOptionalToDTO() {
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setId(1L);
        pagamentoEntity.setPedidoId(100L);
        pagamentoEntity.setDataPagamento(LocalDateTime.of(2023, 10, 1, 12, 0));
        pagamentoEntity.setStatus(StatusPagamento.APROVADO);

        Optional<PagamentoEntity> optionalPagamentoEntity = Optional.of(pagamentoEntity);
        Optional<PagamentoResponse> optionalPagamentoResponse = PagamentoEntityMapper.toDTO(optionalPagamentoEntity);

        assertEquals(true, optionalPagamentoResponse.isPresent());
        assertEquals(pagamentoEntity.getId(), optionalPagamentoResponse.get().id());
        assertEquals(pagamentoEntity.getPedidoId(), optionalPagamentoResponse.get().pedidoId());
        assertEquals(pagamentoEntity.getDataPagamento(), optionalPagamentoResponse.get().dataPagamento());
        assertEquals(pagamentoEntity.getStatus(), optionalPagamentoResponse.get().status());
    }

    @Test
    public void testEmptyOptionalToDTO() {
        Optional<PagamentoEntity> optionalPagamentoEntity = Optional.empty();
        Optional<PagamentoResponse> optionalPagamentoResponse = PagamentoEntityMapper.toDTO(optionalPagamentoEntity);

        assertEquals(false, optionalPagamentoResponse.isPresent());
    }
    
    @Test
    public void testToDomain() throws FoodException {
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setId(1L);
        pagamentoEntity.setPedidoId(100L);
        pagamentoEntity.setDataPagamento(LocalDateTime.of(2023, 10, 1, 12, 0));
        pagamentoEntity.setStatus(StatusPagamento.APROVADO);

        Pagamento pagamento = PagamentoEntityMapper.toDomain(pagamentoEntity);

        assertEquals(pagamentoEntity.getId(), pagamento.getId());
        assertEquals(pagamentoEntity.getPedidoId(), pagamento.getPedidoId());
        assertEquals(pagamentoEntity.getDataPagamento(), pagamento.getDataPagamento());
        assertEquals(pagamentoEntity.getStatus(), pagamento.getStatusPagamento());
    }

    @Test
    public void testOptionalToDomain() throws FoodException {
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setId(1L);
        pagamentoEntity.setPedidoId(100L);
        pagamentoEntity.setDataPagamento(LocalDateTime.of(2023, 10, 1, 12, 0));
        pagamentoEntity.setStatus(StatusPagamento.APROVADO);

        Optional<PagamentoEntity> optionalPagamentoEntity = Optional.of(pagamentoEntity);
        Optional<Pagamento> optionalPagamento = PagamentoEntityMapper.toDomain(optionalPagamentoEntity);

        assertEquals(true, optionalPagamento.isPresent());
        assertEquals(pagamentoEntity.getId(), optionalPagamento.get().getId());
        assertEquals(pagamentoEntity.getPedidoId(), optionalPagamento.get().getPedidoId());
        assertEquals(pagamentoEntity.getDataPagamento(), optionalPagamento.get().getDataPagamento());
        assertEquals(pagamentoEntity.getStatus(), optionalPagamento.get().getStatusPagamento());
    }

    @Test
    public void testEmptyOptionalToDomain() throws FoodException {
        Optional<PagamentoEntity> optionalPagamentoEntity = Optional.empty();
        Optional<Pagamento> optionalPagamento = PagamentoEntityMapper.toDomain(optionalPagamentoEntity);

        assertEquals(false, optionalPagamento.isPresent());
    }
}