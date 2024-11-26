package br.com.alfac.food.infra.pedido.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Table(name = "item_combo_complemento")
public class ItemComboComplementoEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_item")
    @NotNull(message = "itemId é obrigatório")
    private Long itemId;

    @NotNull(message = "Preço do item é obrigatório")
    private Double preco;

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
    
}