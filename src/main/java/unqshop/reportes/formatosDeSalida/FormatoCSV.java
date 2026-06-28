package main.java.unqshop.reportes.formatosDeSalida;

import main.java.unqshop.reportes.ReporteDeProductosMasVendidos;
import main.java.unqshop.reportes.ReporteVisitor;
import main.java.unqshop.reportes.estructurasDelReporte.FilaProductoVendido;

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
