package main.java.unqshop.envios;

//Concrete Strategy
//implementa el algoritmo de retiro en sucursal, sobreescribiendo los métodos de la interfaz MetodoEnvio(abstract strategy).
public class RetiroEnSucursal implements MetodoEnvio {

    private Sucursal sucursal;

    public RetiroEnSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public double calcularCosto(Enviable pedido) {
        return 0;
    }

    @Override
    public int calcularTiempoEnvio(Enviable pedido) {
        return sucursal.tieneStock(pedido)
                ? 0
                : 3;
        //*
        // equivalente a:
        // if (sucursal.tieneStock()) {
        //    return 0;
        //} else {
        //    return 3;
        //}
        // */
    }

}
