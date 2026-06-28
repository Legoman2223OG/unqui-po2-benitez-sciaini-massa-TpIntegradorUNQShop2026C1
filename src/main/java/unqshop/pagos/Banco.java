package unqshop.pagos;

import java.util.UUID;

public class Banco implements EnteBancario, Protocolo_tarjeta_credito {
    private String CBU;
    private String alias;
    private double monto;
    //llamada al metodo estatico para crear un numero random para simular un numero de operacion
    private String numeroOperacion = UUID.randomUUID().toString();

    public Banco(double monto) {
        this.monto = monto;
    }

    //  - - - - - - - - - - - - - - - - - - - - -
    // PROTOCOLO TRANSFERENCIA BANCARIA
    //  - - - - - - - - - - - - - - - - - - - - -
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

    public void notificarResultado(String cbu) {
        System.out.println("Transferencia enviada con" + cbu + "numero de transferencia" + this.getNumeroOperacion());
    }

    public String getNumeroOperacion() {

        return this.numeroOperacion;
    }
    //  - - - - - - - - - - - - - - - - - - - - -
    //  - - - - - - - - - - - - - - - - - - - - -
    //  - - - - - - - - - - - - - - - - - - - - -


    // --------------
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

    //---------
    // PROTOCOLO TARJETA DE CREDITO {
    @Override
    public void validarVencimiento(int fechaVencimiento) {
        //CONSEGUIR EL TIMPO ACTUAL
        int aÑoActual = java.time.Year.now().getValue();

        if (fechaVencimiento < aÑoActual) {
            throw new IllegalArgumentException("Tarjeta vencida.");
        }

    }

    @Override
    public void validarCVV(String cvv) {
        if (cvv == null || cvv.length() != 3) {
            throw new IllegalArgumentException("CVV inválido.");
        }
    }

    @Override
    public void validarNumeroDeTarjeta(String numeroTarjeta) {
        if (numeroTarjeta == null || numeroTarjeta.length() != 10) {
            throw new IllegalArgumentException("Número de tarjeta inválido.");
        }
    }

    //-------------------------
    // reservarFondos:
    @Override
    public void preAutorizarBancoEmisor() {
        System.out.println("Pre-Autorizando $" + this.getMonto() + "En el banco Emisor.");
    }

    //ejecutarTransaccion:

    @Override
    public void debitoDiferido() {
        System.out.println("Débito diferido con monto $" + getMonto());
    }

    //}
    @Override
    public void tarjetaCreditoNotificacion() {
        System.out.println(
                "Cupón de pago generado."
        );
    }


}

