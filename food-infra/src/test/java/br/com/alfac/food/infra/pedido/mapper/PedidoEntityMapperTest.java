package br.com.alfac.food.infra.pedido.mapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alfac.food.core.domain.pedido.Combo;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.domain.pedido.StatusPedido;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.infra.cliente.mapper.ClienteEntityMapper;
import br.com.alfac.food.infra.pedido.persistence.ComboEntity;
import br.com.alfac.food.infra.pedido.persistence.PedidoEntity;
import br.com.alfac.food.infra.utils.ComboHelper;

@ExtendWith(MockitoExtension.class)
public class PedidoEntityMapperTest {

    private PedidoEntityMapper mapper;

    @Mock
    private ClienteEntityMapper clienteEntityMapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(PedidoEntityMapper.class);
    }

    // @Test
    // public void testToEntity() throws FoodException {
    //     Pedido pedido = PedidoHelper.criarPedidoComCliente();

    //     ClienteEntity cliente = new ClienteEntity();
    //     cliente.setId(1L);
    //     cliente.setUuid(UUID.randomUUID());
    //     cliente.setCpf("12345678901");
    //     cliente.setNome("John Doe");
    //     cliente.setEmail("john@doe.com");
        
    //     when(clienteEntityMapper.toEntity(pedido.getCliente())).thenReturn(cliente);

    //     PedidoEntity pedidoEntity = mapper.toEntity(pedido);

    //     assertNotNull(pedidoEntity);
    //     assertEquals(pedido.getId(), pedidoEntity.getId());
    //     assertEquals(pedido.getStatus(), pedidoEntity.getStatus());
    //     assertEquals(pedido.getCombos().size(), pedidoEntity.getCombos().size());
    // }

    @Test
    public void testToDomain() throws FoodException {
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(1L);
        pedidoEntity.setStatus(StatusPedido.RECEBIDO);
        pedidoEntity.setCombos(new ArrayList<>());
        pedidoEntity.setDataCadastro(LocalDateTime.now());

        Pedido pedido = mapper.toDomain(pedidoEntity);

        assertNotNull(pedido);
        assertEquals(pedidoEntity.getId(), pedido.getId());
        assertEquals(pedidoEntity.getStatus(), pedido.getStatus());
        assertEquals(pedidoEntity.getCombos().size(), pedido.getCombos().size());
    }

    @Test
    public void testCombosToEntity() {
        List<Combo> combos = new ArrayList<>();
        Combo combo = ComboHelper.criarCombo();
        combos.add(combo);

        List<ComboEntity> comboEntities = mapper.combosToEntity(combos);

        assertNotNull(comboEntities);
        assertEquals(combos.size(), comboEntities.size());
    }

    @Test
    public void testCombosToDomain() {
        List<ComboEntity> comboEntities = new ArrayList<>();
        ComboEntity comboEntity = new ComboEntity();
        comboEntity.setItens(new ArrayList<>());
        comboEntities.add(comboEntity);

        List<Combo> combos = mapper.combosToDomain(comboEntities);

        assertNotNull(combos);
        assertEquals(comboEntities.size(), combos.size());
    }


}