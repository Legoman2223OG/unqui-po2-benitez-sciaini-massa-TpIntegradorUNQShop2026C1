package unqshop.pagos;

public class
TransferenciaBancaria extends MetodoPago {

    public TransferenciaBancaria(double monto) {
        super(monto);
    }

    @Override
    protected void validarDatos() {
        validarCBU_CVU();

        validarAlias();

    }

    @Override
    protected void reservarFondos() {
        System.out.println(
                "|| No requerido, Transferencia directa...||"
        );
    }

    @Override
    protected void ejecutarTransaccion() {
        System.out.println(
                "Transferencia bancaria ejecutada correctamente."
        );
    }

    @Override
    protected void notificarResultado() {

    }

    //--------------------------------------
    //--------------------------------------
    //--------------------------------------

    //meth. concreto.
    private void validarCBU_CVU() {
    }

    private void validarAlias() {
    }

}
