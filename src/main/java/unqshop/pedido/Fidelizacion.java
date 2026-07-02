package main.java.unqshop.pedido;

public class Fidelizacion implements ObserverPedido{
	
	public Fidelizacion() {
		super();
	}
	
	@Override
	public void actualizar(Contexto estadoAnterior, Contexto estadoNuevo, Pedido pedido) {
		estadoNuevo.notificarCupon5Porciento(pedido);
	}

}
