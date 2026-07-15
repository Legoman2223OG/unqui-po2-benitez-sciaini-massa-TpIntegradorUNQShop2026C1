package main.java.unqshop.reportes;

import main.java.unqshop.catalogo.ItemCatalogo;
import main.java.unqshop.envios.Direccion;
import main.java.unqshop.envios.MetodoEnvio;
import main.java.unqshop.pagos.MetodoPago;
import main.java.unqshop.pedido.MailSender;
import main.java.unqshop.pedido.Pedido;
import main.java.unqshop.reportes.formatosDeSalida.FormatoCSV;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * ReportesFacade es el punto de entrada del modulo: convierte un Pedido real
 * (con sus ItemCatalogo) a los DTOs internos del reporte (PedidoReporte,
 * ItemReporte, ProductoReporte) y arma el reporte final.
 *
 * Por eso el Pedido NO se mockea (es la conversion lo que hay que probar),
 * pero sus dependencias (MetodoPago, MetodoEnvio, MailSender, Direccion)
 * si se mockean porque son de otros modulos y no hace falta su logica real.
 */
class ReportesFacadeTest {

    ReportesFacade facade;

    MetodoPago metodoPagoMock;
    MetodoEnvio metodoEnvioMock;
    MailSender mailSenderMock;
    Direccion direccionMock;

    LocalDate desde;
    LocalDate hasta;

    @BeforeEach
    void setUp() {
        facade = new ReportesFacade();

        metodoPagoMock = mock(MetodoPago.class);
        metodoEnvioMock = mock(MetodoEnvio.class);
        mailSenderMock = mock(MailSender.class);
        direccionMock = mock(Direccion.class);

        desde = LocalDate.of(2025, 1, 1);
        hasta = LocalDate.of(2025, 1, 31);
    }

    private Pedido crearPedidoConItem(LocalDate fechaEntrega, String sku, String nombre, double precioFinal) {
        Pedido pedido = new Pedido(10,"cliente@mail.com", metodoPagoMock, metodoEnvioMock, mailSenderMock, direccionMock);
        pedido.setFechaEntrega(fechaEntrega);

        ItemCatalogo item = mock(ItemCatalogo.class);
        when(item.getSku()).thenReturn(sku);
        when(item.getNombre()).thenReturn(nombre);
        when(item.getPrecioFinal()).thenReturn(precioFinal);

        pedido.agregarItem(item);

        return pedido;
    }

    // ---- productosMasVendidos: arma el reporte a partir de Pedidos reales ----

    @Test
    void productosMasVendidosIncluyeLosItemsDeUnPedidoEntregadoEnElPeriodo() {
        Pedido pedido = crearPedidoConItem(LocalDate.of(2025, 1, 15), "auris", "Auriculares", 8000.0);

        String salida = facade.productosMasVendidos(List.of(pedido), desde, hasta, new FormatoCSV());

        assertTrue(salida.contains("auris,Auriculares"));
    }

    @Test
    void productosMasVendidosIgnoraPedidosSinFechaDeEntrega() {
        Pedido pedido = crearPedidoConItem(null, "auris", "Auriculares", 8000.0);

        String salida = facade.productosMasVendidos(List.of(pedido), desde, hasta, new FormatoCSV());

        assertFalse(salida.contains("auris"));
    }

    @Test
    void productosMasVendidosAcumulaCadaItemDelPedidoComoUnaUnidadVendida() {
        // el Pedido real guarda una entrada por unidad comprada,
        // por eso cada ItemCatalogo en la lista cuenta como cantidad = 1
        Pedido pedido = crearPedidoConItem(LocalDate.of(2025, 1, 15), "auris", "Auriculares", 8000.0);

        ItemCatalogo segundoAuricular = mock(ItemCatalogo.class);
        when(segundoAuricular.getSku()).thenReturn("auris");
        when(segundoAuricular.getNombre()).thenReturn("Auriculares");
        when(segundoAuricular.getPrecioFinal()).thenReturn(8000.0);
        pedido.agregarItem(segundoAuricular); 

        String salida = facade.productosMasVendidos(List.of(pedido), desde, hasta, new FormatoCSV());

        // 2 unidades vendidas (1 por cada ItemCatalogo agregado al pedido)
        assertTrue(salida.contains("auris,Auriculares,2,8000.0"));
    }
}
