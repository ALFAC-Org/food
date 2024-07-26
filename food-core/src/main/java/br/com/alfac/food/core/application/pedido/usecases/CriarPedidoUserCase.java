package br.com.alfac.food.core.application.pedido.usecases;

import br.com.alfac.food.core.application.cliente.adapters.gateways.RepositorioClienteGateway;
import br.com.alfac.food.core.application.cliente.usecases.ConsultarClientePorIdUseCase;
import br.com.alfac.food.core.application.item.adapters.gateways.RepositorioItemGateway;
import br.com.alfac.food.core.application.item.dto.ItemDTO;
import br.com.alfac.food.core.application.item.usecases.ConsultarItemPorIdUseCase;
import br.com.alfac.food.core.application.pedido.dto.ComboDTO;
import br.com.alfac.food.core.application.pedido.dto.LancheDTO;
import br.com.alfac.food.core.application.pedido.dto.PedidoDTO;
import br.com.alfac.food.core.application.pedido.adapters.gateways.RepositorioPedidoGateway;
import br.com.alfac.food.core.domain.cliente.Cliente;
import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.core.domain.pedido.Combo;
import br.com.alfac.food.core.domain.pedido.ComboBuilder;
import br.com.alfac.food.core.domain.pedido.Lanche;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.exception.combo.ComboError;
import br.com.alfac.food.core.utils.CollectionsUtils;

import java.util.Objects;

public class CriarPedidoUserCase {

    private final RepositorioPedidoGateway repositorioPedidoGateway;
    private final ConsultarClientePorIdUseCase consultarClientePorIdUseCase;
    private final ConsultarItemPorIdUseCase consultarItemPorIdUseCase;

    public CriarPedidoUserCase(final RepositorioPedidoGateway repositorioPedidoGateway, final RepositorioClienteGateway clienteRepository,
                               final RepositorioItemGateway itemRepository) {
        this.repositorioPedidoGateway = repositorioPedidoGateway;
        this.consultarClientePorIdUseCase = new ConsultarClientePorIdUseCase(clienteRepository);
        this.consultarItemPorIdUseCase = new ConsultarItemPorIdUseCase(itemRepository);
    }

    public Pedido executar(PedidoDTO pedidoDTO) throws FoodException {

        Cliente cliente = null;

        if (pedidoDTO.getClienteId() != null) {
            cliente = consultarClientePorIdUseCase.execute(pedidoDTO.getClienteId());
        }

        if (CollectionsUtils.vazio(pedidoDTO.getCombos())) {
            throw new FoodException(ComboError.COMBO_VAZIO);
        }

        Pedido pedido = new Pedido(cliente);
        for (ComboDTO comboDTO : pedidoDTO.getCombos()) {
            Combo combo = ComboBuilder.combo()
                    .comLanche(buscarLanche(comboDTO.getLanche()))
                    .comAcompanhamento(buscarItem(comboDTO.getAcompanhamento()))
                    .comBebida(buscarItem(comboDTO.getBebida()))
                    .comSobremesa(buscarItem(comboDTO.getSobremesa()))
                    .build();

            pedido.adicionaCombo(combo);
        }

        return repositorioPedidoGateway.registrarPedido(pedido);
    }

    private Lanche buscarLanche(final LancheDTO lancheDTO) throws FoodException {

        if (Objects.nonNull(lancheDTO)) {
            Long lancheId = lancheDTO.getId();
            Item item = consultarItemPorIdUseCase.executar(lancheId);

            Lanche lanche = new Lanche();
            lanche.setNome(item.getNome());
            lanche.setId(item.getId());
            lanche.setPreco(item.getPreco());
            lanche.setCategoria(item.getCategoria());
            lanche.setObservacoes(lancheDTO.getObservacoes());
            lanche.setCategoria(item.getCategoria());

            if (CollectionsUtils.naoVazio(lancheDTO.getComplementos())) {
                for (ItemDTO complementoDTO : lancheDTO.getComplementos()) {
                    lanche.adicionaComplemento(buscarItem(complementoDTO));
                }
            }

            return lanche;
        }
        return null;
    }

    private Item buscarItem(final ItemDTO itemDTO) throws FoodException {
        if (Objects.nonNull(itemDTO)) {
            Long itemId = itemDTO.getId();
            return consultarItemPorIdUseCase.executar(itemId);
        }
        return null;
    }
}
