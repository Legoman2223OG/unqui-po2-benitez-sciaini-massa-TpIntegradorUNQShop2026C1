package unqshop.envios;

public class EnvioEstandar implements MetodoEnvio {

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
        return 7;
    }
}
