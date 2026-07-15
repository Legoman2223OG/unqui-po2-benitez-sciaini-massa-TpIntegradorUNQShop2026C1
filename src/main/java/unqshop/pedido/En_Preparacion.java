package main.java.unqshop.pedido;

import main.java.unqshop.catalogo.ItemCatalogo;

public class En_Preparacion implements Contexto {

	@Override
	public ContextoTipo contexto() {
		return ContextoTipo.EN_PREPARACION;
	}

	@Override
	public void confirmar(Pedido pedido) {
		
		throw new RuntimeException("No se puede confirmar un pedido en preparacion");
	}

	@Override
	public void prepararPedido(Pedido pedido) {
		
		throw new RuntimeException("No se puede preparar un pedido que esta siendo preparado");
	}

	@Override
	public void enviar(Pedido pedido) {
		
		pedido.cambiarContexto(new Enviado());
	}

	@Override
	public void entregar(Pedido pedido) {
		
		throw new RuntimeException("No se puede se puede entregar un pedido en EN_PREPARACION");
	}

	@Override
	public void cancelar(Pedido pedido) {
		pedido.cancelarEnEn_Preparacion();
		
		
	}


	@Override
	public void quitarItem(Pedido pedido, ItemCatalogo item) {
		
		throw new RuntimeException("Solo se pueden quitar items del pedido en BORRADOR");
	}
	
	public void agregarItem(Pedido pedido, ItemCatalogo item) {
		throw new RuntimeException("Solo se pueden agregar items del pedido en BORRADOR");
	}

	@Override
	public void notificarCambio(Pedido pedido) {}
	
	@Override
	public void notificarCupon5Porciento(Pedido pedido) {}
	
	@Override
	public void generarComprobanteFizcal(Pedido pedido) {}


}
