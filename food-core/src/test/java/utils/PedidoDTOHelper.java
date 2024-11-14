package utils;

import br.com.alfac.food.core.application.pedido.dto.PedidoDTO;
import br.com.alfac.food.core.domain.pedido.StatusPedido;

import java.math.BigDecimal;

abstract public class PedidoDTOHelper {

    public static PedidoDTO criarPedidoDTO() {
        var pedidoDTO = new PedidoDTO();

        pedidoDTO.setId(1L);
        pedidoDTO.setClienteId(1L);
        pedidoDTO.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedidoDTO.setValorTotal(BigDecimal.TEN);

        return pedidoDTO;
    }
}
