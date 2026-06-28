package unqshop.reportes.formatosDeSalida;

import unqshop.reportes.ReporteDeProductosMasVendidos;
import unqshop.reportes.ReporteVisitor;
import unqshop.reportes.estructurasDelReporte.FilaProductoVendido;

/*
 * Segun GoF == 'CONCRETE_VISITOR'
 * serializa el reporte en formato CSV.
 * */
public class FormatoCSV implements ReporteVisitor {

    @Override
    public String visitarProductos(ReporteDeProductosMasVendidos reporte) {

        StringBuilder sb = new StringBuilder();

        sb.append("SKU,NOMBRE,CANTIDAD,PRECIO_PROMEDIO\n");

        for (FilaProductoVendido fila : reporte.getFilas()) {
            sb.append(fila.getSku()).append(",")
              .append(fila.getNombre()).append(",")
              .append(fila.getCantidadVendida()).append(",")
              .append(fila.getPrecioPromedioCobrado()).append("\n");
        }

        return sb.toString();
    }
}
