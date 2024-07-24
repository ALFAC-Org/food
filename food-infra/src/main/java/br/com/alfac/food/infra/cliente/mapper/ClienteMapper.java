package br.com.alfac.food.infra.cliente.mapper;

import br.com.alfac.food.infra.cliente.dto.ClienteRequest;
import br.com.alfac.food.core.application.cliente.dto.ClienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);
    ClienteDTO toDTO(ClienteRequest clienteRequest);

}
