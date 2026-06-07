package src.main.java.unqshop.pagos;

public class BilleteraVirtual extends MetodoPago {
    //var. ins.:
    private double saldoDisponible;
    private double saldoReservado;
    private MobileUI mobileUI;

    public BilleteraVirtual(double monto, double saldoDisponible, double saldoReservado) {
        super(monto);

        this.saldoDisponible = saldoDisponible;
        this.saldoReservado = saldoReservado;
        this.mobileUI = new MobileUI(); //posible Adapter

    }


    @Override
    protected void validarDatos() {

        checkearSaldoSuficiente();
    }

    @Override
    protected void reservarFondos() {

        saldoDisponible -= getMonto();
        saldoReservado += getMonto();

    }

    @Override
    protected void ejecutarTransaccion() {

        enviarPago(this.getSaldoReservado());
        this.setSaldoReservado(0);
    }

    protected void notificarResultado() {

        mobileUI.push("Pago enviado: " + this.getMonto() + " pesos enviados de tu cuenta al destinatario.");
    }

    //-----------------------
    //metodos_concretos:

    private void checkearSaldoSuficiente() {

        //si no hay salgo suficiente falla
        //to-do: revisar Excepción.
        if (saldoDisponible < this.getMonto()) {
            throw new IllegalArgumentException(
                    "No tenes suficiente saldo.\n Necesitas al menos " + this.getMonto() + " para pagar."
            );
        }

    }

    private void enviarPago(double monto) {
        System.out.println("Enviando pago de " + monto + " pesos, desde billetera virtual...");
        System.out.println("Éxito, Pago acreditado al vendedor. \n" + monto + "pesos descontados de tu cuenta.");
    }


    //----------------
    // GETTER & SETTER
    public double getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public double getSaldoReservado() {
        return saldoReservado;
    }

    public void setSaldoReservado(double saldoReservado) {
        this.saldoReservado = saldoReservado;
    }
    //----------------  --------------  --------------

}

