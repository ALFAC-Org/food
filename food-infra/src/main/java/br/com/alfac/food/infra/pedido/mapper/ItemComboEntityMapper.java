package br.com.alfac.food.infra.pedido.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.core.domain.pedido.Lanche;
import br.com.alfac.food.infra.pedido.persistence.ItemComboComplementoEntity;
import br.com.alfac.food.infra.pedido.persistence.ItemComboEntity;

@Mapper(componentModel = "spring")
public interface ItemComboEntityMapper {

    ItemComboEntityMapper INSTANCE = Mappers.getMapper(ItemComboEntityMapper.class);

    @Mapping(target = "item.id", source = "id")
    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "item.nome", source = "nome")
    @Mapping(target = "item.categoria", source = "categoria")
    @Mapping(target = "item.preco", source = "preco")
    @Mapping(target = "preco", source = "preco")
    @Mapping(target = "complementos", ignore = true)
    @Mapping(target = "observacoes", ignore = true)
    ItemComboEntity itemToEntity(Item item);

    @Mapping(target = "item.id", source = "id")
    @Mapping(target = "item.nome", source = "nome")
    @Mapping(target = "item.categoria", source = "categoria")
    @Mapping(target = "item.preco", source = "preco")
    @Mapping(target = "preco", source = "preco")
    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "complementos", source = "complementos", qualifiedByName = "itemComboToEntityParser")
    ItemComboEntity lancheToEntity(Lanche lanche);

    @Mapping(target = "id", source = "item.id")
    @Mapping(target = "nome", source = "item.nome")
    @Mapping(target = "categoria", source = "item.categoria")
    Item toItemDomain(ItemComboEntity item);

    @Mapping(target = "id", source = "item.id")
    @Mapping(target = "nome", source = "item.nome")
    @Mapping(target = "categoria", source = "item.categoria")
    @Mapping(target = "complementos", source = "complementos", qualifiedByName = "itemComboToDomainParser")
    Lanche toLancheDomain(ItemComboEntity item); 

    @Named("itemComboToEntityParser")
    default List<ItemComboComplementoEntity> itemComboToEntityParser(List<Item> complementos) {
        List<ItemComboComplementoEntity> complementosEntities = null;
        if(complementos != null){
            complementosEntities = new ArrayList<>(); 
            for(Item complemento : complementos){
                complementosEntities.add(ItemComboComplementoEntityMapper.INSTANCE.toEntity(complemento));
            }
        }
        return complementosEntities;
    }

    @Named("itemComboToDomainParser")
    default List<Item> itemComboToDomainParser(List<ItemComboComplementoEntity> complementosEntities) {
        List<Item> complementos = null;
        if(complementosEntities != null){
            complementos = new ArrayList<>(); 
            for(ItemComboComplementoEntity complementoEntity : complementosEntities){
                complementos.add(ItemComboComplementoEntityMapper.INSTANCE.toDomain(complementoEntity));
            }
        }
        return complementos;
    }

}
