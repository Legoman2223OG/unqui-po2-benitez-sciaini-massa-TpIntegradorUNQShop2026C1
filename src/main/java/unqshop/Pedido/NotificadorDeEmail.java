package main.java.unqshop.Pedido;

public class NotificadorDeEmail implements ObserverPedido{
	private MailSender mailSender;
	
	
	public NotificadorDeEmail(MailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	@Override
	public void actualizar(CambioContexto evento, Pedido pedido) {
		ContextoTipo nuevo = evento.getNuevo();
		if (nuevo.equals(ContextoTipo.CONFIRMADO) || nuevo.equals(ContextoTipo.ENVIADO) || nuevo.equals(ContextoTipo.ENTREGADO)) {
			this.getMailSender().enviarMail(pedido.getMailCliente(), "Pedido " + nuevo , "su pedido se encuentra " + nuevo, null);
		}

	}

}
