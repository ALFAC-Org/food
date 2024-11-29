package br.com.alfac.food.infra.pedido.mapper;

import br.com.alfac.food.core.domain.item.CategoriaItem;
import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.core.domain.pedido.Combo;
import br.com.alfac.food.core.domain.pedido.Lanche;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.utils.CollectionsUtils;
import br.com.alfac.food.infra.pedido.persistence.ComboEntity;
import br.com.alfac.food.infra.pedido.persistence.ItemComboEntity;
import br.com.alfac.food.infra.pedido.persistence.PedidoEntity;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                if (StringUtils.isNotBlank(itemEntity.getObservacoes()) ||
                        CollectionsUtils.naoVazio(itemEntity.getComplementos())){

                    Lanche lanche = new Lanche();
                    lanche.setObservacoes(itemEntity.getObservacoes());
                    lanche.setPreco(new BigDecimal(itemEntity.getPreco()));
                    lanche.setId(itemEntity.getItemId());
                    itemEntity.getComplementos().forEach(complemento -> {
                        lanche.adicionaComplemento(new Item(complemento.getItemId(), new BigDecimal(complemento.getPreco())));
                    });
                    combo.setLanche(lanche);
                } else {

                    combo.adicionarItem(new Item(itemEntity.getItemId(), new BigDecimal(itemEntity.getPreco())));
                }
            }
            combos.add(combo);
        }

        return combos;
    }
}
