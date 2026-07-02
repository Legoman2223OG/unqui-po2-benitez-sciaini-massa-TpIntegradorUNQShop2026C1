package main.java.unqshop.pagos;

/*
 * Cualquier Objeto que adhiera a este protocolo podra utilizar el
 *  comportamiento preDefinido para una tarjeta de Credito.
 * y por transitividad 'se le puede introducir 'pre-comportamientos'
 * a los objetos implementadores de cualquier interfaz'
 */

public interface Protocolo_tarjeta_credito {

    void validarNumeroDeTarjeta(String numeroTarjeta);

    void validarCVV(String cvv);

    void validarVencimiento(int fechaVencimiento);

    void preAutorizarBancoEmisor();

    void debitoDiferido();

    void tarjetaCreditoNotificacion();

}
