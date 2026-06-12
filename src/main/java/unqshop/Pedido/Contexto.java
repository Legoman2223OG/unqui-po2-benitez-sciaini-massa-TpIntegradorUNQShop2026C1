package main.java.unqshop.Pedido;

public interface Contexto {
	
	String contexto();/*posible cambio*/
	
	void confirmar(Pedido pedido);
	
	void prepararEnvio(Pedido pedido);
	
	void enviar(Pedido pedido);
	
	void entregar(Pedido pedido);
	
	void cancelar(Pedido pedido);
	
	void agregarItem(Pedido pedido, CatalogoItem item);
	
	void quitarItem(Pedido pedido, CatalogoItem item);
}
