package br.com.alfac.food.infra.cliente.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteEntityMapper {

    ClienteEntityMapper INSTANCE = Mappers.getMapper(ClienteEntityMapper.class);


}
