package main.java.unqshop.envios;

//Concrete Strategy
//implementa el algoritmo de envío estándar, sobreescribiendo los métodos de la interfaz MetodoEnvio(abstract strategy).
public class EnvioEstandar implements MetodoEnvio {
   
    //Inyección de dependencias.
    private CorreoArgentina correoArgentina;

    public EnvioEstandar(CorreoArgentina correoArgentina) {

        this.correoArgentina = correoArgentina;
    }

    @Override
    public double calcularCosto(Pedido pedido) {

        return correoArgentina.estimarEnvio(
                pedido.pesoTotal(),
                pedido.direccionEntrega());
    }

    @Override
    public int calcularTiempoEnvio(Pedido pedido) {
        //por conveniencia, se asume que el tiempo de envío es de 7 días.
        return 7;
    }
}
