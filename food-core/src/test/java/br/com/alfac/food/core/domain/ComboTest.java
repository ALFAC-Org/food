package br.com.alfac.food.core.domain;

import br.com.alfac.food.core.domain.item.Item;
import br.com.alfac.food.core.domain.pedido.Combo;
import br.com.alfac.food.core.domain.pedido.Lanche;
import br.com.alfac.food.core.exception.FoodException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import utils.ComboHelper;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ComboTest {

    private Combo combo;

    @BeforeEach
    public void setUp() {
        combo = ComboHelper.criarCombo();
    }

    @Nested
    class getLanche {
        @Test
        public void deveRetornarLanche() {
            assertThat(combo.getLanche())
                    .isNotNull()
                    .isInstanceOf(Lanche.class);
        }
    }

    @Nested
    class setLanche {
        @Test
        public void deveAtribuirLanche() {
            Lanche lanche = new Lanche();
            combo.setLanche(lanche);
            assertThat(combo.getLanche())
                    .isNotNull()
                    .isSameAs(lanche);
        }
    }

    @Nested
    class getAcompanhamento {
        @Test
        public void deveRetornarAcompanhamento() {
            assertThat(combo.getAcompanhamento())
                    .isNotNull()
                    .isInstanceOf(Item.class);
        }
    }

    @Nested
    class setAcompanhamento {
        @Test
        public void deveAtribuirAcompanhamento() {
            Item acompanhamento = new Item();
            combo.setAcompanhamento(acompanhamento);
            assertThat(combo.getAcompanhamento())
                    .isNotNull()
                    .isSameAs(acompanhamento);
        }
    }

    @Nested
    class getBebida {
        @Test
        public void deveRetornarBebida() {
            assertThat(combo.getBebida())
                    .isNotNull()
                    .isInstanceOf(Item.class);
        }
    }

    @Nested
    class setBebida {
        @Test
        public void deveAtribuirBebida() {
            Item bebida = new Item();
            combo.setBebida(bebida);
            assertThat(combo.getBebida())
                    .isNotNull()
                    .isSameAs(bebida);
        }
    }

    @Nested
    class getSobremesa {
        @Test
        public void deveRetornarSobremesa() {
            assertThat(combo.getSobremesa())
                    .isNotNull()
                    .isInstanceOf(Item.class);
        }
    }

    @Nested
    class setSobremesa {
        @Test
        public void deveAtribuirSobremesa() {
            Item sobremesa = new Item();
            combo.setSobremesa(sobremesa);
            assertThat(combo.getSobremesa())
                    .isNotNull()
                    .isSameAs(sobremesa);
        }
    }

    @Nested
    class getTotal {
        @Test
        public void deveRetornarTotal() {
            combo.setTotal(BigDecimal.TEN);
            assertThat(combo.getTotal())
                    .isNotNull();
        }

        @Test
        public void deveRetornarTotalQuandoNulo() {
            combo.setTotal(null);
            assertThat(combo.getTotal())
                    .isNull();
        }
    }

    @Nested
    class setTotal {
        @Test
        public void deveAtribuirTotal() {
            combo.setTotal(BigDecimal.TEN);
            assertThat(combo.getTotal())
                    .isEqualTo("10.00");
        }
    }

    @Nested
    class calcularValorTotal {
        @Test
        public void deveCalcularValorTotal() {
            combo.calcularValorTotal();
            assertThat(combo.getTotal())
                    .isEqualTo("42.00");
        }
    }

    @Nested
    class getItens {
        @Test
        public void deveRetornarItens() {
            assertThat(combo.getItens())
                    .isNotNull()
                    .isNotEmpty();
        }
    }

    @Nested
    class validarItens {
        @Test
        public void deveNaoLancarErroSeTudoEstiverCerto() {
            combo.setLanche(null);
            combo.setAcompanhamento(null);
            combo.setBebida(null);
            combo.setSobremesa(null);
            combo.calcularValorTotal();
            assertThat(combo.getTotal())
                    .isEqualTo("0.00");
        }

        @Test
        public void deveLancarErroSeLancheEstiverNulo() {
            var combo = new Combo();

            assertThatThrownBy(() -> combo.validarItens())
                    .isInstanceOf(FoodException.class)
                    .hasMessage("Combo não pode ser vazio");
        }

        @Test
        public void deveLancarErroSeLancheTiverCategoriaInvalida() {
            var lanche = new Lanche();
            combo.setLanche(lanche);

            assertThatThrownBy(() -> combo.validarItens())
                    .isInstanceOf(FoodException.class)
                    .hasMessage("Categoria do item lanche está inválida");
        }

        @Test
        public void deveLancarErroSeAcompanhamentoTiverCategoriaInvalida() {
            var acompanhamento = new Item();
            combo.setAcompanhamento(acompanhamento);

            assertThatThrownBy(() -> combo.validarItens())
                    .isInstanceOf(FoodException.class)
                    .hasMessage("Categoria do item acompanhamento está inválida");
        }

        @Test
        public void deveLancarErroSeBebidaTiverCategoriaInvalida() {
            var bebida = new Item();
            combo.setBebida(bebida);

            assertThatThrownBy(() -> combo.validarItens())
                    .isInstanceOf(FoodException.class)
                    .hasMessage("Categoria do item bebida está inválida");
        }

        @Test
        public void deveLancarErroSeSobreMesaTiverCategoriaInvalida() {
            var sobremesa = new Item();
            combo.setSobremesa(sobremesa);

            assertThatThrownBy(() -> combo.validarItens())
                    .isInstanceOf(FoodException.class)
                    .hasMessage("Categoria do item sobremesa está inválida");
        }
    }
}
