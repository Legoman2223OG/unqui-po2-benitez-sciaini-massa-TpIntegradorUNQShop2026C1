package main.java.unqshop.Pedido;

public class Cancelado implements Contexto {

	@Override
	public String contexto() {
		// TODO Auto-generated method stub
		return "CANCELADO";
	}

	@Override
	public void confirmar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("El pedido ah sido cancelado");
	}

	@Override
	public void prepararPedido(Pedido pedido, MetodoDePago metodoDePago, Envio envio) {
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
	


}
