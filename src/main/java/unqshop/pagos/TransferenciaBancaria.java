package unqshop.pagos;

public class
TransferenciaBancaria extends MetodoPago {

    Banco banco;
    String CBU_aEnviar;
    String alias_a_enviar;

    //ahora TransferenciaBancaria implementa una abstraccion (BANCO)
    // y no de un medio en concreto reduciendo acoplamiento.
    // en los correctos metodos del template hace las correspondientes llamadas al EnteBancario
    // que entendera el protocolo de la situacion.

    public TransferenciaBancaria(double monto) {
        super(monto);
    }

    public TransferenciaBancaria(double monto, String CBU_aEnviar, String alias_a_enviar, Banco banco) {
        super(monto);
        this.CBU_aEnviar = CBU_aEnviar;
        this.alias_a_enviar = alias_a_enviar;
        this.banco = banco;
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

    public void validarCBU(String CBU) {
        //hace las correspondientes llamadas a la entidadBancaria correspondiente que entendera el protocolo de la q implementa tambien esta clase.
        this.getBanco().validarCBU(CBU);

    }


    public void validarAlias(String alias) {
        this.getBanco().validarAlias(alias);
    }


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
