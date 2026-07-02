package main.java.unqshop.pedido;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CanceladoTest {

	Cancelado cancelado;
    Pedido pedido;
    GestorSubsistemas gestor;

	@BeforeEach
	void setUp() throws Exception {
		cancelado = new Cancelado();
		pedido  = Mockito.mock(Pedido.class); 
		gestor = Mockito.mock(GestorSubsistemas.class);
	}

	@Test
	void contextoEsCANCELADO() {
		assertEquals(ContextoTipo.CANCELADO, cancelado.contexto());
	}
	
	@Test
	void seGeneraCupon() {
		Mockito.when(pedido.getGestor()).thenReturn(gestor);
		cancelado.notificarCupon5Porciento(pedido);
		
		Mockito.verify(gestor).notificarClienteCupon(pedido, 5);
	}
	
	@Test
	void notificarCambioNoHaceNada() {
		cancelado.notificarCambio(pedido);

		Mockito.verifyNoInteractions(pedido);
	}
	
	@Test
	void generarComprobanteFiscalNoHaceNada() {
		cancelado.generarComprobanteFizcal(pedido);

		Mockito.verifyNoInteractions(pedido);
	}
	

}
