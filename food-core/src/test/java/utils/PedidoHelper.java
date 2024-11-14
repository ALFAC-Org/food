package utils;

import br.com.alfac.food.core.domain.cliente.Cliente;
import br.com.alfac.food.core.domain.pedido.Combo;
import br.com.alfac.food.core.domain.pedido.Pedido;
import br.com.alfac.food.core.domain.pedido.StatusPedido;
import br.com.alfac.food.core.exception.FoodException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class PedidoHelper {

    public static Pedido criarPedido(
            Long id,
            Cliente cliente,
            StatusPedido status,
            List<Combo> combo,
            LocalDateTime localDateTime
    ) throws FoodException {
        return new Pedido(
                id,
                cliente,
                status,
                combo,
                localDateTime
        );
    }

    public static Pedido criarPedido() throws FoodException {
        return new Pedido(
                1L,
                null,
                StatusPedido.AGUARDANDO_PAGAMENTO,
                List.of(ComboHelper.criarCombo()),
                LocalDateTime.now()
        );
    }

    public static Pedido criarPedido(StatusPedido status) throws FoodException {
        return new Pedido(
                1L,
                null,
                status,
                List.of(ComboHelper.criarCombo()),
                LocalDateTime.now()
        );
    }

    public static Pedido criarPedidoRecebido() throws FoodException {
        return new Pedido(
                1L,
                null,
                StatusPedido.RECEBIDO,
                List.of(ComboHelper.criarCombo()),
                LocalDateTime.now()
        );
    }

    public static Pedido criarPedidoCancelado() throws FoodException {
        return new Pedido(
                1L,
                null,
                StatusPedido.CANCELADO,
                List.of(ComboHelper.criarCombo()),
                LocalDateTime.now()
        );
    }

    public static Pedido criarPedidoFinalizado() throws FoodException {
        return new Pedido(
                1L,
                null,
                StatusPedido.FINALIZADO,
                List.of(ComboHelper.criarCombo()),
                LocalDateTime.now()
        );
    }

    public static Pedido criarPedidoComCliente() throws FoodException {
        return new Pedido(
                1L,
                ClienteHelper.criarCliente(),
                StatusPedido.AGUARDANDO_PAGAMENTO,
                List.of(ComboHelper.criarCombo()),
                LocalDateTime.now()
        );
    }

    public static List<Pedido> criarPedidos(Integer quantidade) throws FoodException {
        List<Pedido> pedidos = new ArrayList<>();

        for (int i = 0; i < quantidade; i++) {
            pedidos.add(criarPedido());
        }

        return pedidos;
    }
}
