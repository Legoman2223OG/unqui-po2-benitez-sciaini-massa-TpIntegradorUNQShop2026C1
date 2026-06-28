package main.java.unqshop.reportes.formatosDeSalida;

import main.java.unqshop.reportes.ReporteDeProductosMasVendidos;
import main.java.unqshop.reportes.ReporteVisitor;
import main.java.unqshop.reportes.estructurasDelReporte.FilaProductoVendido;

/*
 * Segun GoF == 'CONCRETE_VISITOR'
 * serializa el reporte en formato texto plano.
 * */
public class FormatoDeTexto implements ReporteVisitor {

    @Override
    public String visitarProductos(ReporteDeProductosMasVendidos reporte) {

        StringBuilder sb = new StringBuilder();

        sb.append("=== PRODUCTOS MAS VENDIDOS ===\n");
        sb.append("SKU | NOMBRE | CANTIDAD | PRECIO PROMEDIO\n");
        sb.append("-----------------------------------------\n");

        for (FilaProductoVendido fila : reporte.getFilas()) {
            sb.append(fila.getSku()).append(" | ")
                    .append(fila.getNombre()).append(" | ")
                    .append(fila.getCantidadVendida()).append(" | ")
                    .append(fila.getPrecioPromedioCobrado()).append("\n");
        }

        return sb.toString();
    }
}
