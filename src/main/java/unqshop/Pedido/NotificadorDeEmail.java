package main.java.unqshop.Pedido;

public class NotificadorDeEmail implements ObserverPedido{
	
	public NotificadorDeEmail(MailSender mailSender) {
		super();
	}

	@Override
	public void actualizar(Contexto estadoAnterior, Contexto estadoNuevo, Pedido pedido) {
		estadoNuevo.notificarCambio(pedido);
	}

}
