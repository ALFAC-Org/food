package br.com.alfac.food.infra.pedido.mapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import br.com.alfac.food.core.domain.item.CategoriaItem;
import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.core.domain.pedido.Lanche;
import br.com.alfac.food.infra.item.persistence.ItemEntity;
import br.com.alfac.food.infra.pedido.persistence.ItemComboComplementoEntity;
import br.com.alfac.food.infra.pedido.persistence.ItemComboEntity;

public class ItemComboEntityMapperTest {

    @Test
    public void testItemToEntity() {
        Item item = new Item();
        item.setId(1L);
        item.setNome("Burger");
        item.setCategoria(CategoriaItem.ACOMPANHAMENTO);
        item.setPreco(new java.math.BigDecimal("10.00"));

        ItemComboEntity entity = ItemComboEntityMapper.INSTANCE.itemToEntity(item);

        assertEquals(item.getId(), entity.getItem().getId());
        assertEquals(item.getNome(), entity.getItem().getNome());
        assertEquals(item.getCategoria(), entity.getItem().getCategoria());
        assertEquals(item.getPreco(), entity.getItem().getPreco());
        assertEquals((Double) item.getPreco().doubleValue(), entity.getPreco());
        assertNull(entity.getId());
        assertNull(entity.getComplementos());
        assertNull(entity.getObservacoes());
    }

    @Test
    public void testLancheToEntity() {
        Lanche lanche = new Lanche();
        lanche.setId(2L);
        lanche.setNome("Combo Meal");
        lanche.setCategoria(CategoriaItem.LANCHE);
        lanche.setPreco(new java.math.BigDecimal("20.00"));

        List<Item> complementos = new ArrayList<>();
        Item complemento = new Item();
        complemento.setId(3L);
        complemento.setNome("Fries");
        complemento.setCategoria(CategoriaItem.ACOMPANHAMENTO);
        complemento.setPreco(new java.math.BigDecimal("5.00"));
        complementos.add(complemento);
        lanche.setComplementos(complementos);

        ItemComboEntity entity = ItemComboEntityMapper.INSTANCE.lancheToEntity(lanche);

        assertEquals(lanche.getId(), entity.getItem().getId());
        assertEquals(lanche.getNome(), entity.getItem().getNome());
        assertEquals(lanche.getCategoria(), entity.getItem().getCategoria());
        assertEquals(lanche.getPreco(), entity.getItem().getPreco());
        assertEquals((Double) lanche.getPreco().doubleValue(), entity.getPreco());
        assertNull(entity.getId());
        assertEquals(1, entity.getComplementos().size());
        ItemComboComplementoEntity complementoEntity = entity.getComplementos().get(0);
        assertEquals(complemento.getId(), complementoEntity.getItem().getId());
    }

    @Test
    public void testToItemDomain() {
        ItemComboEntity entity = new ItemComboEntity();
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1L);
        itemEntity.setNome("Burger");
        itemEntity.setCategoria(CategoriaItem.ACOMPANHAMENTO);
        itemEntity.setPreco(new java.math.BigDecimal("5.00"));

        entity.setItem(itemEntity);

        Item item = ItemComboEntityMapper.INSTANCE.toItemDomain(entity);

        assertEquals(entity.getItem().getId(), item.getId());
        assertEquals(entity.getItem().getNome(), item.getNome());
        assertEquals(entity.getItem().getCategoria(), item.getCategoria());
    }

    @Test
    public void testToLancheDomain() {
        ItemComboEntity entity = new ItemComboEntity();
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(2L);
        itemEntity.setNome("Combo Meal");
        itemEntity.setCategoria(CategoriaItem.LANCHE);
        itemEntity.setPreco(new java.math.BigDecimal("20.00"));

        List<ItemComboComplementoEntity> complementoEntities = new ArrayList<>();
        ItemComboComplementoEntity complementoEntity = new ItemComboComplementoEntity();
        ItemEntity complementoItemEntity = new ItemEntity();
        complementoItemEntity.setId(3L);
        complementoItemEntity.setNome("Fries");
        complementoItemEntity.setCategoria(CategoriaItem.ACOMPANHAMENTO);
        complementoItemEntity.setPreco(new java.math.BigDecimal("5.00"));
        complementoEntity.setItem(complementoItemEntity);
        complementoEntities.add(complementoEntity);

        entity.setItem(itemEntity);
        entity.setComplementos(complementoEntities);

        Lanche lanche = ItemComboEntityMapper.INSTANCE.toLancheDomain(entity);

        assertEquals(entity.getItem().getId(), lanche.getId());
        assertEquals(entity.getItem().getNome(), lanche.getNome());
        assertEquals(entity.getItem().getCategoria(), lanche.getCategoria());
        assertEquals(1, lanche.getComplementos().size());
        Item complemento = lanche.getComplementos().get(0);
        assertEquals(complementoEntity.getItem().getId(), complemento.getId());
        assertEquals(complementoEntity.getItem().getNome(), complemento.getNome());
        assertEquals(complementoEntity.getItem().getCategoria(), complemento.getCategoria());
    }

    @Test
    public void testItemComboToEntityParser_withNullComplementos() {
        List<ItemComboComplementoEntity> result = ItemComboEntityMapper.INSTANCE.itemComboToEntityParser(null);
        assertNull(result);
    }

    @Test
    public void testItemComboToEntityParser_withEmptyComplementos() {
        List<Item> complementos = new ArrayList<>();
        List<ItemComboComplementoEntity> result = ItemComboEntityMapper.INSTANCE.itemComboToEntityParser(complementos);
        assertEquals(0, result.size());
    }

    @Test
    public void testItemComboToEntityParser_withComplementos() {
        List<Item> complementos = new ArrayList<>();
        Item complemento = new Item();
        complemento.setId(3L);
        complemento.setNome("Fries");
        complemento.setCategoria(CategoriaItem.ACOMPANHAMENTO);
        complemento.setPreco(new java.math.BigDecimal("5.00"));
        complementos.add(complemento);

        List<ItemComboComplementoEntity> result = ItemComboEntityMapper.INSTANCE.itemComboToEntityParser(complementos);

        assertEquals(1, result.size());
        ItemComboComplementoEntity complementoEntity = result.get(0);
        assertEquals(complemento.getId(), complementoEntity.getItem().getId());
    }

    @Test
    public void testItemComboToDomainParser_withNullComplementosEntities() {
        List<Item> result = ItemComboEntityMapper.INSTANCE.itemComboToDomainParser(null);
        assertNull(result);
    }

    @Test
    public void testItemComboToDomainParser_withEmptyComplementosEntities() {
        List<ItemComboComplementoEntity> complementosEntities = new ArrayList<>();
        List<Item> result = ItemComboEntityMapper.INSTANCE.itemComboToDomainParser(complementosEntities);
        assertEquals(0, result.size());
    }

    @Test
    public void testItemComboToDomainParser_withComplementosEntities() {
        List<ItemComboComplementoEntity> complementosEntities = new ArrayList<>();
        ItemComboComplementoEntity complementoEntity = new ItemComboComplementoEntity();
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(3L);
        itemEntity.setNome("Fries");
        itemEntity.setCategoria(CategoriaItem.ACOMPANHAMENTO);
        itemEntity.setPreco(new java.math.BigDecimal("5.00"));
        complementoEntity.setItem(itemEntity);
        complementosEntities.add(complementoEntity);

        List<Item> result = ItemComboEntityMapper.INSTANCE.itemComboToDomainParser(complementosEntities);

        assertEquals(1, result.size());
        Item complemento = result.get(0);
        assertEquals(complementoEntity.getItem().getId(), complemento.getId());
        assertEquals(complementoEntity.getItem().getNome(), complemento.getNome());
        assertEquals(complementoEntity.getItem().getCategoria(), complemento.getCategoria());
    }
}
