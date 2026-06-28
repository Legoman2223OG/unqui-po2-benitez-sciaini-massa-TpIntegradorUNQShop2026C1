package main.java.unqshop.pagos;

/*
 * Cualquier Objeto que adihera a este protocolo podra utilizar el
 * comportamiento preDefinido para una tarjeta de Credito.
 * pudiendo un ente externo 'real' validar los datos brindados
 *  por el objeto TarjetaCredito
 * */

public interface Protocolo_tarjeta_credito {

    void validarNumeroDeTarjeta(String numeroTarjeta);

    void validarCVV(String cvv);

    void validarVencimiento(int fechaVencimiento);

    void preAutorizarBancoEmisor();

    void debitoDiferido();

    void tarjetaCreditoNotificacion();

}
