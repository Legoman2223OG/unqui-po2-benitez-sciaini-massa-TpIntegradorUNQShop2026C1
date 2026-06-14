package main.java.unqshop.Pedido;

public interface Contexto {
	
	String contexto();/*posible cambio*/
	
	void confirmar(Pedido pedido);
	
	void prepararPedido(Pedido pedido, MetodoDePago metodoDePago, Envio envio);
	
	void enviar(Pedido pedido);
	
	void entregar(Pedido pedido);
	
	void cancelar(Pedido pedido);
	
	void agregarItem(Pedido pedido, ItemCatalogo item);
	
	void quitarItem(Pedido pedido, ItemCatalogo item);
	
	
}
