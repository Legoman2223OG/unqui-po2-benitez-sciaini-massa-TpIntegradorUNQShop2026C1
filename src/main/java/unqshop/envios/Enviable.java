package main.java.unqshop.envios;

public interface Enviable {
 ///cada MetodoEnvio recibe un Enviable y saca solo lo que necesita.
    double total();

    double pesoTotal();

    Direccion direccionEntrega();
}
