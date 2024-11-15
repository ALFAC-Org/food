package br.com.alfac.food.infra.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.alfac.food.infra.item.dto.ItemRequest;
import jakarta.annotation.Generated;
import br.com.alfac.food.core.application.item.dto.ItemDTO;

@Generated("jacoco")
@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);
    ItemDTO toDTO(ItemRequest itemRequest);
    
}
