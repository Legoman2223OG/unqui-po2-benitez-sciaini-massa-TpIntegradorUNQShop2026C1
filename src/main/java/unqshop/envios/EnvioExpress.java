package main.java.unqshop.envios;

//Concrete Strategy
//implementa el algoritmo de envío express, sobreescribiendo los métodos de la interfaz MetodoEnvio(abstract strategy).
public class EnvioExpress implements MetodoEnvio {

    //Inyección de dependencias.
    private EnvioExpressAPI envioExpressAPI;

    public EnvioExpress(EnvioExpressAPI envioExpressAPI) {
        this.envioExpressAPI = envioExpressAPI;
    }

    @Override
    public double calcularCosto(Enviable pedido) {
        return this.getEnvioExpressAPI().calcularCosto(
                pedido.total()
        );
    }

    @Override
    public int calcularTiempoEnvio(Enviable pedido) {
        return 1;
    }
    public EnvioExpressAPI getEnvioExpressAPI() {
        return envioExpressAPI;
    }
}
