package main.java.unqshop.envios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * EnvioExpress delega el cálculo del costo a un EnvioExpressAPI inyectado.
 */
class EnvioExpressTest {

    EnvioExpressAPI apiMock;
    Enviable enviableMock;

    EnvioExpress envioExpress;

    @BeforeEach
    void setUp() {
        //Creo el objeto MOCK:
        apiMock = mock(EnvioExpressAPI.class);
        enviableMock = mock(Enviable.class);

        envioExpress = new EnvioExpress(apiMock);

        when(enviableMock.total()).thenReturn(10000.0);
    }

    // ---- calcularCosto: delega a la API ----

    @Test
    void calcularCostoDelegaALaAPI() {
        when(apiMock.calcularCosto(10000.0)).thenReturn(3000.0);

        envioExpress.calcularCosto(enviableMock);

        verify(apiMock).calcularCosto(10000.0);
    }

    @Test
    void calcularCostoDevuelveLoQueCalculaLaAPI() {
        when(apiMock.calcularCosto(10000.0)).thenReturn(3000.0);

        double costo = envioExpress.calcularCosto(enviableMock);

        assertEquals(3000.0, costo);
    }

    // ---- calcularTiempoEnvio ----

    @Test
    void calcularTiempoEnvioSiempreEsUnDia() {
        int dias = envioExpress.calcularTiempoEnvio(enviableMock);

        assertEquals(1, dias);
    }
}
