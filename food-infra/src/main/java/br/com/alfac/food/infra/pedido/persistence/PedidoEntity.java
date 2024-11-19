package br.com.alfac.food.infra.pedido.persistence;

import br.com.alfac.food.core.domain.pedido.StatusPedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
public class PedidoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "id_cliente")
    private Long clienteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido")
    @NotNull(message = "Status do pedido é obrigatório")
    private StatusPedido status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_pedido")
    @NotEmpty(message = "Obrigatório 1 ou mais combos")
    private List<ComboEntity> combos;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long cliente) {
        this.clienteId = cliente;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public List<ComboEntity> getCombos() {
        return combos;
    }

    public void setCombos(List<ComboEntity> combos) {
        this.combos = combos;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}