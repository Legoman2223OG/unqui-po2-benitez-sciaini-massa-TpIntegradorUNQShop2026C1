package main.java.unqshop.envios;

public interface Pedido {

    double total();

    double pesoTotal();

    Direccion direccionEntrega();
}
