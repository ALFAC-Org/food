package br.com.alfac.food.infra.cliente.persistence;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import br.com.alfac.food.infra.pedido.persistence.PedidoEntity;
import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "cliente")
public class ClienteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nome do cliente é obrigatório")
    private String nome;

    @NotEmpty(message = "CPF do cliente é obrigatório")
    private String cpf;

    @NotEmpty(message = "Email do cliente é obrigatório")
    private String email;

    private UUID uuid;

    @OneToMany(mappedBy = "cliente")
    private Set<PedidoEntity> pedidos;

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }
}