package br.com.alfac.food.infra.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.alfac.food.core.domain.item.CategoriaItem;
import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.core.domain.pedido.Lanche;

public class LancheHelper {
   public static Lanche criarLanche() {
        Lanche lanche = new Lanche();
        List<Item> complementos = new ArrayList<>();

        complementos.add(ItemHelper.criarItem(20L, "Mais bacon", new BigDecimal("2.0"), CategoriaItem.COMPLEMENTO));

        lanche.setId(1L);
        lanche.setNome("X-Bacon");
        lanche.setPreco(new BigDecimal("10.0"));
        lanche.setCategoria(CategoriaItem.LANCHE);
        lanche.setComplementos(complementos);

        return lanche;
    }
}
