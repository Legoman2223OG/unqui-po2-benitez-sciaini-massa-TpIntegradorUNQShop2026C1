package main.java.unqshop.Pedido;

public class Enviado implements Contexto {

	@Override
	public String contexto() {
		// TODO Auto-generated method stub
		return "ENVIADO";
	}

	@Override
	public void confirmar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("No se puede enviar un pedido que se esta enviando");
	}

	@Override
	public void prepararPedido(Pedido pedido, MetodoDePago metodoDePago, Envio envio) {
		// TODO Auto-generated method stub
		throw new RuntimeException("No se puede preparar un pedido que esta siendo enviado");
	}

	@Override
	public void enviar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("No se puede enviar un pedido que esta siendo enviado");
	}

	@Override
	public void entregar(Pedido pedido) {
		// TODO Auto-generated method stub
		pedido.cambiarContexto(new Entregado());
		System.out.println("pedido entregado");
	}

	@Override
	public void cancelar(Pedido pedido) {
		// TODO Auto-generated method stub
		pedido.cancelarEnEnvio();
		
		
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
