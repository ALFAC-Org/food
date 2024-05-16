package br.com.alfac.food.core.application.item.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.alfac.food.core.application.item.dto.ItemDTO;
import br.com.alfac.food.core.application.item.ports.ItemRepository;
import br.com.alfac.food.core.application.item.ports.ItemService;
import br.com.alfac.food.core.domain.item.CategoriaItem;
import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.exception.item.ItemErros;

public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ItemDTO> consultarItens() throws FoodException {
        List<Item> itemList = itemRepository.consultarItens();

        if (itemList == null || itemList.isEmpty()) {
            throw new FoodException(ItemErros.ITEM_NAO_ENCONTRADO);
        }

        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (Item item : itemList) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setNome(item.getNome());
            itemDTO.setPreco(item.getPreco());
            itemDTO.setCategoria(item.getCategoria().toString());
            itemDTOList.add(itemDTO);
        }

        return itemDTOList;
    }

    @Override
    public List<ItemDTO> consultarItensPorCategoria(CategoriaItem categoria) throws FoodException {
        List<Item> itemList = itemRepository.consultarItensPorCategoria(categoria);

        if (itemList == null || itemList.isEmpty()) {
            throw new FoodException(ItemErros.ITEM_NAO_ENCONTRADO);
        }

        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (Item item : itemList) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setNome(item.getNome());
            itemDTO.setPreco(item.getPreco());
            itemDTO.setCategoria(item.getCategoria().toString());
            itemDTOList.add(itemDTO);
        }

        return itemDTOList;
    }

    
    @Override
    public ItemDTO consultarItemPorId(Long id) throws FoodException {
        Optional<Item> itemOpt = itemRepository.consultarItemPorId(id);

        Item item = itemOpt.orElseThrow(() -> new FoodException(ItemErros.ITEM_NAO_ENCONTRADO));

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setNome(item.getNome());
        itemDTO.setPreco(item.getPreco());
        itemDTO.setCategoria(item.getCategoria().toString());

        return itemDTO;
    }

    @Override
    public void cadastrarItem(Item item) {

    }

    @Override
    public ItemDTO atualizarItem(Long id, ItemDTO item) throws FoodException {
        Item itemAtualizado = itemRepository.atualizarItem(id, item);

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setNome(itemAtualizado.getNome());
        itemDTO.setPreco(itemAtualizado.getPreco());
        itemDTO.setCategoria(itemAtualizado.getCategoria().toString());

        return itemDTO;
    }

    @Override
    public ItemDTO excluirItem(Long id) throws FoodException {
        Item item = itemRepository.excluirItem(id);

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setNome(item.getNome());
        itemDTO.setPreco(item.getPreco());
        itemDTO.setCategoria(item.getCategoria().toString());

        return itemDTO;
    }
}
