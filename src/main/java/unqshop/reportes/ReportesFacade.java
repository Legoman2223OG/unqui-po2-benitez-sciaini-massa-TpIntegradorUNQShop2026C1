package main.java.unqshop.reportes;

import main.java.unqshop.pedido.Pedido;
import main.java.unqshop.reportes.datosDeEntrada.PedidoReporte;
import main.java.unqshop.reportes.datosDeEntrada.PedidoReporteAdapter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// #############
// #  FACADE    #
// #############
/*
 * REPORTES FACADE — punto de entrada del módulo
 *
 * Esta clase es la que usan los demás módulos para pedir un reporte.
 * Orquesta la conversión (delegada en PedidoReporteAdapter, en
 * datosDeEntrada) y el armado del reporte con el formato elegido.
 * No conoce Pedido/ItemCatalogo más allá de pasárselos al adapter.
 *
 * El flujo es así:
 *  1. Recibe una lista de Pedidos reales + rango de fechas + formato de salida
 *  2. Le pide al PedidoReporteAdapter que convierta cada Pedido a PedidoReporte
 *  3. Le pasa eso a ReporteDeProductosMasVendidos que hace el cálculo
 *  4. Devuelve el String ya formateado según el visitor que se pasó
 *
 *  COMO USARLA — tres pasos:
 *
 *   // 1. crear la facade
 *   ReportesFacade facade = new ReportesFacade();
 *
 *   // 2. elegir el formato de salida
 *   ReporteVisitor formato = new FormatoCSV();    // opciones: FormatoCSV, FormatoHTML, FormatoDeTexto
 *
 *   // 3. generar el reporte
 *   String reporte = facade.productosMasVendidos(
 *          listaDePedidos,
 *          LocalDate.of(2025, 1, 1),
 *          LocalDate.of(2025, 1, 31),
 *          formato
 *      );
 *
 *   System.out.println(reporte); // o lo guardas donde quieras
 *
 * QUE DEVUELVE:
 *   Un String con los productos más vendidos en ese período, ordenados de mayor
 *   a menor cantidad vendida, en el formato que elegiste.
 *
 * QUE NECESITA LA LISTA DE PEDIDOS:
 *   - Solo se cuentan los pedidos que tienen fecha de entrega dentro del rango.
 *   - Si un pedido no tiene fecha de entrega todavía, se ignora solo.
 *   - Los ítems de cada pedido tienen que ser Producto o Paquete del módulo Catálogo.
 */

//punto por donde pasa todo el acoplamiento externo:
public class ReportesFacade {

    private final PedidoReporteAdapter adapter = new PedidoReporteAdapter();

    public String productosMasVendidos(List<Pedido> pedidos, LocalDate desde, LocalDate hasta, ReporteVisitor formato) {

        List<PedidoReporte> pedidosDTO = pedidos.stream()
                .map(adapter::adaptar)
                .collect(Collectors.toList());

        Reporte reporte = new ReporteDeProductosMasVendidos(pedidosDTO, desde, hasta);
        //'1ER' Dispatch:
        //(el segundo ocurre en visitor.visitarProductos(this); @ReporteVisitor)
        return reporte.aceptar(formato);
    }
}