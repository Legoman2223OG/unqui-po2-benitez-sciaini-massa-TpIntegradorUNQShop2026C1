package main.java.unqshop.pedido;

public class Fidelizacion implements ObserverPedido{
	
	public Fidelizacion(MailSender mailSender) {
		super();
	}
	
	@Override
	public void actualizar(Contexto estadoAnterior, Contexto estadoNuevo, Pedido pedido) {
		estadoNuevo.notificarCupon5Porciento(pedido);
	}

}
