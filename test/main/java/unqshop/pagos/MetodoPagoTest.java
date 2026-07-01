package main.java.unqshop.pagos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * MetodoPago es abstracta y todas sus subclases (BilleteraVirtual, TarjetaCredito,
 * TransferenciaBancaria) sobreescriben notificarResultado(). Para poder testear el
 * HOOK con su comportamiento por defecto, y el setter de monto propios de esta clase,
 * se arma una subclase mínima que no sobreescribe nada más que lo obligatorio.
 */
class MetodoPagoTest {

    MetodoPago metodoPago;

    @BeforeEach
    void setUp() {
        metodoPago = new MetodoPagoConcreto(1000);
    }

    // ---- notificarResultado: HOOK con comportamiento por defecto ----

    @Test
    void notificarResultadoNoTiraExcepcionUsandoElComportamientoPorDefecto() {
        assertDoesNotThrow(() -> metodoPago.notificarResultado());
    }

    // ---- setMonto ----

    @Test
    void setMontoDejaCorrectamenteSeteadoElAtrDeInstanciaMonto() {
        metodoPago.setMonto(2500);

        assertEquals(2500, metodoPago.getMonto());
    }

    //subclase concreta minima, solo para poder instanciar MetodoPago en el test
    private static class MetodoPagoConcreto extends MetodoPago {

        MetodoPagoConcreto(double monto) {
            super(monto);
        }

        @Override
        protected void validarDatos() {
        }

        @Override
        protected void reservarFondos() {
        }

        @Override
        protected void ejecutarTransaccion() {
        }
    }
}
