package main.java.unqshop.pedido;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EntregadoTest {

	Entregado entregado;
    Pedido pedido;
    GestorSubsistemas gestor;

	@BeforeEach
	void setUp() throws Exception {
		entregado = new Entregado();
		pedido  = Mockito.mock(Pedido.class); 
		gestor = Mockito.mock(GestorSubsistemas.class);
	}

	@Test
	void contextoEsEntregado() {
		assertEquals(ContextoTipo.ENTREGADO, entregado.contexto());
	}
	
	@Test
	void seNotificaCambioDeestadoAlCliente() {
		Mockito.when(pedido.getGestor()).thenReturn(gestor);
		entregado.notificarCambio(pedido);
		
		Mockito.verify(gestor).notificarCambioACliente(pedido, entregado.contexto());
		
	}
	
	@Test
	void notificarCuponNoHaceNada() {
		entregado.notificarCupon5Porciento(pedido);

		Mockito.verifyNoInteractions(pedido);
	}
	
	@Test
	void seGeneraComprobanteFizcal() {
		Mockito.when(pedido.getGestor()).thenReturn(gestor);
		entregado.generarComprobanteFizcal(pedido);
		
		Mockito.verify(gestor).generarComprobanteFizcal();
		
	}
	
	

}
