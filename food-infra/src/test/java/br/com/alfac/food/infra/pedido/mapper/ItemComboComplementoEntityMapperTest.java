package br.com.alfac.food.infra.pedido.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import br.com.alfac.food.core.domain.item.CategoriaItem;
import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.infra.item.persistence.ItemEntity;
import br.com.alfac.food.infra.pedido.persistence.ItemComboComplementoEntity;

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
        assertEquals(item.getId(), entity.getItem().getId());
        assertNull(entity.getId());
    }

    @Test
    void testToDomain() {
        ItemComboComplementoEntity entity = new ItemComboComplementoEntity();
        entity.setId(1L);
        entity.setPreco(10.0);
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1L);
        itemEntity.setNome("Test Item");
        itemEntity.setCategoria(CategoriaItem.BEBIDA);

        entity.setItem(itemEntity);

        Item item = mapper.toDomain(entity);

        assertNotNull(item);
        assertEquals(entity.getId(), item.getId());
        assertEquals(entity.getItem().getNome(), item.getNome());
        assertEquals(entity.getItem().getCategoria(), item.getCategoria());
    }
}
