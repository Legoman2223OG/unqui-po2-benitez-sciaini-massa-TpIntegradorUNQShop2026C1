package unqshop.envios;

public class RetiroEnSucursal implements MetodoEnvio {

    private Sucursal sucursal;

    public RetiroEnSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public double calcularCosto(Pedido pedido) {
        return 0;
    }

    @Override
    public int calcularTiempoEnvio(Pedido pedido) {
        return sucursal.tieneStock()
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
