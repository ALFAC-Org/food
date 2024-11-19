package br.com.alfac.food.core.application.pedido.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.math.RoundingMode;

import br.com.alfac.food.core.domain.pedido.StatusPedido;

public class PedidoDTO {

    private List<ComboDTO> combos;
    private Long clienteId;
    private Long id;
    private StatusPedido statusPedido;
    private BigDecimal valorTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ComboDTO> getCombos() {
        return combos;
    }

    public void setCombos(List<ComboDTO> combos) {
        this.combos = combos;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public BigDecimal getValorTotal() {

        if(Objects.nonNull(valorTotal)) {
            return valorTotal.setScale(2, RoundingMode.HALF_UP);
        }

        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}