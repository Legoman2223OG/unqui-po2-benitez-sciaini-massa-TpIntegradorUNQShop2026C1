package main.java.unqshop.pedido;

import main.java.unqshop.catalogo.ItemCatalogo;

public class Cancelado implements Contexto {

	@Override
	public ContextoTipo contexto() {
		return ContextoTipo.CANCELADO;
	}

	@Override
	public void confirmar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("El pedido ah sido cancelado");
	}

	@Override
	public void prepararPedido(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("El pedido ah sido cancelado");
	}

	@Override
	public void enviar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("El pedido ah sido cancelado");
	}

	@Override
	public void entregar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("El pedido ah sido cancelado");
	}

	@Override
	public void cancelar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("El pedido ah sido cancelado");
		
		
	}


	@Override
	public void quitarItem(Pedido pedido, ItemCatalogo item) {
		// TODO Auto-generated method stub
		throw new RuntimeException("El pedido ah sido cancelado");
	}
	
	public void agregarItem(Pedido pedido, ItemCatalogo item) {
		throw new RuntimeException("El pedido ah sido cancelado");
	}

	@Override
	public void notificarCambio(Pedido pedido) {}

	@Override
	public void notificarCupon5Porciento(Pedido pedido) {
		pedido.getGestor().notificarClienteCupon(pedido, 5);
//		pedido.notificarClienteCupon(5);
		
	}
	
	@Override
	public void generarComprobanteFizcal(Pedido pedido) {}
	


}
