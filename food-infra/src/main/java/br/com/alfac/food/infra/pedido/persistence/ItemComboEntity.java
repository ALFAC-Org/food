package br.com.alfac.food.infra.pedido.persistence;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "item_combo")
public class ItemComboEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_item")
    @NotNull(message = "itemId é obrigatório")
    private Long itemId;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_item_combo")
    private List<ItemComboComplementoEntity> complementos;

    @NotNull(message = "Preço do item é obrigatório")
    private Double preco;

    private String observacoes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long item) {
        this.itemId = item;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public List<ItemComboComplementoEntity> getComplementos() {
        return complementos;
    }

    public void setComplementos(List<ItemComboComplementoEntity> complementos) {
        this.complementos = complementos;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }    
    
}