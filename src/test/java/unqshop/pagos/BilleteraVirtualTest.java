package unqshop.pagos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BilleteraVirtualTest {
    BilleteraVirtual billeteraAprobada;
    BilleteraVirtual billeteraDesAprobada;
    MobileUI mobileUIMock;

    @BeforeEach
    void setUp() {
        //Creo el objeto MOCK:
        mobileUIMock = mock(MobileUI.class);

        billeteraAprobada = new BilleteraVirtual(500, 5000, mobileUIMock);
        billeteraDesAprobada = new BilleteraVirtual(6700, 5000, mobileUIMock);

    }

    @Test
    void checkearSaldoSuficienteNoTiraExcepcionCuandoHaySuficienteSaldo() {

        assertDoesNotThrow(() -> billeteraAprobada.validarDatos());
    }

    @Test
    void checkearSaldoSuficienteSiTiraExcepcionCuandoHaySuficienteSaldo() {

        assertThrows(IllegalArgumentException.class, () -> billeteraDesAprobada.validarDatos());
    }

    @Test
    void reservarFondosDejaCorrectamenteSeteadoElAtrDeInstanciaSaldoDisponible() {
        billeteraAprobada.validarDatos();
        billeteraAprobada.reservarFondos();
        assertEquals(4500, billeteraAprobada.getSaldoDisponible());
    }

    @Test
    void reservarFondosDejaCorrectamenteSeteadoElAtrDeInstanciaSaldoReservado() {
        billeteraAprobada.validarDatos();
        billeteraAprobada.reservarFondos();
        assertEquals(500, billeteraAprobada.getSaldoReservado());
    }

    @Test
    void reservarFondosNoActuaCuandoNoSeValidaronLosDatos() {
        //billeteraDesAprobada.validarDatos();

        assertThrows(IllegalStateException.class, () -> billeteraDesAprobada.reservarFondos());

    }

    @Test
    void ejecutarTransaccionReiniciaSaldoReservadoACero() {
        billeteraAprobada.validarDatos();
        billeteraAprobada.reservarFondos();   // saldoReservado = 500
        billeteraAprobada.ejecutarTransaccion();
        assertEquals(0, billeteraAprobada.getSaldoReservado());
    }

    //--- Mockito Tests: --- //
    @Test
    void notificarResultadoInvocaCorrectamenteAlPushDeMobileUI() {
        billeteraAprobada.notificarResultado();

        //verify comprueba que sobre un mock
        //se llamó determinado metodo, con determinados argumentos.
        verify(mobileUIMock).push("Pago enviado: " + billeteraAprobada.getMonto() + " pesos enviados de tu cuenta al destinatario.");
    }

    @Test
    void procesarElPagoCompletoNotificaAlFinal() {
        billeteraAprobada.procesar_el_pago();

        verify(mobileUIMock, times(1)).push("Pago enviado: " + billeteraAprobada.getMonto() + " pesos enviados de tu cuenta al destinatario.");
    }

}