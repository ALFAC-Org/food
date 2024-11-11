package utils;

import br.com.alfac.food.core.domain.item.CategoriaItem;
import br.com.alfac.food.core.domain.pedido.Combo;

public class ComboHelper {
    public static Combo criarCombo() {
        Combo combo = new Combo();

        combo.setLanche(LancheHelper.criarLanche());
        combo.setAcompanhamento(ItemHelper.criarItem(101L, CategoriaItem.ACOMPANHAMENTO));
        combo.setBebida(ItemHelper.criarItem(102L, CategoriaItem.BEBIDA));
        combo.setSobremesa(ItemHelper.criarItem(103L, CategoriaItem.SOBREMESA));

        return combo;
    }
}
