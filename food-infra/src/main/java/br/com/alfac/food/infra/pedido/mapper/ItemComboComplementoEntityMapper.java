package br.com.alfac.food.infra.pedido.mapper;

import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.infra.pedido.persistence.ItemComboComplementoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemComboComplementoEntityMapper {

    ItemComboComplementoEntityMapper INSTANCE = Mappers.getMapper(ItemComboComplementoEntityMapper.class);
    @Mapping(target = "itemId", source = "id")
    @Mapping(target = "id", source = "id", ignore = true)
    ItemComboComplementoEntity toEntity(Item item);

    @Mapping(target = "id", source = "itemId")
    Item toDomain(ItemComboComplementoEntity item);

}
    