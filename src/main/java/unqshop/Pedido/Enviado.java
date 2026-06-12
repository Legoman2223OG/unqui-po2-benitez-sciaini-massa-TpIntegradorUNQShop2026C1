package main.java.unqshop.Pedido;

public class Enviado implements Contexto {

	@Override
	public String contexto() {
		// TODO Auto-generated method stub
		return "BORRADOR";
	}

	@Override
	public void confirmar(Pedido pedido) {
		// TODO Auto-generated method stub
		pedido.cambiarContexto(new Confirmado());
	}

	@Override
	public void prepararEnvio(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("No se puede preparar un envio que aun no fue CONFIRMADO");
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
		throw new RuntimeException("Solo se pueden quitar items del pedido en BORRADOR");
	}
	
	public void agregarItem(Pedido pedido, ItemCatalogo item) {
		throw new RuntimeException("Solo se pueden agregar items del pedido en BORRADOR");
	}


}
