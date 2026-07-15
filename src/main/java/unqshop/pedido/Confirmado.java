package main.java.unqshop.pedido;

import main.java.unqshop.catalogo.ItemCatalogo;

public class Confirmado implements Contexto {

	@Override
	public ContextoTipo contexto() {
		return ContextoTipo.CONFIRMADO;
	}

	@Override
	public void confirmar(Pedido pedido) {
		
		throw new RuntimeException("No se puede confirmar un pedido ya confirmado");
	}

	@Override
	public void prepararPedido(Pedido pedido) {
		
		pedido.pagarPedido();
		pedido.cambiarContexto(new En_Preparacion());
	}

	@Override
	public void enviar(Pedido pedido) {
		
		throw new RuntimeException("No se puede enviar un pedido sin prepararlo");
	}

	@Override
	public void entregar(Pedido pedido) {
		
		throw new RuntimeException("No se puede se puede entregar un pedido en CONFIRMADO");
	}

	@Override
	public void cancelar(Pedido pedido) {
		pedido.cancelarEnConfirmado();
		
		
	}


	@Override
	public void quitarItem(Pedido pedido, ItemCatalogo item) {
		
		throw new RuntimeException("Solo se pueden quitar items del pedido en BORRADOR");
	}
	
	public void agregarItem(Pedido pedido, ItemCatalogo item) {
		throw new RuntimeException("Solo se pueden agregar items del pedido en BORRADOR");
	}

	@Override
	public void notificarCambio(Pedido pedido) {
		pedido.getGestor().notificarCambioACliente(pedido, this.contexto());
		
	}
	
	@Override
	public void notificarCupon5Porciento(Pedido pedido) {}
	
	@Override
	public void generarComprobanteFizcal(Pedido pedido) {}

}
