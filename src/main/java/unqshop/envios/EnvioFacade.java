package main.java.unqshop.envios;
/*
* Esta fachada queda obsoleta luego de la correction del 03/07/2026 @17.00hs
* no se debe aplicar en la APP.
* */
public class EnvioFacade { 
 
    public MetodoEnvio envioEstandar() {
        return new EnvioEstandar(
                new CorreoArgentina());
    }

    public MetodoEnvio envioExpress() {
        return new EnvioExpress(new EnvioExpressAPI());
    }

    public MetodoEnvio retiroEnSucursal(
            Sucursal sucursal) {

        return new RetiroEnSucursal(
                sucursal);
    }
}