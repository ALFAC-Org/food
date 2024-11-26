package br.com.alfac.food.infra.pedido.mapper;

import br.com.alfac.food.core.domain.item.CategoriaItem;
import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.core.domain.pedido.Combo;
import br.com.alfac.food.core.domain.pedido.Lanche;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.infra.pedido.persistence.ComboEntity;
import br.com.alfac.food.infra.pedido.persistence.ItemComboEntity;
import br.com.alfac.food.infra.pedido.persistence.PedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoEntityMapper {

    @Mapping(target = "combos", source = "combos", qualifiedByName = "combosToEntityParser")
    PedidoEntity toEntity(Pedido pedido);


    default Pedido toDomain(PedidoEntity pedido) throws FoodException {
        //
        return new Pedido(pedido.getId(), pedido.getClienteId(), pedido.getStatus(), combosToDomain(pedido.getCombos()), pedido.getDataCadastro());
    }

    List<Pedido> toDomain(List<PedidoEntity> pedido);

    @Named("combosToEntityParser")
    default List<ComboEntity> combosToEntity(List<Combo> combos) {
        List<ComboEntity> combosEntities = new ArrayList<>();

        for (Combo combo : combos) {
            List<ItemComboEntity> itensComboEntities = new ArrayList<>();
            ComboEntity comboEntity = new ComboEntity();

            for (Item item : combo.getItens()) {
                ItemComboEntity itemComboEntity = null;

                if (item instanceof Lanche) {
                    itemComboEntity = ItemComboEntityMapper.INSTANCE.lancheToEntity((Lanche) item);
                } else {
                    itemComboEntity = ItemComboEntityMapper.INSTANCE.itemToEntity(item);
                }

                itensComboEntities.add(itemComboEntity);
            }

            comboEntity.setItens(itensComboEntities);
            combosEntities.add(comboEntity);
        }
        return combosEntities;
    }

    //    @Named("combosToDomainParser")
    default List<Combo> combosToDomain(List<ComboEntity> combosEntities) {
        List<Combo> combos = new ArrayList<>();

        for (ComboEntity comboEntity : combosEntities) {
            Combo combo = new Combo();

            for (ItemComboEntity itemEntity : comboEntity.getItens()) {
// TODO: Fix this
//                if (CategoriaItem.LANCHE.equals(itemEntity.getItem().getCategoria())) {
//                    combo.setLanche(ItemComboEntityMapper.INSTANCE.toLancheDomain(itemEntity));
//                }
//                if (CategoriaItem.ACOMPANHAMENTO.equals(itemEntity.getItem().getCategoria())) {
//                    combo.setAcompanhamento(ItemComboEntityMapper.INSTANCE.toItemDomain(itemEntity));
//                }
//                if (CategoriaItem.BEBIDA.equals(itemEntity.getItem().getCategoria())) {
//                    combo.setBebida(ItemComboEntityMapper.INSTANCE.toItemDomain(itemEntity));
//                }
//                if (CategoriaItem.SOBREMESA.equals(itemEntity.getItem().getCategoria())) {
//                    combo.setSobremesa(ItemComboEntityMapper.INSTANCE.toItemDomain(itemEntity));
//                }
            }
            combos.add(combo);
        }

        return combos;
    }
}
