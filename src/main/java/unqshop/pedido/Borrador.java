package main.java.unqshop.pedido;

import main.java.unqshop.catalogo.ItemCatalogo;

public class Borrador implements Contexto {

	@Override
	public ContextoTipo contexto() {
		return ContextoTipo.BORRADOR;
	}

	@Override
	public void confirmar(Pedido pedido) throws Exception {
		pedido.cambiarContexto(new Confirmado());
		pedido.descrementarStock();
		
	}

	@Override
	public void prepararPedido(Pedido pedido) {
		throw new RuntimeException("No se puede preparar un pedido que aun no fue CONFIRMADO");
	}

	@Override
	public void enviar(Pedido pedido) {
		throw new RuntimeException("No se puede enviar un pedido en BORRADOR");
	}

	@Override
	public void entregar(Pedido pedido) {
		throw new RuntimeException("No se puede se puede entregar un pedido en BORRADOR");
	}

	@Override
	public void cancelar(Pedido pedido) {
		pedido.cancelarPriv();
		
		
	}


	@Override
	public void quitarItem(Pedido pedido, ItemCatalogo item) {
		pedido.quitarItemPriv(item);
	}
	
	public void agregarItem(Pedido pedido, ItemCatalogo item) {
		pedido.agregarItemPriv(item);
	}

	@Override
	public void notificarCambio(Pedido pedido) {}

	@Override
	public void notificarCupon5Porciento(Pedido pedido) {}

	@Override
	public void generarComprobanteFizcal(Pedido pedido) {}
	


}
