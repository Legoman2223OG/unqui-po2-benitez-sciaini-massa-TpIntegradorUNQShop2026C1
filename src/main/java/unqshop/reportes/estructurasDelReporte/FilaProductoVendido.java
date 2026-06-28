package unqshop.reportes.estructurasDelReporte;

// una fila del reporte de productos mas vendidos.
public class FilaProductoVendido {

    private String sku;
    private String nombre;
    private int cantidadVendida;
    private double precioPromedioCobrado;

    public FilaProductoVendido(String sku, String nombre,
                               int cantidadVendida, double precioPromedioCobrado) {
        this.sku = sku;
        this.nombre = nombre;
        this.cantidadVendida = cantidadVendida;
        this.precioPromedioCobrado = precioPromedioCobrado;
    }

    //-----------------
    // getter & setter
    //-----------------

    public String getSku() {
        return sku;
    }

    public String getNombre() {
        return nombre;

    }

    public int getCantidadVendida() {
        return cantidadVendida;

    }

    public double getPrecioPromedioCobrado() {
        return precioPromedioCobrado;
        
    }
}
