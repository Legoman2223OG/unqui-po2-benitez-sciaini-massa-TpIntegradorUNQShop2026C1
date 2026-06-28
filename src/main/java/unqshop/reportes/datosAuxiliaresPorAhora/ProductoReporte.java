package main.java.unqshop.reportes.datosAuxiliaresPorAhora;

// producto o paquete tal como lo necesita reportes (hasta que integremos catalogo).
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
