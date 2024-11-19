package utils;

import br.com.alfac.food.core.domain.pagamento.Pagamento;
import br.com.alfac.food.core.domain.pagamento.StatusPagamento;
import br.com.alfac.food.core.exception.FoodException;

import java.time.LocalDateTime;

abstract public class PagamentoHelper {

    public static Pagamento criarPagamento() throws FoodException {
        return new Pagamento(
                1L,
                StatusPagamento.PENDENTE,
                1L,
                LocalDateTime.now()
        );
    }

    public static Pagamento criarPagamento(Long id, StatusPagamento statusPagamento, Long pedidoId, LocalDateTime dataPagamento) throws FoodException {
        return new Pagamento(id, statusPagamento, pedidoId, dataPagamento);
    }
}
