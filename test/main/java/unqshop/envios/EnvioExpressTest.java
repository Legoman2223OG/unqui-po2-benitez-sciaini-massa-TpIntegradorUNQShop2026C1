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
    Pedido pedidoMock;

    EnvioExpress envioExpress;

    @BeforeEach
    void setUp() {
        //Creo el objeto MOCK:
        apiMock = mock(EnvioExpressAPI.class);
        pedidoMock = mock(Pedido.class);

        envioExpress = new EnvioExpress(apiMock);

        when(pedidoMock.total()).thenReturn(10000.0);
    }

    // ---- calcularCosto: delega a la API ----

    @Test
    void calcularCostoDelegaALaAPI() {
        when(apiMock.calcularCosto(10000.0)).thenReturn(3000.0);

        envioExpress.calcularCosto(pedidoMock);

        verify(apiMock).calcularCosto(10000.0);
    }

    @Test
    void calcularCostoDevuelveLoQueCalculaLaAPI() {
        when(apiMock.calcularCosto(10000.0)).thenReturn(3000.0);

        double costo = envioExpress.calcularCosto(pedidoMock);

        assertEquals(3000.0, costo);
    }

    // ---- calcularTiempoEnvio ----

    @Test
    void calcularTiempoEnvioSiempreEsUnDia() {
        int dias = envioExpress.calcularTiempoEnvio(pedidoMock);

        assertEquals(1, dias);
    }
}
