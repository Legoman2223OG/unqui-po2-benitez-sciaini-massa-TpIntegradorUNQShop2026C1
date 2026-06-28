package main.java.unqshop.Pedido;

public class GeneradorDeFactura implements ObserverPedido {

	public GeneradorDeFactura() {
		
	}
	@Override
	public void actualizar(Contexto estadoAnterior, Contexto estadoNuevo, Pedido pedido) {
		estadoNuevo.generarComprobanteFizcal(pedido);estadoNuevo.generarComprobanteFizcal(pedido);
		
	}
		/*
		ContextoTipo nuevo = evento.getNuevo();
		if (nuevo.equals(ContextoTipo.ENTREGADO)) {
			System.out.print("Generando Comprobante fizcal");
		}

	}
	*/
}

