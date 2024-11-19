package br.com.alfac.food.infra.pedido.mapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import br.com.alfac.food.core.application.pedido.dto.PedidoDTO;
import br.com.alfac.food.infra.pedido.dto.ComboRequest;
import br.com.alfac.food.infra.pedido.dto.ItemRequest;
import br.com.alfac.food.infra.pedido.dto.LancheRequest;
import br.com.alfac.food.infra.pedido.dto.PedidoRequest;

public class PedidoMapperTest {

    private PedidoMapper mapper = Mappers.getMapper(PedidoMapper.class);

    @Test
    public void testToDTO() {
        PedidoRequest pedidoRequest = new PedidoRequest();
        List<ComboRequest> combos = new ArrayList<>();
        ComboRequest combo = new ComboRequest();

        LancheRequest lanche = new LancheRequest();
        lanche.setObservacoes("Observacoes");
        lanche.setId(1L);

        ItemRequest item = new ItemRequest();
        item.setId(1L);

        ItemRequest item2 = new ItemRequest();
        item2.setId(2L);

        List<ItemRequest> complementos = new ArrayList<>();
        complementos.add(item);
        complementos.add(item2);
        lanche.setComplementos(complementos);

        combo.setLanche(lanche);
        combos.add(combo);
        pedidoRequest.setCombos(combos);
        Long clienteId = 1L;

        PedidoDTO pedidoDTO = mapper.toDTO(pedidoRequest, clienteId);

        assertNotNull(pedidoDTO);
        assertEquals("Observacoes", pedidoDTO.getCombos().get(0).getLanche().getObservacoes());
        assertEquals(1L, pedidoDTO.getCombos().get(0).getLanche().getId());
        assertEquals(clienteId, pedidoDTO.getClienteId());
    }
}