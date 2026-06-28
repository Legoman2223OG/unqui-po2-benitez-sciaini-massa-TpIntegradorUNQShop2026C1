package main.java.unqshop.pagos;

/**
 * Clase abstracta para encapsular el comportamiento del patrón Template Method,
 * sintetizando la estructura de acción de las subclases y proporcionando
 * seguridad y estructura, al algoritmo de ejecución.
 * Esta sección del Template Method, define el flujo de acción;
 * Las subclases definen el comportamiento específico.
 */
//(abstract class - Template Method)
public abstract class MetodoPago {
    private double monto;


    public MetodoPago(double monto) {
        this.monto = monto;
    }

    //inCambiable
    public final void procesar_el_pago() {

        //algoritmo abs.
        validarDatos();
        reservarFondos();
        ejecutarTransaccion();
        notificarResultado();
    }

    //operaciones primitivas
    //------------------------

    protected abstract void validarDatos();

    protected abstract void reservarFondos();

    protected abstract void ejecutarTransaccion();

    //------------------------


    //HOOK Method: posee comportamiento pero puede ser sobreescrito.
    protected void notificarResultado() {
        System.out.println("Pago notificado.");
    }


    //-----------------

    //GETTER & SETTER
    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getMonto() {
        return this.monto;
    }
    //-----------------//-----------------

}
