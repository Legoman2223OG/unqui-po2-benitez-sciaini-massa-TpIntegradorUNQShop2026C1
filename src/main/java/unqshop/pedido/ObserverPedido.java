package main.java.unqshop.pedido;

public interface ObserverPedido {
	void actualizar(Contexto estadoAnterior, Contexto estadoNuevo, Pedido pedido);
}
