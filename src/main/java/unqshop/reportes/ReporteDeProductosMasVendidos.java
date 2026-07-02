package main.java.unqshop.reportes;

import main.java.unqshop.reportes.datosDeEntrada.ItemReporte;
import main.java.unqshop.reportes.datosDeEntrada.PedidoReporte;
import main.java.unqshop.reportes.estructurasDelReporte.FilaProductoVendido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*libro gof:
* ConcreteElement (AssignmentNode,VariableRefNode)
- implements an Accept operation that takes a visitor as an argument
* */
/*
 * Segun GoF == 'CONCRETE_ELEMENT'
 * define un tipo concreto de reporte,
 * en este caso, el del producto mas vendido...
 * */

/// /Reporte CONCRETO, con logica y datos propios:
public class ReporteDeProductosMasVendidos implements Reporte {
    // encargado *solamente* de saber QUE datos recolectar. no de como formatearlos luego.

    private final List<FilaProductoVendido> filas;

    public ReporteDeProductosMasVendidos(List<PedidoReporte> pedidos, LocalDate fecha_desde, LocalDate fecha_hasta) {

        Map<String, Integer> cant_por_sku = new HashMap<>();
        Map<String, Double> monto_por_sku = new HashMap<>();
        Map<String, String> nombre_por_sku = new HashMap<>();

        for (PedidoReporte pedido : pedidos) {
            

            if (!pedido_entregado_en_periodo(pedido, fecha_desde, fecha_hasta)) {
                continue;
            }

            acumular_items_del_pedido(pedido, cant_por_sku, monto_por_sku, nombre_por_sku);
        }

        this.filas = armar_filas_ordenadas(cant_por_sku, monto_por_sku, nombre_por_sku);
    }

    @Override
    public String aceptar(ReporteVisitor visitor) {
        //---------------------------------------
        //DOUBLE DISPATCH 'CLAVE' para el patron:
        return visitor.visitarProductos(this);
        //---------------------------------------
    }

    public List<FilaProductoVendido> getFilas() {
        return filas;
    }

    //--------------------------------------
    // meth. concretos
    //--------------------------------------

    private boolean pedido_entregado_en_periodo(PedidoReporte pedido, LocalDate desde, LocalDate hasta) {

        if (pedido.getFechaEntrega() == null) {
            return false;
        }

        LocalDate fecha = pedido.getFechaEntrega();
        return !fecha.isBefore(desde) && !fecha.isAfter(hasta);
    }

    private void acumular_items_del_pedido(PedidoReporte pedido,
                                           Map<String, Integer> cant_por_sku,
                                           Map<String, Double> monto_por_sku,
                                           Map<String, String> nombre_por_sku) {

        for (ItemReporte item : pedido.getItems()) {

            String sku = item.getProducto().getSku();
            int cant = item.getCantidad();
            double monto_del_item = cant * item.getPrecioUnitarioCobrado();

            cant_por_sku.put(sku, cant_por_sku.getOrDefault(sku, 0) + cant);
            monto_por_sku.put(sku, monto_por_sku.getOrDefault(sku, 0.0) + monto_del_item);
            nombre_por_sku.put(sku, item.getProducto().getNombre());
        }
    }

    private List<FilaProductoVendido> armar_filas_ordenadas(Map<String, Integer> cant_por_sku,
                                                            Map<String, Double> monto_por_sku,
                                                            Map<String, String> nombre_por_sku) {

        List<FilaProductoVendido> lista = new ArrayList<>();

        for (String sku : cant_por_sku.keySet()) {

            int cantidad_total = cant_por_sku.get(sku);
            double promedio = monto_por_sku.get(sku) / cantidad_total;

            lista.add(new FilaProductoVendido(sku, nombre_por_sku.get(sku), cantidad_total, promedio));
        }

        lista.sort((a, b) -> Integer.compare(b.getCantidadVendida(), a.getCantidadVendida()));
        return lista;
    }
}
