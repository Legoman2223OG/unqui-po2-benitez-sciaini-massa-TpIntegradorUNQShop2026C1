package main.java.unqshop.pedido;

public class NotificadorDeEmail implements ObserverPedido{
	
	public NotificadorDeEmail() {
		super();
	}

	@Override
	public void actualizar(Contexto estadoAnterior, Contexto estadoNuevo, Pedido pedido) {
		estadoNuevo.notificarCambio(pedido);
	}

}
