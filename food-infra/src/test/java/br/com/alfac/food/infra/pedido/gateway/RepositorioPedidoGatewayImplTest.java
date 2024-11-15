package br.com.alfac.food.infra.pedido.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.domain.pedido.StatusPedido;
import br.com.alfac.food.infra.pedido.gateways.RepositorioPedidoGatewayImpl;
import br.com.alfac.food.infra.pedido.mapper.PedidoEntityMapper;
import br.com.alfac.food.infra.pedido.persistence.PedidoEntity;
import br.com.alfac.food.infra.pedido.persistence.PedidoEntityRepository;
import br.com.alfac.food.infra.utils.PedidoEntityHelper;
import br.com.alfac.food.infra.utils.PedidoHelper;

@ExtendWith(MockitoExtension.class)
public class RepositorioPedidoGatewayImplTest {

    @Mock
    private PedidoEntityRepository pedidoEntityRepository;

    @Mock
    private PedidoEntityMapper pedidoEntityMapper;

    @InjectMocks
    private RepositorioPedidoGatewayImpl repositorioPedidoGatewayImpl;

    @Test
    void deveListarPedidos() throws Exception {
        // Arrange
        List<Pedido> pedidos = PedidoHelper.criarPedidos(2, true);
        List<PedidoEntity> pedidoEntities = PedidoEntityHelper.criarPedidoEntities(pedidos);

        when(pedidoEntityRepository.findAll()).thenReturn(pedidoEntities);
        when(pedidoEntityMapper.toDomain(pedidoEntities)).thenReturn(pedidos);

        // Act
        List<Pedido> result = repositorioPedidoGatewayImpl.listarPedidos();

        // Assert
        assertEquals(pedidos, result);
    }

    @Test
    void deveConsultarPedidoPorIdExistente() throws Exception {
        // Arrange
        Long id = 1L;
        Pedido pedido = PedidoHelper.criarPedido();
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedido);

        PedidoEntity pedidoEntity = PedidoEntityHelper.criarPedidoEntities(pedidos).get(0);

        when(pedidoEntityRepository.findById(id)).thenReturn(Optional.of(pedidoEntity));
        when(pedidoEntityMapper.toDomain(pedidoEntity)).thenReturn(pedido);

        // Act
        Optional<Pedido> result = repositorioPedidoGatewayImpl.consultarPedidoPorId(id);

        // Assert
        assertEquals(Optional.of(pedido), result);
    }

    @Test
    void deveRetornarVazioQuandoPedidoNaoExistir() throws Exception {
        // Arrange
        Long id = 1L;

        when(pedidoEntityRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Pedido> result = repositorioPedidoGatewayImpl.consultarPedidoPorId(id);

        // Assert
        assertEquals(Optional.empty(), result);
    }

    @Test
    void deveRegistrarPedido() throws Exception {
        // Arrange
        Pedido pedido = PedidoHelper.criarPedido();
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedido);

        PedidoEntity pedidoEntity = PedidoEntityHelper.criarPedidoEntities(pedidos).get(0);
        PedidoEntity pedidoCriado = PedidoEntityHelper.criarPedidoEntities(pedidos).get(0);
        pedidoCriado.setId(1L);

        when(pedidoEntityMapper.toEntity(pedido)).thenReturn(pedidoEntity);
        when(pedidoEntityRepository.save(pedidoEntity)).thenReturn(pedidoCriado);
        when(pedidoEntityMapper.toDomain(pedidoCriado)).thenReturn(pedido);

        // Act
        Pedido result = repositorioPedidoGatewayImpl.registrarPedido(pedido);

        // Assert
        assertEquals(pedido, result);
    }

    @Test
    void deveAtualizarStatusPedidoExistente() throws Exception {
        // Arrange
        Long id = 1L;
        // StatusPedido novoStatus = StatusPedido.EM_PREPARACAO;
        Pedido pedido = PedidoHelper.criarPedido();
        pedido.atualizarStatus();
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedido);

        PedidoEntity pedidoEntity = PedidoEntityHelper.criarPedidoEntities(pedidos).get(0);
        PedidoEntity pedidoAtualizado = PedidoEntityHelper.criarPedidoEntities(pedidos).get(0);
        pedidoAtualizado.setStatus(pedido.getStatus());

        when(pedidoEntityRepository.findById(id)).thenReturn(Optional.of(pedidoEntity));
        when(pedidoEntityRepository.save(pedidoEntity)).thenReturn(pedidoAtualizado);
        when(pedidoEntityMapper.toDomain(pedidoAtualizado)).thenReturn(pedido);

        // Act
        Pedido result = repositorioPedidoGatewayImpl.atualizarStatusPedido(id, pedido.getStatus());

        // Assert
        assertEquals(pedido, result);
        assertEquals(pedido.getStatus(), result.getStatus());
    }

    @Test
    void deveRetornarNuloQuandoPedidoNaoExistirParaAtualizarStatus() throws Exception {
        // Arrange
        Long id = 1L;
        StatusPedido novoStatus = StatusPedido.EM_PREPARACAO;

        when(pedidoEntityRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Pedido result = repositorioPedidoGatewayImpl.atualizarStatusPedido(id, novoStatus);

        // Assert
        assertEquals(null, result);
    }

    @Test
    void deveListarPedidosPorStatus() throws Exception {
        // Arrange
        Pedido pedidoEmCancelado = PedidoHelper.criarPedido(StatusPedido.CANCELADO);
        Pedido pedidoEmCancelado2 = PedidoHelper.criarPedido(StatusPedido.CANCELADO);
        StatusPedido status = StatusPedido.CANCELADO;

        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedidoEmCancelado);
        pedidos.add(pedidoEmCancelado2);

        List<PedidoEntity> pedidoEntities = PedidoEntityHelper.criarPedidoEntities(pedidos);

        when(pedidoEntityRepository.findAllByStatus(status)).thenReturn(pedidoEntities);
        when(pedidoEntityMapper.toDomain(pedidoEntities)).thenReturn(pedidos);

        // Act
        List<Pedido> result = repositorioPedidoGatewayImpl.listarPedidosPorStatus(status);

        // Assert
        assertEquals(pedidos, result);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistirPedidosComStatus() throws Exception {
        // Arrange
        StatusPedido status = StatusPedido.EM_PREPARACAO;

        when(pedidoEntityRepository.findAllByStatus(status)).thenReturn(new ArrayList<>());

        // Act
        List<Pedido> result = repositorioPedidoGatewayImpl.listarPedidosPorStatus(status);

        // Assert
        assertEquals(0, result.size());
    }
}
