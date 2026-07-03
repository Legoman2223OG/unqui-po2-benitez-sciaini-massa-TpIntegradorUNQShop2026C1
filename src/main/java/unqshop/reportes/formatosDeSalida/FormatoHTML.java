package main.java.unqshop.reportes.formatosDeSalida;

import main.java.unqshop.reportes.ReporteDeProductosMasVendidos;
import main.java.unqshop.reportes.ReporteVisitor;
import main.java.unqshop.reportes.estructurasDelReporte.FilaProductoVendido;

/*
 * ################################
 * Segun GoF == 'CONCRETE_VISITOR'
 * ################################
 *
 *
 * serializa el reporte en formato HTML.
 */
public class FormatoHTML implements ReporteVisitor {

    @Override
    public String visitarProductos(ReporteDeProductosMasVendidos reporte) {

        StringBuilder sb = new StringBuilder();

        sb.append("<table>\n");
        sb.append("  <thead>\n");
        sb.append("    <tr><th>SKU</th><th>NOMBRE</th><th>CANTIDAD</th><th>PRECIO PROMEDIO</th></tr>\n");
        sb.append("  </thead>\n");
        sb.append("  <tbody>\n");

        for (FilaProductoVendido fila : reporte.getFilas()) {
            sb.append("    <tr>")
                    .append("<td>").append(fila.getSku()).append("</td>")
                    .append("<td>").append(fila.getNombre()).append("</td>")
                    .append("<td>").append(fila.getCantidadVendida()).append("</td>")
                    .append("<td>").append(fila.getPrecioPromedioCobrado()).append("</td>")
                    .append("</tr>\n");
        }

        sb.append("  </tbody>\n");
        sb.append("</table>");

        return sb.toString();
    }
}
