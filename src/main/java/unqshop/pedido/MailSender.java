package main.java.unqshop.pedido;

public interface MailSender {
	public void enviarMail(String direcciónDestino,String título, String mensaje, String adjunto);

}
