package unqshop.reportes;

import main.java.unqshop.Pedido.Pedido;
import main.java.unqshop.catalogo.ItemCatalogo;
import unqshop.reportes.datosAuxiliaresPorAhora.ItemReporte;
import unqshop.reportes.datosAuxiliaresPorAhora.PedidoReporte;
import unqshop.reportes.datosAuxiliaresPorAhora.ProductoReporte;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


/*
 *  * REPORTES FACADE — punto de entrada del módulo
 *
 * Esta clase es la que usan los demás módulos para pedir un reporte.
 * Funciona como adaptador entre el Pedido real (del módulo de mi compañero)
 * y la lógica interna de reportes.
 *
 * El flujo es así:
 *  1. Recibe una lista de Pedidos reales + rango de fechas + formato de salida
 *  2. Convierte cada Pedido a un PedidoReporte (DTO interno)
 *     → usa getFechaEntrega() y getItems() del Pedido real
 *  3. De cada ítem saca el SKU, nombre y precio con getSku/getNombre/getPrecioFinal
 *     (requiere que el módulo Pedido use catalogo.ItemCatalogo en su lista de ítems)
 *  4. Le pasa all eso a ReporteDeProductosMasVendidos que hace el cálculo
 *  5. Devuelve el String ya formateado según el visitor que se pasó
 *
 * Nota sobre cantidad=1 por ítem:
 *  El Pedido de mi compañero guarda una entrada por unidad en la lista,
 *  o sea si compraste 3 auriculares hay 3 objetos en la lista.
 *  Por eso pongo cantidad 1 por ítem y el reporte los acumula solo.
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

/// porque el Visitor resuelve el problema de los formatos, pero quien llama al módulo todavía tendría que saber construir PedidoReporte, ItemReporte, ProductoReporte
///     y pasárselos al constructor de ReporteDeProductosMasVendidos.
/// La Facade oculta eso detrás de un solo method con los parámetros que cualquiera entiende:
///     dame la lista de pedidos, el rango de fechas y el formato, y te devuelvo el String

public class ReportesFacade {

    public String productosMasVendidos(List<Pedido> pedidos, LocalDate desde, LocalDate hasta, ReporteVisitor formato) {

        List<PedidoReporte> pedidosDTO = pedidos.stream()
                .map(this::adaptarPedido)
                .collect(Collectors.toList());

        Reporte reporte = new ReporteDeProductosMasVendidos(pedidosDTO, desde, hasta);
        return reporte.aceptar(formato);
    }

    private PedidoReporte adaptarPedido(Pedido pedido) {
        List<ItemReporte> items = pedido.getItems().stream()
                .map(this::adaptarItem)
                .collect(Collectors.toList());
        return new PedidoReporte(pedido.getFechaEntrega(), items);
    }

    private ItemReporte adaptarItem(ItemCatalogo item) {
        ProductoReporte producto = new ProductoReporte(item.getSku(), item.getNombre());
        return new ItemReporte(producto, 1, item.getPrecioFinal());
    }
}