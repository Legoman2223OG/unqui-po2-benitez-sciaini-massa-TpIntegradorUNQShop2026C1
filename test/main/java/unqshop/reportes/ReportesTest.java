package main.java.unqshop.reportes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.unqshop.reportes.datosAuxiliaresPorAhora.ItemReporte;
import main.java.unqshop.reportes.datosAuxiliaresPorAhora.PedidoReporte;
import main.java.unqshop.reportes.datosAuxiliaresPorAhora.ProductoReporte;
import main.java.unqshop.reportes.formatosDeSalida.FormatoCSV;
import main.java.unqshop.reportes.formatosDeSalida.FormatoDeTexto;
import main.java.unqshop.reportes.formatosDeSalida.FormatoHTML;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Tests del modulo de reportes.
 * No se necesitan mocks porque PedidoReporte, ItemReporte y ProductoReporte
 * son objetos simples (sin dependencias externas).
 * */
class ReportesTest {

    // formatos de salida (ConcreteVisitors)
    FormatoDeTexto texto;
    FormatoCSV csv;
    FormatoHTML html;

    // datos de prueba
    ProductoReporte auricular;
    ProductoReporte cable;

    LocalDate desde;
    LocalDate hasta;
    LocalDate fechaDentro;
    LocalDate fechaFuera;

    PedidoReporte pedidoDentro;
    PedidoReporte pedidoFuera;
    PedidoReporte pedidoSinFecha;

    @BeforeEach
    void setUp() {

        texto = new FormatoDeTexto();
        csv = new FormatoCSV();
        html = new FormatoHTML();

        auricular = new ProductoReporte("auris", "Auriculares");
        cable = new ProductoReporte("cable", "Cable USB-C");

        desde = LocalDate.of(2025, 1, 1);
        hasta = LocalDate.of(2025, 1, 31);
        fechaDentro = LocalDate.of(2025, 1, 15);
        fechaFuera = LocalDate.of(2025, 2, 15);

        // 2 auriculares a $8000 + 3 cables a $800, entregado dentro del periodo:
        pedidoDentro = new PedidoReporte(fechaDentro, List.of(
                new ItemReporte(auricular, 2, 8000.0),
                new ItemReporte(cable, 3, 800.0)
        ));

        // mismo contenido pero entregado fuera del periodo:
        pedidoFuera = new PedidoReporte(fechaFuera, List.of(
                new ItemReporte(auricular, 1, 8000.0)
        ));

        // pedido que todavia no fue entregado (sin fecha):
        pedidoSinFecha = new PedidoReporte(null, List.of(
                new ItemReporte(auricular, 1, 8000.0)
        ));
    }


    // ---- logica del reporte ----

    @Test
    void soloIncluye_pedidosEntregadosDentroDelPeriodo() {
        ReporteDeProductosMasVendidos reporte = new ReporteDeProductosMasVendidos(
                List.of(pedidoDentro, pedidoFuera, pedidoSinFecha), desde, hasta);

        // solo pedidoDentro aporta datos: 2 auriculares + 3 cables
        assertEquals(2, reporte.getFilas().size());
    }

    @Test
    void ignoraPedidosFueraDelPeriodo() {
        ReporteDeProductosMasVendidos reporte = new ReporteDeProductosMasVendidos(
                List.of(pedidoFuera), desde, hasta);

        assertTrue(reporte.getFilas().isEmpty());
    }

    @Test
    void ignoraPedidosSinFechaDeEntrega() {
        ReporteDeProductosMasVendidos reporte = new ReporteDeProductosMasVendidos(
                List.of(pedidoSinFecha), desde, hasta);

        assertTrue(reporte.getFilas().isEmpty());
    }

    @Test
    void ordenaDesMayorAMenorPorCantidadVendida() {
        ReporteDeProductosMasVendidos reporte = new ReporteDeProductosMasVendidos(
                List.of(pedidoDentro), desde, hasta);

        // cable tiene 3 unidades > auricular con 2 → cable va primero
        assertEquals("cable", reporte.getFilas().get(0).getSku());
        assertEquals("auris", reporte.getFilas().get(1).getSku());
    }

    @Test
    void calculaCorrectamenteElPrecioPromedioCobrado() {
        ReporteDeProductosMasVendidos reporte = new ReporteDeProductosMasVendidos(
                List.of(pedidoDentro), desde, hasta);

        // auricular: 2 unidades * $8000 = $16000 / 2 = $8000 de promedio
        double promedio = reporte.getFilas().get(1).getPrecioPromedioCobrado();
        assertEquals(8000.0, promedio);
    }

    @Test
    void acumulaCantidadesDeVariosPedidos() {
        PedidoReporte otrosDentro = new PedidoReporte(fechaDentro, List.of(
                new ItemReporte(auricular, 5, 8000.0)
        ));

        ReporteDeProductosMasVendidos reporte = new ReporteDeProductosMasVendidos(
                List.of(pedidoDentro, otrosDentro), desde, hasta);

        // auricular: 2 + 5 = 7 unidades en total
        int cant_auricular = reporte.getFilas().stream()
                .filter(f -> f.getSku().equals("auris"))
                .findFirst().get()
                .getCantidadVendida();

        assertEquals(7, cant_auricular);
    }


    // ---- formato texto ----

    @Test
    void textoContieneEncabezado() {
        ReporteDeProductosMasVendidos reporte = new ReporteDeProductosMasVendidos(
                List.of(pedidoDentro), desde, hasta);

        String salida = reporte.aceptar(texto);

        assertTrue(salida.contains("PRODUCTOS MAS VENDIDOS"));
    }

    @Test
    void textoContieneDatosDelProducto() {
        ReporteDeProductosMasVendidos reporte = new ReporteDeProductosMasVendidos(
                List.of(pedidoDentro), desde, hasta);

        String salida = reporte.aceptar(texto);

        assertTrue(salida.contains("auris"));
        assertTrue(salida.contains("Auriculares"));
    }

    @Test
    void textoEstaVacioCuandoNoHayVentasEnElPeriodo() {
        ReporteDeProductosMasVendidos reporte = new ReporteDeProductosMasVendidos(
                List.of(pedidoFuera), desde, hasta);

        String salida = reporte.aceptar(texto);

        // solo encabezado, sin filas de datos
        assertFalse(salida.contains("auris"));
    }


    // ---- formato CSV ----

    @Test
    void csvContieneEncabezadoConComas() {
        ReporteDeProductosMasVendidos reporte = new ReporteDeProductosMasVendidos(
                List.of(pedidoDentro), desde, hasta);

        String salida = reporte.aceptar(csv);

        assertTrue(salida.contains("SKU,NOMBRE,CANTIDAD,PRECIO_PROMEDIO"));
    }

    @Test
    void csvContieneFilaDelProductoSeparadaPorComas() {
        ReporteDeProductosMasVendidos reporte = new ReporteDeProductosMasVendidos(
                List.of(pedidoDentro), desde, hasta);

        String salida = reporte.aceptar(csv);

        assertTrue(salida.contains("auris,Auriculares"));
    }


    // ---- formato HTML ----

    @Test
    void htmlContieneEtiquetasDeTabla() {
        ReporteDeProductosMasVendidos reporte = new ReporteDeProductosMasVendidos(
                List.of(pedidoDentro), desde, hasta);

        String salida = reporte.aceptar(html);

        assertTrue(salida.contains("<table>"));
        assertTrue(salida.contains("</table>"));
        assertTrue(salida.contains("<th>"));
        assertTrue(salida.contains("<td>"));
    }

    @Test
    void htmlContieneIdDelProductoEntreTags() {
        ReporteDeProductosMasVendidos reporte = new ReporteDeProductosMasVendidos(
                List.of(pedidoDentro), desde, hasta);

        String salida = reporte.aceptar(html);

        assertTrue(salida.contains("<td>auris</td>"));
    }
}
