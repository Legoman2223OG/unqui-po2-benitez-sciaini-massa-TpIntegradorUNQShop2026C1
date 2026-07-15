package main.java.unqshop.pedido;

import main.java.unqshop.catalogo.ItemCatalogo;

import java.time.LocalDate;

public class Enviado implements Contexto {

	@Override
	public ContextoTipo contexto() {
		return ContextoTipo.ENVIADO;
	}

	@Override
	public void confirmar(Pedido pedido) {
		
		throw new RuntimeException("No se puede enviar un pedido que se esta enviando");
	}

	@Override
	public void prepararPedido(Pedido pedido) {
		
		throw new RuntimeException("No se puede preparar un pedido que esta siendo enviado");
	}

	@Override
	public void enviar(Pedido pedido) {
		
		throw new RuntimeException("No se puede enviar un pedido que esta siendo enviado");
	}

	@Override
	public void entregar(Pedido pedido) {
		pedido.setFechaEntrega(LocalDate.now());
		pedido.cambiarContexto(new Entregado());
	}

	@Override
	public void cancelar(Pedido pedido) {
		pedido.cancelarEnEnvio();
		
		
	}


	@Override
	public void quitarItem(Pedido pedido, ItemCatalogo item) {
		
		throw new RuntimeException("Solo se pueden quitar items del pedido en BORRADOR");
	}
	
	public void agregarItem(Pedido pedido, ItemCatalogo item) {
		throw new RuntimeException("Solo se pueden agregar items del pedido en BORRADOR");
	}

	@Override
	public void notificarCambio(Pedido pedido) {
		pedido.getGestor().notificarCambioACliente(pedido, this.contexto());
	
		
	}
	
	@Override
	public void notificarCupon5Porciento(Pedido pedido) {}
	
	@Override
	public void generarComprobanteFizcal(Pedido pedido) {}



}
