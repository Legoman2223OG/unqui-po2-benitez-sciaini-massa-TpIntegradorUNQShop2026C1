package main.java.unqshop.pagos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/*
 * TransferenciaBancaria delega la validación y la señal de aprobación a un Banco inyectado cuando se lo invoca con new.
 * Con uno de sus constructores se puede inyectar un mock de Banco para aislar
 * TransferenciaBancaria de la implementación real.
 */
class TransferenciaBancariaTest {

    Banco bancoMock;

    // transferencia con banco mockeado — aísla TransferenciaBancaria del Banco real
    TransferenciaBancaria transferencia;

    // transferencias con Banco real — para verificar validaciones con datos inválidos
    TransferenciaBancaria transferenciaCBUInvalido;
    TransferenciaBancaria transferenciaCBUVacio;
    TransferenciaBancaria transferenciaAliasInvalido;

    @BeforeEach
    void setUp() {
        //Creo el objeto MOCK:
        bancoMock = mock(Banco.class);

        transferencia = new TransferenciaBancaria(1000, "12345678901", "mi.alias.ok", bancoMock);

        // CBU de menos de 10 caracteres — debe fallar
        transferenciaCBUInvalido = new TransferenciaBancaria(1000, "123", "mi.alias.ok");

        // CBU en blanco — debe fallar
        transferenciaCBUVacio = new TransferenciaBancaria(1000, "", "mi.alias.ok");

        // alias demasiado corto (menos de 6 caracteres) — debe fallar
        transferenciaAliasInvalido = new TransferenciaBancaria(1000, "12345678901", "corto");
    }

    // ---- validarDatos: delega al banco ----

    @Test
    void validarDatosDelegaAlBanco() {
        transferencia.validarDatos();

        verify(bancoMock).validarCBU("12345678901");
        verify(bancoMock).validarAlias("mi.alias.ok");
    }

    @Test
    void validarDatosLanzaExcepcionSiCBUEsDemasiadoCorto() {
        assertThrows(IllegalArgumentException.class, () -> transferenciaCBUInvalido.validarDatos());
    }

    @Test
    void validarDatosLanzaExcepcionSiCBUEstaVacio() {
        assertThrows(IllegalArgumentException.class, () -> transferenciaCBUVacio.validarDatos());
    }

    @Test
    void validarDatosLanzaExcepcionSiAliasEsDemasiadoCorto() {
        assertThrows(IllegalArgumentException.class, () -> transferenciaAliasInvalido.validarDatos());
    }

    // ---- reservarFondos ----

    @Test
    void reservarFondosLanzaExcepcionSiDatosNoFueronValidados() {
        assertThrows(IllegalStateException.class, () -> transferencia.reservarFondos());
    }

    @Test
    void reservarFondosDelegaSeñalDeAprobacionAlBanco() {
        // a diferencia de otros medios de pago, acá no hay fondos que bloquear:
        // la "reserva" es simplemente la señal de aprobación de la entidad bancaria.
        transferencia.validarDatos();
        transferencia.reservarFondos();

        verify(bancoMock).emitirSeñalDeAprobacion();
    }

    // ---- ejecutarTransaccion ----

    @Test
    void ejecutarTransaccionNoLanzaExcepcion() {
        // no hay delegación al banco ni estado:
        // verificmos que el paso no rompe el flujo.
        assertDoesNotThrow(() -> transferencia.ejecutarTransaccion());
    }

    // ---- notificarResultado ----

    @Test
    void notificarResultadoDelegaAlBanco() {
        transferencia.notificarResultado();
        verify(bancoMock).notificarResultado("12345678901");
    }

    // ---- realizarTransferencia: metodo propio, siempre vacio ----

    @Test
    void realizarTransferenciaSiempreDevuelveNull() {
        assertEquals("", transferencia.realizarTransferencia(1000, "origen", "destino"));
    }

    // ---- constructor con solo el monto: no inicializa el banco ----

    @Test
    void constructorConSoloMontoNoInicializaElBanco() {
        TransferenciaBancaria transferenciaSimple = new TransferenciaBancaria(1000);

        assertNull(transferenciaSimple.getBanco());
        assertEquals(1000, transferenciaSimple.getMonto());
    }

    // ---- flujo completo ----

    @Test
    void procesarElPagoCompletoEjecutaTodosLosPasos() {
        assertDoesNotThrow(() -> transferencia.procesar_el_pago());

        verify(bancoMock).validarCBU("12345678901");
        verify(bancoMock).validarAlias("mi.alias.ok");
        verify(bancoMock).emitirSeñalDeAprobacion();
        verify(bancoMock).notificarResultado("12345678901");
    }


}