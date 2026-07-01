package main.java.unqshop.envios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * CorreoArgentina es la implementacion real de CalculadoraDeCorreo.
 * A diferencia de EnvioEstandarTest (que mockea CorreoArgentina para
 * aislar EnvioEstandar), aca se prueba la formula real: peso * distancia * 0.5.
 * Direccion se mockea porque es una dependencia externa, pero CorreoArgentina
 * es la clase bajo prueba, no se mockea.
 */
class CorreoArgentinaTest {

    Direccion direccionMock;

    CorreoArgentina correoArgentina;

    @BeforeEach
    void setUp() {
        direccionMock = mock(Direccion.class);

        correoArgentina = new CorreoArgentina();
    }

    // ---- estimarEnvio: peso * distancia * 0.5 ----

    @Test
    void estimarEnvioCalculaLaFormulaCorrectamente() {
        when(direccionMock.distancia()).thenReturn(10.0);

        double costo = correoArgentina.estimarEnvio(4.0, direccionMock);

        assertEquals(20.0, costo);
    }

    @Test
    void estimarEnvioEsCeroSiElPesoEsCero() {
        when(direccionMock.distancia()).thenReturn(50.0);

        double costo = correoArgentina.estimarEnvio(0.0, direccionMock);

        assertEquals(0.0, costo);
    }

    @Test
    void estimarEnvioEsCeroSiLaDistanciaEsCero() {
        when(direccionMock.distancia()).thenReturn(0.0);

        double costo = correoArgentina.estimarEnvio(8.0, direccionMock);

        assertEquals(0.0, costo);
    }

    @Test
    void estimarEnvioFuncionaConValoresDecimales() {
        when(direccionMock.distancia()).thenReturn(2.5);

        double costo = correoArgentina.estimarEnvio(3.0, direccionMock);

        assertEquals(3.75, costo);
    }
}
