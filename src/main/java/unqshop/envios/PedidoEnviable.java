package main.java.unqshop.envios;

import main.java.unqshop.pedido.Pedido;

/*
 * Segun GoF == 'ADAPTER'
 * Adapta el Pedido real (main.java.unqshop.pedido.Pedido) al contrato minimo
 * que necesita el modulo de envios (la interfaz envios.Enviable).
 *
 * El modulo de envios no depende de la clase Pedido completa (con su State,
 * sus observers, su pago, etc.), solo de esta interfaz con los 3 datos que
 * precisa para cotizar un envio: total(), pesoTotal() y direccionEntrega().
 * Este adapter es el que traduce un Pedido real a ese contrato.
 *
 * Detalles a tener en cuenta:
 *
 *  - total(): devuelve el precio de los ITEMS (precioItems()), NO precioPedido().
 *    precioPedido() incluye el envio, y como EnvioExpress cotiza usando total(),
 *    si devolviera precioPedido() volveria a pedir el costo de envio y caeriamos
 *    en una recursion infinita (precioPedido -> precioEnvio -> calcularCosto ->
 *    total -> precioPedido -> ...). El recargo express es sobre la mercaderia.
 *
 *  - pesoTotal(): se calcula sumando el peso de cada item del pedido, igual que
 *    el Pedido real suma sus precios en precioItems().
 *
 *  - direccionEntrega(): el Pedido real todavia no modela una direccion de
 *    entrega, asi que se la inyecta por constructor. El dia que el modulo de
 *    pedidos agregue su propia direccion, solo cambia quien construye este
 *    adapter, no el adapter en si.
 */
public class PedidoEnviable implements Enviable {

    private final Pedido pedido;
    private final Direccion direccion;

    public PedidoEnviable(Pedido pedido, Direccion direccion) {
        this.pedido = pedido;
        this.direccion = direccion;
    }

    @Override
    public double total() {
        return pedido.precioItems();
    }

    @Override
    public double pesoTotal() {
        return pedido.getItems().stream()
                .mapToDouble(item -> item.getPeso())
                .sum();
    }

    @Override
    public Direccion direccionEntrega() {
        return direccion;
    }
}
