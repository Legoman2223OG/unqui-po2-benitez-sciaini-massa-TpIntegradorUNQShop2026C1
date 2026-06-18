package unqshop.envios;

public class CorreoArgentina implements CalculadoraDeCorreo {

    @Override
    public double estimarEnvio(double peso, Direccion direccion) {
        return peso * direccion.distancia() * 0.5;
    }
}
