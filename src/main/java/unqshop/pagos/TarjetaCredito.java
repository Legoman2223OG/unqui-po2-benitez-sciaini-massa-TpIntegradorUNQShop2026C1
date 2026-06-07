package src.main.java.unqshop.pagos;



public class TarjetaCredito extends MetodoPago {

    public TarjetaCredito(double monto) {
        super(monto);
    }

    //Metodos concretos
    //Sobreescriben el comportamiento abstracto de la superClase
    @Override
    protected void validarDatos() {

        validarNumeroDeTarjeta();
        validarCVV();
        validarVencimiento();

    }


    @Override
    protected void reservarFondos() {
        preAutorizarBancoEmisor();
    }



    @Override
    protected void ejecutarTransaccion() {

        debitoDiferido();

    }




    @Override
    protected void notificarResultado() {
        System.out.println(
                "Cupón de pago generado."
        );
    }

    // ------------------------------------------
    // ------------------------------------------
    // ------------------------------------------

    //METODOS PARTICULARES DE LA CLASE:
    // validar_datos:
    private void validarVencimiento() {
    }

    private void validarCVV() {
    }

    private void validarNumeroDeTarjeta() {
    }

    // reservarFondos:
    private void preAutorizarBancoEmisor() {

    }

    //ejecutarTransaccion
    private void debitoDiferido() {
    }


    // ------------------------------------------
    // ------------------------------------------
    // ------------------------------------------


}
