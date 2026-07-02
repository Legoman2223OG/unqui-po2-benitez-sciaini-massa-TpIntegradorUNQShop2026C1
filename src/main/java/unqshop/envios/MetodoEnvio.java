package main.java.unqshop.envios;

//Abstract Strategy Pattern
//define el contrato de los algoritmos de envío
public interface MetodoEnvio {

    //calcula el costo de envío del pedido
    double calcularCosto(Enviable pedido);

    //calcula el tiempo de envío del pedido
    int calcularTiempoEnvio(Enviable pedido);

}
