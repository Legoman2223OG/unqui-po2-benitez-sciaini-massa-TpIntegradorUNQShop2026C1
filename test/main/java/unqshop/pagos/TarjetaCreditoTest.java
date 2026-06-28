package main.java.unqshop.pagos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/*
 * con uno de sus constructores TarjetaCredito delega la lógica de validación y transacción a un
 * Protocolo_tarjeta_credito inyectado.
 */
class TarjetaCreditoTest {

    Protocolo_tarjeta_credito bancoMock;

    // tarjeta con protocolo mockeado — aísla TarjetaCredito del Banco real
    TarjetaCredito tarjeta;

    // tarjetas con Banco real — para verificar validaciones con datos inválidos
    TarjetaCredito tarjetaNumeroInvalido;
    TarjetaCredito tarjetaCVVInvalido;
    TarjetaCredito tarjetaVencida;

    @BeforeEach
    void setUp() {
        bancoMock = mock(Protocolo_tarjeta_credito.class);

        tarjeta = new TarjetaCredito(1000, "1234567890", "123", 2027, bancoMock);

        tarjetaNumeroInvalido = new TarjetaCredito(1000, "123", "123", 2027);
        tarjetaCVVInvalido = new TarjetaCredito(1000, "1234567890", "12", 2027);
        tarjetaVencida = new TarjetaCredito(1000, "1234567890", "123", 2020);
    }

    // ---- validarDatos: delega al protocolo ----

    @Test
    void validarDatosDelegaAlProtocolo() {
        tarjeta.validarDatos();

        verify(bancoMock).validarNumeroDeTarjeta("1234567890");
        verify(bancoMock).validarCVV("123");
        verify(bancoMock).validarVencimiento(2027);
    }

    @Test
    void validarDatosLanzaExcepcionSiNumeroEsInvalido() {
        assertThrows(IllegalArgumentException.class, () -> tarjetaNumeroInvalido.validarDatos());
    }

    @Test
    void validarDatosLanzaExcepcionSiCVVEsInvalido() {
        assertThrows(IllegalArgumentException.class, () -> tarjetaCVVInvalido.validarDatos());
    }

    @Test
    void validarDatosLanzaExcepcionSiTarjetaEstaVencida() {
        assertThrows(IllegalArgumentException.class, () -> tarjetaVencida.validarDatos());
    }

    // ---- reservarFondos ----

    @Test
    void reservarFondosLanzaExcepcionSiDatosNoFueronValidados() {
        // TarjetaCredito lanza la excepción por el flag propio, antes de llegar al protocolo
        assertThrows(IllegalStateException.class, () -> tarjeta.reservarFondos());
    }

    @Test
    void reservarFondosDelegaPreAutorizacionSiDatosValidados() {
        tarjeta.validarDatos();
        tarjeta.reservarFondos();

        verify(bancoMock).preAutorizarBancoEmisor();
    }

    // ---- ejecutarTransaccion ----

    @Test
    void ejecutarTransaccionDelegaDebitoDiferido() {
        tarjeta.ejecutarTransaccion();

        verify(bancoMock).debitoDiferido();
    }

    // ---- notificarResultado ----

    @Test
    void notificarResultadoDelegaNotificacionAlProtocolo() {
        tarjeta.notificarResultado();

        verify(bancoMock).tarjetaCreditoNotificacion();
    }

    // ---- flujo completo ----

    @Test
    void procesarElPagoCompletoEjecutaTodosLosPasos() {
        assertDoesNotThrow(() -> tarjeta.procesar_el_pago());

        verify(bancoMock).validarNumeroDeTarjeta("1234567890");
        verify(bancoMock).validarCVV("123");
        verify(bancoMock).validarVencimiento(2027);
        verify(bancoMock).preAutorizarBancoEmisor();
        verify(bancoMock).debitoDiferido();
        verify(bancoMock).tarjetaCreditoNotificacion();
    }
}