package br.com.alfac.food.core.application.item.adapters.gateways;

import br.com.alfac.food.core.domain.item.Item;

import java.util.Optional;

public interface RepositorioItemGateway {


    Optional<Item> consultarItemPorId(Long id);

}
