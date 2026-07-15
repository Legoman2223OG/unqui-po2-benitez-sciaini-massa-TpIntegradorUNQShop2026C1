package main.java.unqshop.pedido;

import main.java.unqshop.catalogo.ItemCatalogo;

public class Entregado implements Contexto {

	@Override
	public ContextoTipo contexto() {
		return ContextoTipo.ENTREGADO;
	}

	@Override
	public void confirmar(Pedido pedido) {
		
		throw new RuntimeException("el pedido ya fue entregado");
	}

	@Override
	public void prepararPedido(Pedido pedido) {
		
		throw new RuntimeException("el pedido ya fue entregado");
	}

	@Override
	public void enviar(Pedido pedido) {
		
		throw new RuntimeException("el pedido ya fue entregado");
	}

	@Override
	public void entregar(Pedido pedido) {
		
		throw new RuntimeException("el pedido ya fue entregado");
	}

	@Override
	public void cancelar(Pedido pedido) {
		
		throw new RuntimeException("el pedido ya fue entregado");
	}


	@Override
	public void quitarItem(Pedido pedido, ItemCatalogo item) {
		
		throw new RuntimeException("el pedido ya fue entregado");
	}
	
	public void agregarItem(Pedido pedido, ItemCatalogo item) {
		throw new RuntimeException("el pedido ya fue entregado");
	}

	@Override
	public void notificarCambio(Pedido pedido) {
		pedido.getGestor().notificarCambioACliente(pedido, this.contexto());

		
	}
	
	@Override
	public void notificarCupon5Porciento(Pedido pedido) {}

	@Override
	public void generarComprobanteFizcal(Pedido pedido) {
		pedido.getGestor().generarComprobanteFizcal();

		
	}
	

}
