package main.java.unqshop.reportes.datosDeEntrada;
//la Facade los usa para construir ReporteDeProductosMasVendidos (que calcula FilaProductoVendido)
//VALUE OBJECT
public class ProductoReporte {

    private String sku;
    private String nombre;

    public ProductoReporte(String sku, String nombre) {
        this.sku = sku;
        this.nombre = nombre;
    }

    //-----------------
    // getter & setter

    public String getSku() {
        return sku;
    }

    public String getNombre() {
        return nombre;
    }
    //-----------------
}
