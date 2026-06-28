package unqshop.envios;

public class EnvioExpressAPI {

    private static final double porcentaje = 0.10;
    private static final double cargoXBase = 2000;

    public double calcularCosto(double precioPedido) {

        return precioPedido * porcentaje + cargoXBase;
    }
}