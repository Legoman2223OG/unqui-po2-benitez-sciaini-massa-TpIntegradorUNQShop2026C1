package main.java.unqshop.Pedido;

public class Fidelizacion implements ObserverPedido{
	private MailSender mailSender;
	
	public Fidelizacion(MailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}
	
	public MailSender getMailSender() {
		return mailSender;
	}

	@Override
	public void actualizar(CambioContexto evento, Pedido pedido) {
		ContextoTipo nuevo = evento.getNuevo();
		if (nuevo.equals(ContextoTipo.CANCELADO)) {
			this.getMailSender().enviarMail(pedido.getMailCliente(), "Cupon de Descuento", "Por la cancelacion de su pedido se le envia un cupon del 5%", null);
		}

	}

}
