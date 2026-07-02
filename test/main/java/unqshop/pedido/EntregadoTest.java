package main.java.unqshop.pedido;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EntregadoTest {

	Entregado entregado;
    Pedido pedido;

	@BeforeEach
	void setUp() throws Exception {
		entregado = new Entregado();
		pedido  = Mockito.mock(Pedido.class); 
	}

	@Test
	void contextoEsEntregado() {
		assertEquals(ContextoTipo.ENTREGADO, entregado.contexto());
	}
	
	@Test
	void seNotificaCambioDeestadoAlCliente() {
		entregado.notificarCambio(pedido);
		
		Mockito.verify(pedido).notificarCambioACliente(entregado.contexto());
		
	}
	
	@Test
	void notificarCuponNoHaceNada() {
		entregado.notificarCupon5Porciento(pedido);

		Mockito.verifyNoInteractions(pedido);
	}
	
	@Test
	void seGeneraComprobanteFizcal() {
		entregado.generarComprobanteFizcal(pedido);
		
		Mockito.verify(pedido).generarComprobanteFizcal();
		
	}
	
	

}
