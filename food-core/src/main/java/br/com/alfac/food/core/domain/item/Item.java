package br.com.alfac.food.core.domain.item;

import java.math.BigDecimal;
import java.util.Objects;
import java.math.RoundingMode;

public class Item {
    private String nome;
    private BigDecimal preco;
    private CategoriaItem categoria;
    private Long id;

    public Item() {
    }

    public Item(Long id, BigDecimal preco) {
        this.id = id;
        this.preco = preco;
    }

    public Item(String nome, BigDecimal preco, CategoriaItem categoria, Long id) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.id = id;
    }

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

    public BigDecimal getPreco() {
        if (Objects.nonNull(preco)) {
            return preco.setScale(2, RoundingMode.HALF_UP);
        }
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public CategoriaItem getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaItem categoria) {
        this.categoria = categoria;
    }
}