package br.com.alfac.food.core.domain.pedido;

import br.com.alfac.food.core.domain.cliente.Cliente;
import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.exception.pagamento.PagamentoErro;
import br.com.alfac.food.core.exception.pedido.PedidoErros;
import br.com.alfac.food.core.utils.CollectionsUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pedido {

    private Long id;
    private Cliente cliente;
    private StatusPedido status;
    private List<Combo> combos;
    private LocalDateTime dataCadastro;

    public Pedido(final Cliente cliente) {
        this.cliente = cliente;
        this.status = StatusPedido.AGUARDANDO_PAGAMENTO;
        this.dataCadastro = LocalDateTime.now();
        this.combos = new ArrayList<>();
    }

    public Pedido(final Long id, final Cliente cliente, final StatusPedido status, final List<Combo> combos, final LocalDateTime dataCadastro) throws FoodException {
        validarDados(id, status, dataCadastro);
        this.id = id;
        this.cliente = cliente;
        this.status = status;
        this.combos = combos;
        this.dataCadastro = dataCadastro;
    }

    private void validarDados(final Long id, final StatusPedido statusPedido, final LocalDateTime dataCadastro) throws FoodException {
        if (Objects.isNull(id)) {
            throw new FoodException(PedidoErros.ID_OBRIGATORIO);
        }

        if (Objects.isNull(statusPedido)) {
            throw new FoodException(PedidoErros.STATUS_OBRIGATORIO);
        }

        if (Objects.isNull(dataCadastro)) {
            throw new FoodException(PedidoErros.DATA_CADASTRO_OBRIGATORIO);
        }
    }

    public void atualizarStatus() throws FoodException {
        if (StatusPedido.CANCELADO.equals(this.status)) {
            throw new FoodException(PedidoErros.STATUS_PEDIDO_CANCELADO);
        }

        if (StatusPedido.FINALIZADO.equals(this.status)) {
            throw new FoodException(PedidoErros.STATUS_PEDIDO_JA_FINALIZADO);
        }

        this.status = this.status.getProximoStatus();
    }

    public void atualizarStatusRecebido() throws FoodException {
        if (StatusPedido.CANCELADO.equals(this.status)) {
            throw new FoodException(PedidoErros.STATUS_PEDIDO_CANCELADO);
        }

        if (!StatusPedido.AGUARDANDO_PAGAMENTO.equals(this.status)) {
            throw new FoodException(PagamentoErro.PAGAMENTO_JA_REALIZADO);
        }

        this.status = this.status.getProximoStatus();
    }

    public void cancelar() throws FoodException {
        if (!StatusPedido.AGUARDANDO_PAGAMENTO.equals(this.status)) {
            throw new FoodException(PedidoErros.STATUS_PEDIDO_JA_FINALIZADO);
        }

        this.status = StatusPedido.CANCELADO;
    }

    public void adicionaCombo(Combo combo) throws FoodException {
        if (Objects.isNull(combo)) {
            throw new FoodException(PedidoErros.PEDIDO_COMBO_NULL);
        }

        this.combos.add(combo);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Combo> getCombos() {
        return combos;
    }

    public Long getId() {
        return id;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public BigDecimal getValorTotal() {
        if (CollectionsUtils.naoVazio(combos)) {
            combos.forEach(Combo::calcularValorTotal);

            return combos.stream()
                    .map(Combo::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        return BigDecimal.ZERO;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}