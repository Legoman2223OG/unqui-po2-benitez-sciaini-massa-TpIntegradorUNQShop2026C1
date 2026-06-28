package unqshop.pagos;


public class TarjetaCredito extends MetodoPago {

    private final String numeroTarjeta;
    private final String cvv;
    private final int fechaVencimiento; // SOLO AÑO!
    private boolean datosValidados; //FLAG
    private Protocolo_tarjeta_credito banco;

    public TarjetaCredito(double monto, String numeroTarjeta, String cvv, int fechaVencimiento) {
        super(monto);
        this.numeroTarjeta = numeroTarjeta;
        this.cvv = cvv;
        this.fechaVencimiento = fechaVencimiento;
        this.banco = new Banco(this.getMonto());
    }

    public TarjetaCredito(double monto, String numeroTarjeta, String cvv, int fechaVencimiento, Protocolo_tarjeta_credito banco) {
        super(monto);
        this.numeroTarjeta = numeroTarjeta;
        this.cvv = cvv;
        this.fechaVencimiento = fechaVencimiento;
        this.banco = banco;
    }

    //Metodos concretos
    //Sobreescriben el comportamiento abstracto de la superClase
    @Override
    protected void validarDatos() {


        validarNumeroDeTarjeta();
        validarCVV();
        validarVencimiento();
        this.setDatosValidados(true); // si tdo pasa: FLAG ==TRUE
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
        this.getBanco().tarjetaCreditoNotificacion();

    }


    //  - - - - - - - - - - - - - - - - - - - - -
    //  - - - - - - - - - - - - - - - - - - - - -
    //  - - - - - - - - - - - - - - - - - - - - -

    //METODOS PARTICULARES DE LA CLASE:
    //llama a protocolo_tarjeta_credito inyectado
    // validar_datos:
    private void validarVencimiento() {
        this.getBanco().validarVencimiento(this.getFechaVencimiento());
    }


    private void validarCVV() {
        this.getBanco().validarCVV(this.getCvv());
    }

    private void validarNumeroDeTarjeta() {
        this.getBanco().validarNumeroDeTarjeta(this.getNumeroTarjeta());
    }

    // reservarFondos:
    private void preAutorizarBancoEmisor() {
        this.getBanco().preAutorizarBancoEmisor();

    }

    //ejecutarTransaccion:
    private void debitoDiferido() {
        this.getBanco().debitoDiferido();
    }


    public void setDatosValidados(boolean datosValidados) {
        this.datosValidados = datosValidados;
    }

    //  - - - - - - - - - - - - - - - - - - - - -
    //  - - - - - - - - - - - - - - - - - - - - -
    //  - - - - - - - - - - - - - - - - - - - - -

    // G&S:

    public Protocolo_tarjeta_credito getBanco() {
        return banco;
    }

    public boolean isDatosValidados() {
        return datosValidados;
    }

    public int getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getCvv() {
        return cvv;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }
}