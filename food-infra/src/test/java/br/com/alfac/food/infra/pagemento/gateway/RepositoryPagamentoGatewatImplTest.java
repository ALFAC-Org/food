package br.com.alfac.food.infra.pagemento.gateway;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alfac.food.core.application.pagamento.dto.PagamentoResponse;
import br.com.alfac.food.core.domain.pagamento.Pagamento;
import br.com.alfac.food.core.domain.pagamento.StatusPagamento;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.infra.pagamento.gateway.RepositorioPagamentoGatewayImpl;
import br.com.alfac.food.infra.pagamento.mapper.PagamentoEntityMapper;
import br.com.alfac.food.infra.pagamento.persistence.PagamentoEntity;
import br.com.alfac.food.infra.pagamento.persistence.PagamentoEntityRepository;

@ExtendWith(MockitoExtension.class)
public class RepositoryPagamentoGatewatImplTest {

    @Mock
    private PagamentoEntityRepository pagamentoEntityRepository;

    @InjectMocks
    private RepositorioPagamentoGatewayImpl repositorioPagamentoGateway;

    @Test
    public void testCriar() throws FoodException {
        PagamentoResponse pagamentoResponse = new PagamentoResponse(1L, StatusPagamento.APROVADO, 1L, LocalDateTime.now());
        PagamentoEntity pagamentoEntity = PagamentoEntityMapper.toEntity(pagamentoResponse);
        PagamentoEntity pagamentoSalvo = new PagamentoEntity();
        pagamentoSalvo.setId(1L);
        pagamentoSalvo.setStatus(StatusPagamento.APROVADO);
        pagamentoSalvo.setPedidoId(1L);
        pagamentoSalvo.setDataPagamento(LocalDateTime.now());
        pagamentoSalvo.setDataAtualizacao(LocalDateTime.now());

        when(pagamentoEntityRepository.save(any(PagamentoEntity.class))).thenReturn(pagamentoSalvo);

        Pagamento pagamento = repositorioPagamentoGateway.criar(pagamentoResponse);

        assertEquals(pagamentoSalvo.getId(), pagamento.getId());
        assertEquals(pagamentoSalvo.getStatus(), pagamento.getStatusPagamento());
        assertEquals(pagamentoSalvo.getPedidoId(), pagamento.getPedidoId());
        assertEquals(pagamentoSalvo.getDataPagamento(), pagamento.getDataPagamento());
    }

    @Test
    public void testAtualizar() throws FoodException {
        PagamentoResponse pagamentoResponse = new PagamentoResponse(1L, StatusPagamento.APROVADO, 1L, LocalDateTime.now());
        PagamentoEntity pagamentoEntity = PagamentoEntityMapper.toEntity(pagamentoResponse);
        pagamentoEntity.setId(1L);
        pagamentoEntity.setStatus(StatusPagamento.PENDENTE);
        pagamentoEntity.setPedidoId(1L);
        pagamentoEntity.setDataPagamento(LocalDateTime.now());
        pagamentoEntity.setDataAtualizacao(LocalDateTime.now());

        when(pagamentoEntityRepository.findById(1L)).thenReturn(Optional.of(pagamentoEntity));
        when(pagamentoEntityRepository.save(any(PagamentoEntity.class))).thenReturn(pagamentoEntity);

        Pagamento pagamentoAtualizado = repositorioPagamentoGateway.atualizar(pagamentoResponse);

        assertEquals(pagamentoEntity.getId(), pagamentoAtualizado.getId());
        assertEquals(pagamentoResponse.status(), pagamentoAtualizado.getStatusPagamento());
        assertEquals(pagamentoEntity.getPedidoId(), pagamentoAtualizado.getPedidoId());
        assertEquals(pagamentoEntity.getDataPagamento(), pagamentoAtualizado.getDataPagamento());
    }

    @Test
    public void testAtualizarPagamentoNaoEncontrado() throws FoodException {
        PagamentoResponse pagamentoResponse = new PagamentoResponse(1L, StatusPagamento.APROVADO, 1L, LocalDateTime.now());

        when(pagamentoEntityRepository.findById(1L)).thenReturn(Optional.empty());

        Pagamento pagamentoAtualizado = repositorioPagamentoGateway.atualizar(pagamentoResponse);

        assertNull(pagamentoAtualizado);
    }

    @Test
    public void testBuscarPorId() throws FoodException {
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setId(1L);
        pagamentoEntity.setStatus(StatusPagamento.APROVADO);
        pagamentoEntity.setPedidoId(1L);
        pagamentoEntity.setDataPagamento(LocalDateTime.now());
        pagamentoEntity.setDataAtualizacao(LocalDateTime.now());

        when(pagamentoEntityRepository.findById(1L)).thenReturn(Optional.of(pagamentoEntity));

        Optional<Pagamento> pagamento = repositorioPagamentoGateway.buscarPorId(1L);

        assertEquals(pagamentoEntity.getId(), pagamento.get().getId());
        assertEquals(pagamentoEntity.getStatus(), pagamento.get().getStatusPagamento());
        assertEquals(pagamentoEntity.getPedidoId(), pagamento.get().getPedidoId());
        assertEquals(pagamentoEntity.getDataPagamento(), pagamento.get().getDataPagamento());
    }

    @Test
    public void testBuscarPorIdNaoEncontrado() throws FoodException {
        when(pagamentoEntityRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Pagamento> pagamento = repositorioPagamentoGateway.buscarPorId(1L);

        assertEquals(Optional.empty(), pagamento);
    }

    @Test
    public void testBuscarPorPedidoId() throws FoodException {
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setId(1L);
        pagamentoEntity.setStatus(StatusPagamento.APROVADO);
        pagamentoEntity.setPedidoId(1L);
        pagamentoEntity.setDataPagamento(LocalDateTime.now());
        pagamentoEntity.setDataAtualizacao(LocalDateTime.now());

        when(pagamentoEntityRepository.findByPedidoId(1L)).thenReturn(Optional.of(pagamentoEntity));

        Optional<Pagamento> pagamento = repositorioPagamentoGateway.buscarPorPedidoId(1L);

        assertEquals(pagamentoEntity.getId(), pagamento.get().getId());
        assertEquals(pagamentoEntity.getStatus(), pagamento.get().getStatusPagamento());
        assertEquals(pagamentoEntity.getPedidoId(), pagamento.get().getPedidoId());
        assertEquals(pagamentoEntity.getDataPagamento(), pagamento.get().getDataPagamento());
    }

    @Test
    public void testBuscarPorPedidoIdNaoEncontrado() throws FoodException {
        when(pagamentoEntityRepository.findByPedidoId(1L)).thenReturn(Optional.empty());

        Optional<Pagamento> pagamento = repositorioPagamentoGateway.buscarPorPedidoId(1L);

        assertEquals(Optional.empty(), pagamento);
    }
}
