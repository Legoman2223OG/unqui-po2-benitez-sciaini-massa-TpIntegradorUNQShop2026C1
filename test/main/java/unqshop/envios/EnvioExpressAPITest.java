package main.java.unqshop.envios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * EnvioExpressAPI es la clase real que EnvioExpress usa por detras.
 * En EnvioExpressTest se mockea para aislar EnvioExpress, aca se
 * prueba la formula real: precioPedido * 0.10 + 2000.
 * No tiene dependencias, asi que no hace falta mockear nada.
 */
class EnvioExpressAPITest {

    EnvioExpressAPI envioExpressAPI;

    @BeforeEach
    void setUp() {
        envioExpressAPI = new EnvioExpressAPI();
    }

    // ---- calcularCosto: precioPedido * 0.10 + 2000 ----

    @Test
    void calcularCostoCalculaLaFormulaCorrectamente() {
        double costo = envioExpressAPI.calcularCosto(10000.0);

        assertEquals(3000.0, costo);
    }

    @Test
    void calcularCostoConPrecioCeroDevuelveSoloElCargoFijo() {
        double costo = envioExpressAPI.calcularCosto(0.0);

        assertEquals(2000.0, costo);
    }

    @Test
    void calcularCostoFuncionaConValoresDecimales() {
        double costo = envioExpressAPI.calcularCosto(1250.5);

        assertEquals(2125.05, costo, 0.001);
    }
}
