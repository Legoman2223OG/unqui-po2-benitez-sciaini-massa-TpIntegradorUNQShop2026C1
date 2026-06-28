package unqshop.reportes.datosAuxiliaresPorAhora;

public class ItemReporte {

    private ProductoReporte producto;
    private int cantidad;
    private double precio_unitario_cobrado;

    public ItemReporte(ProductoReporte producto, int cantidad, double precio_unitario_cobrado) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio_unitario_cobrado = precio_unitario_cobrado;
    }

    public ProductoReporte getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitarioCobrado() {
        return precio_unitario_cobrado;
    }
}
