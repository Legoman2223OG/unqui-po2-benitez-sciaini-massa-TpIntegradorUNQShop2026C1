package main.java.unqshop.reportes.datosDeEntrada;

import main.java.unqshop.catalogo.ItemCatalogo;
import main.java.unqshop.pedido.Pedido;

import java.util.List;
import java.util.stream.Collectors;

// ###########
// # ADAPTER
// ###########
/*
 * Adapta la interfaz de Pedido/ItemCatalogo (módulo de mi compañero) a la
 * interfaz que el subsistema de reportes espera: PedidoReporte/ItemReporte/
 * ProductoReporte. De acá para adentro, el módulo de reportes no sabe que
 * existen Pedido ni ItemCatalogo.
 * reestructura Pedido, el único lugar que hay que tocar es PedidoReporteAdapter.
 *  ReporteDeProductosMasVendidos ni se entera
 *Adapter en GoF : {
 * "convert the interface of a class
 *  into another interface client(ReporteDeProductosMasVendidos) expect(la interfaz PedidoReporte/ItemReporte, no la de Pedido/ItemCatalogo.)'
 *  }
 * // Solo el Adapter 'paga el costo' de conocer el mundo externo.
 * Nota sobre cantidad=1 por ítem:
 *  El Pedido de mi compañero guarda una entrada por unidad en la lista,
 *  o sea si compraste 3 auriculares hay 3 objetos en la lista.
 *  Por eso pongo cantidad 1 por ítem y el reporte los acumula solo.
 */

public class PedidoReporteAdapter {

    public PedidoReporte adaptar(Pedido pedido) {
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