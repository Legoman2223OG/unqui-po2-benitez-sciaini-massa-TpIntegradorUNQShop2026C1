package main.java.unqshop.Pedido;

public class Confirmado implements Contexto {

	@Override
	public ContextoTipo contexto() {
		return ContextoTipo.CONFIRMADO;
	}

	@Override
	public void confirmar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("No se puede confirmar un pedido ya confirmado");
	}

	@Override
	public void prepararPedido(Pedido pedido, MetodoDePago metodoDePago, Envio envio) {
		// TODO Auto-generated method stub
		pedido.pagarPedido(metodoDePago, envio);
		pedido.cambiarContexto(new En_Preparacion());
	}

	@Override
	public void enviar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("No se puede enviar un pedido sin prepararlo");
	}

	@Override
	public void entregar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("No se puede se puede entregar un pedido en CONFIRMADO");
	}

	@Override
	public void cancelar(Pedido pedido) {
		// TODO Auto-generated method stub
		pedido.reponerStock();
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
