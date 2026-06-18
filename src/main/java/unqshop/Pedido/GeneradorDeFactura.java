package main.java.unqshop.Pedido;

public class GeneradorDeFactura implements ObserverPedido {

	public GeneradorDeFactura() {
		
	}
	@Override
	public void actualizar(CambioContexto evento, Pedido pedido) {
		ContextoTipo nuevo = evento.getNuevo();
		if (nuevo.equals(ContextoTipo.ENTREGADO)) {
			System.out.print("Generando Comprobante fizcal");
		}

	}

}
