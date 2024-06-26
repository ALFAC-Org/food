package br.com.alfac.food.core.application.item.ports;

import java.util.List;
import java.util.Optional;

import br.com.alfac.food.core.application.item.dto.ItemDTO;
import br.com.alfac.food.core.domain.item.CategoriaItem;
import br.com.alfac.food.core.domain.item.Item;

public interface ItemRepository {

    List<Item> consultarItens();

    List<Item> consultarItensPorCategoria(CategoriaItem categoria);

    Optional<Item> consultarItemPorId(Long id);

    Item cadastrarItem(Item item);

    Item atualizarItem(Long id, ItemDTO item);

    Item excluirItem(Long id);
}
