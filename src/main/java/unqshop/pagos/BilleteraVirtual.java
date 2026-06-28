package main.java.unqshop.pagos;

public class BilleteraVirtual extends MetodoPago {
    //var. ins.:
    private double saldoDisponible;
    private double saldoReservado;
    private MobileUI mobileUI;
    private boolean datosValidados = false;

    //Constructor SIN mock:
    public BilleteraVirtual(double monto, double saldoDisponible) {
        super(monto);

        this.saldoDisponible = saldoDisponible;
        this.mobileUI = new MobileUI(); //posible Adapter
    }

    //Constructor CON dependency injection de Mock (testeado con Mockito):
    public BilleteraVirtual(double monto, double saldoDisponible, MobileUI mobileUIM) {
        super(monto);
        this.saldoDisponible = saldoDisponible;
        this.mobileUI = mobileUIM;
    }


    @Override
    protected void validarDatos() {

        checkearSaldoSuficiente();
        this.datosValidados = true;

    }

    @Override
    protected void reservarFondos() {
        if (!datosValidados) {
            throw new IllegalStateException("Datos no validados Correctamente en el paso anterior.");
        }
        saldoDisponible -= getMonto();
        saldoReservado += getMonto();

    }

    @Override
    protected void ejecutarTransaccion() {

        enviarPago(this.getSaldoReservado());
        this.setSaldoReservado(0);
    }

    @Override
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

