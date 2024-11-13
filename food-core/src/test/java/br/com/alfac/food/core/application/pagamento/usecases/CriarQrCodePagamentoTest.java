package br.com.alfac.food.core.application.pagamento.usecases;

import br.com.alfac.food.core.application.pagamento.adapters.gateways.PagamentoClientGateway;
import br.com.alfac.food.core.exception.FoodException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CriarQrCodePagamentoTest {

    @Mock
    private PagamentoClientGateway pagamentoClientGateway;

    @InjectMocks
    private CriarQrCodePagamento criarQrCodePagamento;

    @Test
    public void deveCriarQrCodePagamento() throws FoodException {
//        Arrange
        when(pagamentoClientGateway.gerarQrCode(1L))
                .thenReturn("QrCode");
//        Act
        var qrCode = criarQrCodePagamento.executar(1L);

//        Assert
        assert qrCode.equals("QrCode");
    }
}
