package main.java.unqshop.Pedido;

public class Borrador implements Contexto {

	@Override
	public String contexto() {
		// TODO Auto-generated method stub
		return "BORRADOR";
	}

	@Override
	public void confirmar(Pedido pedido) {
		// TODO Auto-generated method stub
		pedido.cambiarContexto(new Confirmado());
		pedido.descrementarStock();
		
	}

	@Override
	public void prepararPedido(Pedido pedido, MetodoDePago metodoDePago, Envio envio) {
		// TODO Auto-generated method stub
		throw new RuntimeException("No se puede preparar un pedido que aun no fue CONFIRMADO");
	}

	@Override
	public void enviar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("No se puede enviar un pedido en BORRADOR");
	}

	@Override
	public void entregar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("No se puede se puede entregar un pedido en BORRADOR");
	}

	@Override
	public void cancelar(Pedido pedido) {
		// TODO Auto-generated method stub
		pedido.cancelarPriv();
		
		
	}


	@Override
	public void quitarItem(Pedido pedido, ItemCatalogo item) {
		// TODO Auto-generated method stub
		pedido.quitarItemPriv(item);
	}
	
	public void agregarItem(Pedido pedido, ItemCatalogo item) {
		pedido.agregarItemPriv(item);
	}
	


}
