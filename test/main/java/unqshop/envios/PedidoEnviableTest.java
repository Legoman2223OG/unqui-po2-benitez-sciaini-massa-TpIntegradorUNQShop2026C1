package main.java.unqshop.envios;

import main.java.unqshop.catalogo.ItemCatalogo;
import main.java.unqshop.pagos.MetodoPago;
import main.java.unqshop.pedido.MailSender;
import main.java.unqshop.pedido.Pedido;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * PedidoEnviable es el adapter que traduce un Pedido real al contrato
 * Enviable que necesita el modulo de envios. Por eso el pedido NO se
 * mockea (es la clase real la que hay que probar), pero sus
 * dependencias (MetodoPago, MetodoEnvio, MailSender, Direccion) si se
 * mockean porque son de otros modulos y no nos interesa su logica interna.
 *
 * Los items se agregan con agregarItemPriv porque agregarItem depende
 * del estado (Contexto) del pedido, y no es lo que se quiere probar aca.
 */
class PedidoEnviableTest {

    Direccion direccionMock;
    MetodoPago metodoPagoMock;
    MetodoEnvio metodoEnvioMock;
    MailSender mailSenderMock;

    Pedido pedido;
    PedidoEnviable pedidoEnviable;

    @BeforeEach
    void setUp() {
        direccionMock = mock(Direccion.class);
        metodoPagoMock = mock(MetodoPago.class);
        metodoEnvioMock = mock(MetodoEnvio.class);
        mailSenderMock = mock(MailSender.class);

        pedido = new Pedido(10,"cliente@mail.com", metodoPagoMock, metodoEnvioMock, mailSenderMock, direccionMock);

        pedidoEnviable = new PedidoEnviable(pedido, direccionMock);
    }

    // ---- total: devuelve el precio de los items (no precioPedido, para evitar recursion) ----

    @Test
    void totalDevuelveLaSumaDelPrecioFinalDeLosItems() {
        ItemCatalogo item1 = mock(ItemCatalogo.class);
        ItemCatalogo item2 = mock(ItemCatalogo.class);
        when(item1.getPrecioFinal()).thenReturn(1000.0);
        when(item2.getPrecioFinal()).thenReturn(500.0);

        pedido.agregarItemPriv(item1); //fijarse si anda si se cambia por agregarItem, el cual es metodo publico 
        pedido.agregarItemPriv(item2);

        assertEquals(1500.0, pedidoEnviable.total());
    }

    @Test
    void totalEsCeroSiElPedidoNoTieneItems() {
        assertEquals(0.0, pedidoEnviable.total());
    }

    // ---- pesoTotal: suma el peso de cada item del pedido ----

    @Test
    void pesoTotalDevuelveLaSumaDelPesoDeLosItems() {
        ItemCatalogo item1 = mock(ItemCatalogo.class);
        ItemCatalogo item2 = mock(ItemCatalogo.class);
        when(item1.getPeso()).thenReturn(2.5);
        when(item2.getPeso()).thenReturn(3.5);

        pedido.agregarItemPriv(item1);//fijarse si anda si se cambia por agregarItem, el cual es metodo publico 
        pedido.agregarItemPriv(item2);

        assertEquals(6.0, pedidoEnviable.pesoTotal());
    }

    @Test
    void pesoTotalEsCeroSiElPedidoNoTieneItems() {
        assertEquals(0.0, pedidoEnviable.pesoTotal());
    }

    // ---- direccionEntrega: devuelve la direccion inyectada por afuera del pedido ----

    @Test
    void direccionEntregaDevuelveLaDireccionInyectadaPorConstructor() {
        assertEquals(direccionMock, pedidoEnviable.direccionEntrega());
    }
}
