package main.java.unqshop.envios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


// when/thenReturn setea el comportamiento del mock antes de que corra el código real.
//ejemplo generico:
//--------------------------------------------
// Creás el mock:
/// /Calculadora calcMock = mock(Calculadora.class);
// Seteás el comportamiento
/// /when(calcMock.sumar(2, 3)).thenReturn(5);
//Cuando alguien llame sumar(2, 3), el mock devuelve 5

/// /int resultado = calcMock.sumar(2, 3);  // → 5

//--------------------------------------------


/*
 * RetiroEnSucursal consulta a la Sucursal inyectada para saber
 * si hay stock y así calcular el tiempo de envío.
 */
class RetiroSucursalTest {

    Sucursal sucursalMock;
    Pedido pedidoMock;

    RetiroEnSucursal retiroEnSucursal;

    @BeforeEach
    void setUp() {
        //Creo el objeto MOCK:
        sucursalMock = mock(Sucursal.class);
        pedidoMock = mock(Pedido.class);

        retiroEnSucursal = new RetiroEnSucursal(sucursalMock);
    }

    // ---- calcularCosto ----

    @Test
    void calcularCostoSiempreEsCero() {
        double costo = retiroEnSucursal.calcularCosto(pedidoMock);

        assertEquals(0, costo);
    }

    // ---- calcularTiempoEnvio: depende del stock ----

    @Test
    void calcularTiempoEnvioEsCeroCuandoHayStock() {
        //"si te llaman con estos parámetros,     devolvé esto..."
        when(sucursalMock.tieneStock(pedidoMock)).thenReturn(true);

        int dias = retiroEnSucursal.calcularTiempoEnvio(pedidoMock);

        assertEquals(0, dias);
        verify(sucursalMock).tieneStock(pedidoMock);
    }

    @Test
    void calcularTiempoEnvioEsTresCuandoNoHayStock() {
        when(sucursalMock.tieneStock(pedidoMock)).thenReturn(false);

        int dias = retiroEnSucursal.calcularTiempoEnvio(pedidoMock);

        assertEquals(3, dias);
        verify(sucursalMock).tieneStock(pedidoMock);
    }
}
