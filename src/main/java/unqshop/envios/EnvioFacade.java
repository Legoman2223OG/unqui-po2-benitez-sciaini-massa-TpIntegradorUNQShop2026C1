package unqshop.envios;

public class EnvioFacade {

    public MetodoEnvio envioEstandar() {
        return new EnvioEstandar(
                new CorreoArgentina());
    }

    public MetodoEnvio envioExpress() {
        return new EnvioExpress();
    }

    public MetodoEnvio retiroEnSucursal(
            Sucursal sucursal) {

        return new RetiroEnSucursal(
                sucursal);
    }
}