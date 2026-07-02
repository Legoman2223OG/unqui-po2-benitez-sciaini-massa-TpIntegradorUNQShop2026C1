package main.java.unqshop.pagos;

/**
 * Fachada del módulo pagos.
 * Abstrae el comportamiento complejo de las clases del paquete
 * para facilitar la interacción con sus funcionalidades.
 *
 * <p>La fachada delega el procesamiento del pago a una instancia de MetodoPago.
 * Cada implementación concreta encapsula un medio de pago distinto y debe construirse
 * antes de invocar #pagarCon_(MetodoPago).
 *
 * <p>Ejemplos de uso:
 *
 * <pre>{@code
 * PagoFacade facade = new PagoFacade();
 *
 * - Billetera virtual: monto a pagar, saldo disponible y saldo reservado inicial
 * - EJEMPLO:
 * MetodoPago billetera = new BilleteraVirtual(1500.0, 5000.0, 0.0);
 * facade.pagarCon_(billetera);
 *-----------------------------------
 * - Transferencia bancaria: monto a transferir
 * -    EJEMPLO:
 *  MetodoPago transferencia = new TransferenciaBancaria(1500.0);
 *  facade.pagarCon_(transferencia);
 *
 * Tarjeta de crédito: monto a debitar
 *  EJEMPLO:
 *  MetodoPago tarjeta = new TarjetaCredito(1500.0);
 *  facade.pagarCon_(tarjeta);
 */
public class PagoFacade {


    /*Billetera Virtual*/
    public void pagarCon_(MetodoPago metodoDePago) {

        metodoDePago.procesar_el_pago();
    }

}

