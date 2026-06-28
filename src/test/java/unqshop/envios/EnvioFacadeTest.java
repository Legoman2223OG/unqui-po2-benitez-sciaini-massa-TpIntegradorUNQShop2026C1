package unqshop.envios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;

class EnvioFacadeTest {

    EnvioFacade facade;
    Sucursal sucursalMock;

    @BeforeEach
    void setUp() {
        facade = new EnvioFacade();
        sucursalMock = mock(Sucursal.class);
    }

    @Test
    void envioEstandarDevuelveUnEnvioEstandar() {
        MetodoEnvio metodo = facade.envioEstandar();

        assertInstanceOf(EnvioEstandar.class, metodo);
    }

    @Test
    void envioExpressDevuelveUnEnvioExpress() {
        MetodoEnvio metodo = facade.envioExpress();

        assertInstanceOf(EnvioExpress.class, metodo);
    }

    @Test
    void retiroEnSucursalDevuelveUnRetiroEnSucursal() {
        MetodoEnvio metodo = facade.retiroEnSucursal(sucursalMock);

        assertInstanceOf(RetiroEnSucursal.class, metodo);
    }
}
