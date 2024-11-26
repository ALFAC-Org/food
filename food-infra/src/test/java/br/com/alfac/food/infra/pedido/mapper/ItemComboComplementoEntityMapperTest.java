package br.com.alfac.food.infra.pedido.mapper;

import br.com.alfac.food.core.domain.item.CategoriaItem;
import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.infra.pedido.persistence.ItemComboComplementoEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemComboComplementoEntityMapperTest {

    private final ItemComboComplementoEntityMapper mapper = ItemComboComplementoEntityMapper.INSTANCE;

    @Test
    void testToEntity() {
        Item item = new Item();
        item.setId(1L);
        item.setNome("Test Item");
        item.setCategoria(CategoriaItem.BEBIDA);

        ItemComboComplementoEntity entity = mapper.toEntity(item);

        assertNotNull(entity);
        assertEquals(item.getId(), entity.getItemId());
        assertNull(entity.getId());
    }

    @Test
    void testToDomain() {
        ItemComboComplementoEntity entity = new ItemComboComplementoEntity();
        entity.setId(1L);
        entity.setPreco(10.0);


        entity.setItemId(1L);

        Item item = mapper.toDomain(entity);

        assertNotNull(item);
        assertEquals(entity.getId(), item.getId());

    }
}
