package main.java.unqshop.envios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/*
 * EnvioEstandar delega el cálculo del costo a un CorreoArgentina inyectado.
 * Con el constructor que recibe la dependencia se puede mockear CorreoArgentina
 * y aislar EnvioEstandar de la implementación real.
 */
class EnvioEstandarTest {

    CorreoArgentina correoMock;
    Enviable enviableMock;
    Direccion direccionMock;

    EnvioEstandar envioEstandar;

    @BeforeEach
    void setUp() {

        correoMock = mock(CorreoArgentina.class);
        enviableMock = mock(Enviable.class);
        direccionMock = mock(Direccion.class);

        envioEstandar = new EnvioEstandar(correoMock);

        //lleno de contestaciones al mock:
        when(enviableMock.pesoTotal()).thenReturn(5.0);
        when(enviableMock.direccionEntrega()).thenReturn(direccionMock);
    }

    // ---- calcularCosto: delega al correo ----
    @Test
    void calcularCostoDelegaAlCorreo() {
        envioEstandar.calcularCosto(enviableMock);

        verify(correoMock).estimarEnvio(5.0, direccionMock);
    }

    @Test
    void calcularCostoDevuelveLoQueEstimaElCorreo() {
        when(correoMock.estimarEnvio(5.0, direccionMock)).thenReturn(25.0);

        double costo = envioEstandar.calcularCosto(enviableMock);

        assertEquals(25.0, costo);
    }

    // ---- calcularTiempoEnvio ----

    @Test
    void calcularTiempoEnvioSiempreEsSieteDias() {
        int dias = envioEstandar.calcularTiempoEnvio(enviableMock);

        assertEquals(7, dias);
    }
}
