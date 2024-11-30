package br.com.alfac.food.core.application.pedido.usecases;

import br.com.alfac.food.core.application.item.adapters.gateways.RepositorioItemGateway;
import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.domain.item.CategoriaItem;
import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.core.domain.pedido.Lanche;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.exception.pedido.PedidoErros;
import br.com.alfac.food.core.utils.CollectionsUtils;

import java.util.*;

public class ConsultarPedidoPorIdUseCase {
    private final RepositorioPedidoGateway pedidoRepository;
    private final RepositorioItemGateway repositorioItemGateway;

    public ConsultarPedidoPorIdUseCase(final RepositorioPedidoGateway pedidoRepository, final RepositorioItemGateway repositorioItemGateway) {
        this.pedidoRepository = pedidoRepository;
        this.repositorioItemGateway = repositorioItemGateway;
    }

    public Pedido executar(Long id) throws FoodException {
        Optional<Pedido> pedidoOpt = pedidoRepository.consultarPedidoPorId(id);

        return getPedidoAtualizado(pedidoOpt.orElseThrow(() -> new FoodException(PedidoErros.PEDIDO_NAO_ENCONTRADO)));
    }

    private Pedido getPedidoAtualizado(Pedido pedido) {

        Map<Long, Item> idsItens = getItens(pedido);
        pedido.getCombos().forEach(combo -> {
            Lanche lanche = combo.getLanche();
            if (Objects.nonNull(lanche)) {
                Item item = idsItens.get(lanche.getId());
                if (item != null) {
                    lanche.setNome(item.getNome());
                    lanche.setCategoria(item.getCategoria());
                }
                if (CollectionsUtils.naoVazio(lanche.getComplementos())) {
                    lanche.getComplementos().forEach(complemento -> {
                        Item complementoItem = idsItens.get(complemento.getId());
                        if (complementoItem != null) {
                            complemento.setNome(complementoItem.getNome());
                            complemento.setCategoria(complementoItem.getCategoria());
                        }
                    });
                }
            }

            combo.getItensTmp().forEach(tmp -> {
                Item item = idsItens.get(tmp.getId());
                if (CategoriaItem.ACOMPANHAMENTO.equals(item.getCategoria())) {
                    combo.setAcompanhamento(new Item(item.getNome(), tmp.getPreco(), item.getCategoria(), item.getId()));
                } else if (CategoriaItem.BEBIDA.equals(item.getCategoria())) {
                    combo.setBebida(new Item(item.getNome(), tmp.getPreco(), item.getCategoria(), item.getId()));
                } else if (CategoriaItem.SOBREMESA.equals(item.getCategoria())) {
                    combo.setSobremesa(new Item(item.getNome(), tmp.getPreco(), item.getCategoria(), item.getId()));
                }
            });

        });
        return pedido;

    }

    private Map<Long, Item> getItens(Pedido pedido) {
        Set<Long> idsItens = new HashSet<>();

        pedido.getCombos().forEach(combo -> {
            combo.getItensTmp().forEach(item -> idsItens.add(item.getId()));
            if (Objects.nonNull(combo.getLanche())) {
                idsItens.add(combo.getLanche().getId());
                if (CollectionsUtils.naoVazio(combo.getLanche().getComplementos())) {
                    combo.getLanche().getComplementos().forEach(complemento -> idsItens.add(complemento.getId()));
                }
            }
        });

        Map<Long, Item> itens = new HashMap<>();

        idsItens.forEach(id -> {
           repositorioItemGateway.consultarItemPorId(id).ifPresent(item -> itens.put(item.getId(), item));

        });

        return itens;
    }
}
