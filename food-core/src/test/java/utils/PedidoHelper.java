package utils;

import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.domain.pedido.StatusPedido;
import br.com.alfac.food.core.exception.FoodException;

import java.time.LocalDateTime;
import java.util.List;

public abstract class PedidoHelper {
    public static Pedido criarPedido() throws FoodException {
        return  new Pedido(
                1L,
                null,
                StatusPedido.AGUARDANDO_PAGAMENTO,
                List.of(ComboHelper.criarCombo()),
                LocalDateTime.now()
        );
    }

    public static Pedido criarPedidoComCliente() throws FoodException {
        return  new Pedido(
                1L,
                ClienteHelper.criarCliente().getId(),
                StatusPedido.AGUARDANDO_PAGAMENTO,
                List.of(ComboHelper.criarCombo()),
                LocalDateTime.now()
        );
    }
}
