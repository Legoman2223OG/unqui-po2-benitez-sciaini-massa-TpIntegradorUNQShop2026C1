package main.java.unqshop.Pedido;

public interface MailSender {
	public void enviarMail(String direcciónDestino,String título, String mensaje, String adjunto);

}
