package main.java.unqshop.Pedido;

public class Entregado implements Contexto {

	@Override
	public String contexto() {
		// TODO Auto-generated method stub
		return "ENTREGADO";
	}

	@Override
	public void confirmar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("el pedido ya fue entregado");
	}

	@Override
	public void prepararPedido(Pedido pedido, MetodoDePago metodoDePago, Envio envio) {
		// TODO Auto-generated method stub
		throw new RuntimeException("el pedido ya fue entregado");
	}

	@Override
	public void enviar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("el pedido ya fue entregado");
	}

	@Override
	public void entregar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("el pedido ya fue entregado");
	}

	@Override
	public void cancelar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("el pedido ya fue entregado");
		
		
	}


	@Override
	public void quitarItem(Pedido pedido, ItemCatalogo item) {
		// TODO Auto-generated method stub
		throw new RuntimeException("el pedido ya fue entregado");
	}
	
	public void agregarItem(Pedido pedido, ItemCatalogo item) {
		throw new RuntimeException("el pedido ya fue entregado");
	}
	

}
