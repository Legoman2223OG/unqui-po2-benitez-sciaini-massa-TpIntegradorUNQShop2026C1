package main.java.unqshop.Pedido;
/*
 * Interace con la cual se definen los diferentes estados de un pedido
 * durante su ciclo de vida.
 * */
public interface Contexto {
	
	ContextoTipo contexto();/*posible cambio*/
	
	void confirmar(Pedido pedido);
	
	void prepararPedido(Pedido pedido);
	
	void enviar(Pedido pedido);
	
	void entregar(Pedido pedido);
	
	void cancelar(Pedido pedido);
	
	void agregarItem(Pedido pedido, ItemCatalogo item);
	
	void quitarItem(Pedido pedido, ItemCatalogo item);
	
	void notificarCambio(Pedido pedido);
	
	void notificarCupon5Porciento(Pedido pedido);
	
	void generarComprobanteFizcal(Pedido pedido);
}
