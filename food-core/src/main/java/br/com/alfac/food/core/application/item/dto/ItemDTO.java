package br.com.alfac.food.core.application.item.dto;

import br.com.alfac.food.core.domain.item.CategoriaItem;


public class ItemDTO {
    private Long id;
    private String nome;
    private Double preco;
    private CategoriaItem categoria;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public CategoriaItem getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaItem categoria) {
        this.categoria = categoria;
    }
}