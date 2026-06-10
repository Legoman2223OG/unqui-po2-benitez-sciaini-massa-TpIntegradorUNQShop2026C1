package unqshop.pagos;

public class Banco implements EnteBancario {
    private String CBU;
    private String alias;
    private double monto;

    public Banco(double monto) {
        this.monto = monto;
    }

    @Override
    public void validarCBU(String CBU) {

        if (CBU.isBlank() || CBU.length() < 10) {
            throw new IllegalArgumentException(
                    "Debe ingresar un CBU  válido.\n al menos 10 digitos"
            );
        }
    }

    @Override
    public void validarAlias(String alias) {
        if (alias.isBlank()) {
            return; //no hacerlo obligatorio
        }
        if (alias.length() < 6) {
            throw new IllegalArgumentException(
                    "Alias inválido. Minimo 6 caracteres."
            );

        }
    }

    @Override
    public String realizarTransferencia(double monto, String origen, String destino) {
        return "Transferencia Bancaria con Monto $" + monto + " enviada desde" + origen + "hacia" + destino;
    }

    public void emitirSeñalDeAprobacion() {
        System.out.println("Pago Aprobado por Entidad Bancaria");
    }

    // --------------
    // GETTER & SETTER
    // --------------
    public String getCBU() {
        return CBU;
    }

    public String getAlias() {
        return alias;
    }

    public void setCBU(String CBU) {
        this.CBU = CBU;
    }

    public double getMonto() {
        return monto;
    }
}

