package main.java.unqshop.pagos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/*
 * PagoFacade es una fachada bien simple: solo delega el pago al MetodoPago
 * que le pasan. Se mockea MetodoPago para no depender de ninguna
 * implementacion concreta (BilleteraVirtual, TarjetaCredito, etc.) y probar
 * unicamente que la delegacion ocurra.
 */
class PagoFacadeTest {

    PagoFacade facade;
    MetodoPago metodoPagoMock;

    @BeforeEach
    void setUp() {
        facade = new PagoFacade();
        metodoPagoMock = mock(MetodoPago.class);
    }

    // ---- pagarCon_: delega el procesamiento al metodo de pago ----

    @Test
    void pagarConDelegaElProcesamientoAlMetodoDePago() {
        facade.pagarCon_(metodoPagoMock);

        verify(metodoPagoMock).procesar_el_pago();
    }
}
