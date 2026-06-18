package unqshop.envios;

public interface MetodoEnvio {

    double calcularCosto(Pedido pedido);

    int calcularTiempoEnvio(Pedido pedido);
}
