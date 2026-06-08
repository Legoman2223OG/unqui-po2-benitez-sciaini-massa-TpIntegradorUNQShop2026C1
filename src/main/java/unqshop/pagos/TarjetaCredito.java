package unqshop.pagos;


public class TarjetaCredito extends MetodoPago {

    private final String numeroTarjeta;
    private final String cvv;
    private final int fechaVencimiento; // SOLO AÑO!
    private boolean datosValidados; //FLAG

    public TarjetaCredito(double monto, String numeroTarjeta, String cvv, int fechaVencimiento) {
        super(monto);
        this.numeroTarjeta = numeroTarjeta;
        this.cvv = cvv;
        this.fechaVencimiento = fechaVencimiento;
    }

    //Metodos concretos
    //Sobreescriben el comportamiento abstracto de la superClase
    @Override
    protected void validarDatos() {

        validarNumeroDeTarjeta();
        validarCVV();
        validarVencimiento();
        this.setDatosValidados(true); // si todo pasa: FLAG ==TRUE
    }


    @Override
    protected void reservarFondos() {
        //mismo caso q billeteraVirtual.
        if (!datosValidados) throw new IllegalStateException();
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
    //--------------------
    private void validarVencimiento() {
        //CONSEGUIR EL TIMPO ACTUAL
        int aÑoActual = java.time.Year.now().getValue();

        if (this.fechaVencimiento < aÑoActual) {
            throw new IllegalArgumentException("Tarjeta vencida.");
        }

    }


    private void validarCVV() {
        if (cvv == null || cvv.length() != 3) {
            throw new IllegalArgumentException("CVV inválido.");
        }
    }

    private void validarNumeroDeTarjeta() {
        if (numeroTarjeta == null || numeroTarjeta.length() != 10) {
            throw new IllegalArgumentException("Número de tarjeta inválido.");
        }
    }

    //-------------------------
    // reservarFondos:
    private void preAutorizarBancoEmisor() {
        System.out.println("Pre-Autorizando $" + this.getMonto() + "En el banco Emisor.");
    }

    //ejecutarTransaccion:
    private void debitoDiferido() {
        System.out.println("Débito diferido con monto $" + getMonto());
    }

    public void setDatosValidados(boolean datosValidados) {
        this.datosValidados = datosValidados;
    }

    // ------------------------------------------
    // ------------------------------------------
    // ------------------------------------------


}
