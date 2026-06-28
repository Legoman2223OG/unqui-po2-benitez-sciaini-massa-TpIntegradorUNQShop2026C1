package unqshop.reportes.formatosDeSalida;

import unqshop.reportes.ReporteDeProductosMasVendidos;
import unqshop.reportes.ReporteVisitor;
import unqshop.reportes.estructurasDelReporte.FilaProductoVendido;

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
