package main.java.unqshop.Pedido;

public class En_Preparacion implements Contexto {

	@Override
	public ContextoTipo contexto() {
		return ContextoTipo.EN_PREPARACION;
	}

	@Override
	public void confirmar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("No se puede confirmar un pedido en preparacion");
	}

	@Override
	public void prepararPedido(Pedido pedido, MetodoDePago metodoDePago, Envio envio) {
		// TODO Auto-generated method stub
		throw new RuntimeException("No se puede preparar un pedido que esta siendo preparado");
	}

	@Override
	public void enviar(Pedido pedido) {
		// TODO Auto-generated method stub
		pedido.cambiarContexto(new Enviado());
	}

	@Override
	public void entregar(Pedido pedido) {
		// TODO Auto-generated method stub
		throw new RuntimeException("No se puede se puede entregar un pedido en EN_PREPARACION");
	}

	@Override
	public void cancelar(Pedido pedido) {
		pedido.cancelarEnEn_Preparacion();
		/*
		pedido.generarReembolso(pedido.precioPedido());
		pedido.reponerStock();
		pedido.cancelarPriv();
		*/
		
	}


	@Override
	public void quitarItem(Pedido pedido, ItemCatalogo item) {
		// TODO Auto-generated method stub
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
