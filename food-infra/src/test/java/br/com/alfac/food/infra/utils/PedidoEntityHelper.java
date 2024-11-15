package br.com.alfac.food.infra.utils;

import java.util.ArrayList;
import java.util.List;

import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.core.domain.pedido.Combo;
import br.com.alfac.food.core.domain.pedido.Lanche;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.infra.cliente.persistence.ClienteEntity;
import br.com.alfac.food.infra.item.persistence.ItemEntity;
import br.com.alfac.food.infra.pedido.persistence.ComboEntity;
import br.com.alfac.food.infra.pedido.persistence.ItemComboComplementoEntity;
import br.com.alfac.food.infra.pedido.persistence.ItemComboEntity;
import br.com.alfac.food.infra.pedido.persistence.PedidoEntity;

public class PedidoEntityHelper {

    public static List<PedidoEntity> criarPedidoEntities(List<Pedido> pedidos) {
        List<PedidoEntity> pedidoEntities = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            ClienteEntity clienteEntity = new ClienteEntity();

            if (pedido.getCliente() != null) {
                clienteEntity.setId(pedido.getCliente().getId());
                clienteEntity.setNome(pedido.getCliente().getNome());
                clienteEntity.setCpf(pedido.getCliente().getCpf().getNumero());
                clienteEntity.setEmail(pedido.getCliente().getEmail());
                clienteEntity.setUuid(pedido.getCliente().getUuid());
            }

            List<ComboEntity> comboEntities = new ArrayList<>();
            for (Combo combo : pedido.getCombos()) {

                List<ItemComboEntity> itemComboEntitites = new ArrayList<>();
                ComboEntity comboEntity = new ComboEntity();

                for (Item item : combo.getItens()) {
                    ItemComboEntity itemComboEntity = new ItemComboEntity();

                    ItemEntity itemEntity = new ItemEntity();
                    itemEntity.setId(item.getId());
                    itemEntity.setPreco(item.getPreco());
                    itemEntity.setCategoria(item.getCategoria());
                    itemEntity.setNome(item.getNome());

                    if (item instanceof Lanche lanche) {
                        itemComboEntity.setId(item.getId());
                        itemComboEntity.setObservacoes(lanche.getObservacoes());
                        itemComboEntity.setPreco((Double) item.getPreco().doubleValue());
                        itemComboEntity.setItem(itemEntity);

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
                        itemComboEntity.setItem(itemEntity);
                    }

                    itemComboEntitites.add(itemComboEntity);
                }

                comboEntity.setItens(itemComboEntitites);
                comboEntities.add(comboEntity);
            }

            PedidoEntity pedidoEntity = new PedidoEntity();
            pedidoEntity.setId(pedido.getId());
            pedidoEntity.setCliente(clienteEntity);
            pedidoEntity.setStatus(pedido.getStatus());
            pedidoEntity.setCombos(comboEntities);
            pedidoEntity.setDataCadastro(pedido.getDataCadastro());

            pedidoEntities.add(pedidoEntity);
        }

        return pedidoEntities;
    }
}
