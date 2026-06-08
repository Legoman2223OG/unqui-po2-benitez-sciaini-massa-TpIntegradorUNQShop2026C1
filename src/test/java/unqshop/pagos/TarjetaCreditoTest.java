package unqshop.pagos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TarjetaCreditoTest {

    TarjetaCredito tarjetaValida;
    TarjetaCredito tarjetaNumeroInvalido;
    TarjetaCredito tarjetaCVVInvalido;
    TarjetaCredito tarjetaVencida;

    @BeforeEach
    void setUp() {
        tarjetaValida = new TarjetaCredito(1000, "1234567890", "123", 2027);
        tarjetaNumeroInvalido = new TarjetaCredito(1000, "123", "123", 2027);
        tarjetaCVVInvalido = new TarjetaCredito(1000, "1234567890", "12", 2027);
        tarjetaVencida = new TarjetaCredito(1000, "1234567890", "123", 2020);
    }

    // ---- validarDatos ----
    //assertDoesNotThrow() -> () METH.
    //correcto
    @Test
    void validarDatosNoLanzaExcepcionConDatosCorrectos() {
        assertDoesNotThrow(() -> tarjetaValida.validarDatos());
    }

    //incorrecto
    @Test
    void validarDatosLanzaExcepcionCuandoNumeroEsInvalido() {
        assertThrows(IllegalArgumentException.class, () -> tarjetaNumeroInvalido.validarDatos());
    }

    @Test
    void validarDatosLanzaExcepcionCuandoCVVEsInvalido() {
        assertThrows(IllegalArgumentException.class, () -> tarjetaCVVInvalido.validarDatos());
    }

    @Test
    void validarDatosLanzaExcepcionCuandoTarjetaEstaVencida() {
        assertThrows(IllegalArgumentException.class, () -> tarjetaVencida.validarDatos());
    }

    // ---- reservarFondos ----

    @Test
    void reservarFondosLanzaExcepcionCuandoNoSeValidaronLosDatos() {
        assertThrows(IllegalStateException.class, () -> tarjetaValida.reservarFondos());
    }

    @Test
    void reservarFondosNoLanzaExcepcionSiSeValidaronLosDatos() {
        tarjetaValida.validarDatos();
        assertDoesNotThrow(() -> tarjetaValida.reservarFondos());
    }
 
    // ---- ejecutarTransaccion ----

    @Test
    void ejecutarTransaccionNoLanzaExcepcion() {
        tarjetaValida.validarDatos();
        tarjetaValida.reservarFondos();
        assertDoesNotThrow(() -> tarjetaValida.ejecutarTransaccion());
    }

    // ---- flujo completo ----

    @Test
    void procesarElPagoCompletoNoLanzaExcepcion() {
        assertDoesNotThrow(() -> tarjetaValida.procesar_el_pago());
    }
}