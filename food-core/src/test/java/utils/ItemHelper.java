package utils;

import br.com.alfac.food.core.domain.item.CategoriaItem;
import br.com.alfac.food.core.domain.item.Item;

import java.math.BigDecimal;

public class ItemHelper {
    public static Item criarItem(Long id, String nome, BigDecimal preco, CategoriaItem categoria) {
        Item item = new Item();
        item.setId(id);
        item.setNome(nome);
        item.setPreco(preco);
        item.setCategoria(categoria != null ? categoria : CategoriaItem.ACOMPANHAMENTO);

        return item;
    }

    public static Item criarItem(Long id, CategoriaItem categoria) {
        return criarItem(id, "Item 1", new BigDecimal("10.00"), categoria);
    }

    public static Item criarItem(CategoriaItem categoria) {
        return criarItem(1L, "Item 1", new BigDecimal("10.00"), categoria);
    }
}
