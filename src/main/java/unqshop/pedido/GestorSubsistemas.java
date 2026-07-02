package main.java.unqshop.pedido;

public class GestorSubsistemas {
	private MailSender mailSender;
	
	public GestorSubsistemas(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void notificarCambioACliente(Pedido pedido, ContextoTipo contexto) {
		this.mailSender.enviarMail(pedido.getMailCliente(), "Pedido " + contexto,
				"su pedido se encuentra " + contexto, null);
	}
	
	public void notificarClienteCupon(Pedido pedido, double porcientoCupon) {
		this.mailSender.enviarMail(pedido.getMailCliente(), "Cupon de Descuento",
				"Por la cancelacion de su pedido se le envia un cupon del " + porcientoCupon + "%", null);
	}

	public void generarComprobanteFizcal() {
		System.out.print("Generando Comprobante fizcal");
	}
	
}
