package br.com.alfac.food.infra.pedido.mapper;

import br.com.alfac.food.core.domain.pedido.Combo;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.domain.pedido.StatusPedido;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.infra.pedido.persistence.ComboEntity;
import br.com.alfac.food.infra.pedido.persistence.PedidoEntity;
import br.com.alfac.food.infra.utils.ComboHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class PedidoEntityMapperTest {

    private PedidoEntityMapper mapper;


    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(PedidoEntityMapper.class);
    }

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



}