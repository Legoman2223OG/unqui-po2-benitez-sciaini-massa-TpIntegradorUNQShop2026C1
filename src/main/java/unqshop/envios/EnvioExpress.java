package unqshop.envios;

public class EnvioExpress implements MetodoEnvio {

    @Override
    public double calcularCosto(Pedido pedido) {
        return EnvioExpressAPI.calcularCosto(
                pedido.total()
        );
    }

    @Override
    public int calcularTiempoEnvio(Pedido pedido) {
        return 1;
    }
}
