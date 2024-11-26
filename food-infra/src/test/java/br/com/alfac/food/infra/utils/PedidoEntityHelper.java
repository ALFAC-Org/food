package br.com.alfac.food.infra.utils;

import java.util.ArrayList;
import java.util.List;

import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.core.domain.pedido.Combo;
import br.com.alfac.food.core.domain.pedido.Lanche;
import br.com.alfac.food.core.domain.pedido.Pedido;

import br.com.alfac.food.infra.pedido.persistence.ComboEntity;
import br.com.alfac.food.infra.pedido.persistence.ItemComboComplementoEntity;
import br.com.alfac.food.infra.pedido.persistence.ItemComboEntity;
import br.com.alfac.food.infra.pedido.persistence.PedidoEntity;

public class PedidoEntityHelper {

    public static List<PedidoEntity> criarPedidoEntities(List<Pedido> pedidos) {
        List<PedidoEntity> pedidoEntities = new ArrayList<>();

        for (Pedido pedido : pedidos) {


            List<ComboEntity> comboEntities = new ArrayList<>();
            for (Combo combo : pedido.getCombos()) {

                List<ItemComboEntity> itemComboEntitites = new ArrayList<>();
                ComboEntity comboEntity = new ComboEntity();

                for (Item item : combo.getItens()) {
                    ItemComboEntity itemComboEntity = new ItemComboEntity();


                    if (item instanceof Lanche lanche) {
                        itemComboEntity.setId(item.getId());
                        itemComboEntity.setObservacoes(lanche.getObservacoes());
                        itemComboEntity.setPreco((Double) item.getPreco().doubleValue());
                        itemComboEntity.setItemId(1L);

                        List<ItemComboComplementoEntity> itemComboComplementoEntities = new ArrayList<>();
                        for (Item complemento : ((Lanche) item).getComplementos()) {
                            ItemComboComplementoEntity itemComboComplementoEntity = new ItemComboComplementoEntity();
                            itemComboComplementoEntity.setId(complemento.getId());
                            itemComboComplementoEntity.setPreco((Double) complemento.getPreco().doubleValue());
                            itemComboComplementoEntities.add(itemComboComplementoEntity);
                        }

                        itemComboEntity.setComplementos(itemComboComplementoEntities);
                    } else {
                        itemComboEntity.setId(item.getId());
                        itemComboEntity.setPreco((Double) item.getPreco().doubleValue());
                        itemComboEntity.setPreco((Double) item.getPreco().doubleValue());
                        itemComboEntity.setComplementos(null);
                        itemComboEntity.setObservacoes(null);
                        itemComboEntity.setItemId(1L);
                    }

                    itemComboEntitites.add(itemComboEntity);
                }

                comboEntity.setItens(itemComboEntitites);
                comboEntities.add(comboEntity);
            }

            PedidoEntity pedidoEntity = new PedidoEntity();
            pedidoEntity.setId(pedido.getId());
            pedidoEntity.setClienteId(pedido.getClienteId());
            pedidoEntity.setStatus(pedido.getStatus());
            pedidoEntity.setCombos(comboEntities);
            pedidoEntity.setDataCadastro(pedido.getDataCadastro());

            pedidoEntities.add(pedidoEntity);
        }

        return pedidoEntities;
    }
}
