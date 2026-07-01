package main.java.unqshop.pagos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Banco es la implementacion real de EnteBancario y Protocolo_tarjeta_credito.
 * En TarjetaCreditoTest y TransferenciaBancariaTest se mockea Banco para aislar
 * a esas clases, pero aca se prueba la lógica real de las validaciones.
 * Banco no tiene dependencias externas, asi que no hace falta mockear nada.
 */
class BancoTest {

    Banco banco;

    @BeforeEach
    void setUp() {
        banco = new Banco(1000);
    }

    // ---- validarCBU ----

    @Test
    void validarCBUNoTiraExcepcionConUnCBUValido() {
        assertDoesNotThrow(() -> banco.validarCBU("1234567890"));
    }

    @Test
    void validarCBUTiraExcepcionSiEstaVacio() {
        assertThrows(IllegalArgumentException.class, () -> banco.validarCBU(""));
    }

    @Test
    void validarCBUTiraExcepcionSiTieneMenosDeDiezDigitos() {
        assertThrows(IllegalArgumentException.class, () -> banco.validarCBU("123"));
    }

    // ---- validarAlias ----

    @Test
    void validarAliasNoTiraExcepcionSiEstaVacio() {
        //el alias no es obligatorio, si viene vacio no debe romper
        assertDoesNotThrow(() -> banco.validarAlias(""));
    }

    @Test
    void validarAliasNoTiraExcepcionConUnAliasValido() {
        assertDoesNotThrow(() -> banco.validarAlias("mi.alias.ok"));
    }

    @Test
    void validarAliasTiraExcepcionSiTieneMenosDeSeisCaracteres() {
        assertThrows(IllegalArgumentException.class, () -> banco.validarAlias("corto"));
    }

    // ---- realizarTransferencia ----

    @Test
    void realizarTransferenciaDevuelveElMensajeConLosDatosDeLaOperacion() {
        String mensaje = banco.realizarTransferencia(1000, "origen", "destino");

        assertEquals("Transferencia Bancaria con Monto $1000.0 enviada desdeorigenhaciadestino", mensaje);
    }

    // ---- notificaciones: no rompen el flujo ----

    @Test
    void emitirSeñalDeAprobacionNoTiraExcepcion() {
        assertDoesNotThrow(() -> banco.emitirSeñalDeAprobacion());
    }

    @Test
    void notificarResultadoNoTiraExcepcion() {
        assertDoesNotThrow(() -> banco.notificarResultado("12345678901"));
    }

    @Test
    void preAutorizarBancoEmisorNoTiraExcepcion() {
        assertDoesNotThrow(() -> banco.preAutorizarBancoEmisor());
    }

    @Test
    void debitoDiferidoNoTiraExcepcion() {
        assertDoesNotThrow(() -> banco.debitoDiferido());
    }

    @Test
    void tarjetaCreditoNotificacionNoTiraExcepcion() {
        assertDoesNotThrow(() -> banco.tarjetaCreditoNotificacion());
    }

    // ---- numeroOperacion / getters & setters ----

    @Test
    void getNumeroOperacionNuncaEsNulo() {
        assertNotNull(banco.getNumeroOperacion());
    }

    @Test
    void getMontoDevuelveElMontoDelConstructor() {
        assertEquals(1000, banco.getMonto());
    }

    @Test
    void setCBUYGetCBUFuncionanCorrectamente() {
        banco.setCBU("12345678901");

        assertEquals("12345678901", banco.getCBU());
    }

    @Test
    void getAliasEsNuloPorDefectoYaQueNoExisteUnSetter() {
        //el alias nunca se asigna: no hay setAlias() ni se setea en el constructor
        assertNull(banco.getAlias());
    }

    // ---- validarVencimiento ----

    @Test
    void validarVencimientoNoTiraExcepcionSiElAñoEsElActual() {
        int añoActual = java.time.Year.now().getValue();

        assertDoesNotThrow(() -> banco.validarVencimiento(añoActual));
    }

    @Test
    void validarVencimientoNoTiraExcepcionSiElAñoEsFuturo() {
        int añoFuturo = java.time.Year.now().getValue() + 1;

        assertDoesNotThrow(() -> banco.validarVencimiento(añoFuturo));
    }

    @Test
    void validarVencimientoTiraExcepcionSiLaTarjetaEstaVencida() {
        int añoPasado = java.time.Year.now().getValue() - 1;

        assertThrows(IllegalArgumentException.class, () -> banco.validarVencimiento(añoPasado));
    }

    // ---- validarCVV ----

    @Test
    void validarCVVNoTiraExcepcionConUnCVVDeTresDigitos() {
        assertDoesNotThrow(() -> banco.validarCVV("123"));
    }

    @Test
    void validarCVVTiraExcepcionSiEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> banco.validarCVV(null));
    }

    @Test
    void validarCVVTiraExcepcionSiNoTieneTresDigitos() {
        assertThrows(IllegalArgumentException.class, () -> banco.validarCVV("12"));
    }

    // ---- validarNumeroDeTarjeta ----

    @Test
    void validarNumeroDeTarjetaNoTiraExcepcionConUnNumeroValido() {
        assertDoesNotThrow(() -> banco.validarNumeroDeTarjeta("1234567890"));
    }

    @Test
    void validarNumeroDeTarjetaTiraExcepcionSiEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> banco.validarNumeroDeTarjeta(null));
    }

    @Test
    void validarNumeroDeTarjetaTiraExcepcionSiNoTieneDiezDigitos() {
        assertThrows(IllegalArgumentException.class, () -> banco.validarNumeroDeTarjeta("123"));
    }
}
