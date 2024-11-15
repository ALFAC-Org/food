package br.com.alfac.food.infra.item.mapper;

import org.mapstruct.Mapper;

import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.infra.item.persistence.ItemEntity;
import jakarta.annotation.Generated;

@Generated("jacoco")
@Mapper(componentModel = "spring")
public interface ItemEntityMapper {

    ItemEntity toEntity(Item item);

    Item toDomain(ItemEntity Item);

}
