package unqshop.pagos;

public class
TransferenciaBancaria extends MetodoPago implements EnteBancario {

    Banco banco;
    String CBU_aEnviar;
    String alias_a_enviar;

    //ahora TransferenciaBancaria Depende de una abstraccion (interfaz)
    // y no de un medio en concreto reduciendo acoplamiento.
    // en los correctos metodos del template hace las correspondientes llamadas al EnteBancario
    // que entendera el protocolo de la q implementa tambien esta clase.

    public TransferenciaBancaria(double monto) {
        super(monto);
    }

    public TransferenciaBancaria(double monto, String CBU_aEnviar, String alias_a_enviar) {
        super(monto);
        this.CBU_aEnviar = CBU_aEnviar;
        this.alias_a_enviar = alias_a_enviar;
        banco = new Banco(monto);
    }

    @Override
    protected void validarDatos() {
        validarCBU(this.CBU_aEnviar);

        validarAlias(this.alias_a_enviar);

    }

    @Override
    protected void reservarFondos() {
        System.out.println(
                "|| No requerido, Transferencia directa...||"
        );
        this.getBanco().emitirSeñalDeAprobacion();
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
    @Override
    public void validarCBU(String CBU) {
        //hace las correspondientes llamadas a la entidadBancaria correspondiente que entendera el protocolo de la q implementa tambien esta clase.
        this.getBanco().validarCBU(CBU);

    }

    @Override
    public void validarAlias(String alias) {
        this.getBanco().validarAlias(alias);
    }

    @Override
    public String realizarTransferencia(double monto, String origen, String destino) {
        return "";
    }
    //--------------------------------------
    // getter & setter
    //--------------------------------------

    public Banco getBanco() {
        return banco;
    }

    public String getCBU_aEnviar() {
        return CBU_aEnviar;
    }

    public String getAlias_a_enviar() {
        return alias_a_enviar;
    }
}
