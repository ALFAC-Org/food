package br.com.alfac.food.infra.cliente.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.alfac.food.core.domain.cliente.Cliente;
import br.com.alfac.food.infra.cliente.persistence.ClienteEntity;
import jakarta.annotation.Generated;

@Generated("jacoco")
@Mapper(componentModel = "spring")
public interface ClienteEntityMapper {

    ClienteEntityMapper INSTANCE = Mappers.getMapper(ClienteEntityMapper.class);

    @Mapping(target = "cpf", source = "cpf.numero")
    ClienteEntity toEntity(Cliente cliente);

    @Mapping(target = "cpf.numero", source = "cpf")
    Cliente toDomain(ClienteEntity cliente);
}
