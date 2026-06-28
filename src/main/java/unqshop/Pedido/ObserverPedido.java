package main.java.unqshop.Pedido;

public interface ObserverPedido {
	void actualizar(Contexto estadoAnterior, Contexto estadoNuevo, Pedido pedido);
}
