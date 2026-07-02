package main.java.unqshop.pedido;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CanceladoTest {

	Cancelado cancelado;
    Pedido pedido;

	@BeforeEach
	void setUp() throws Exception {
		cancelado = new Cancelado();
		pedido  = Mockito.mock(Pedido.class); 
	}

	@Test
	void contextoEsCANCELADO() {
		assertEquals(ContextoTipo.CANCELADO, cancelado.contexto());
	}
	
	@Test
	void seGeneraCupon() {
		cancelado.notificarCupon5Porciento(pedido);
		
		Mockito.verify(pedido).notificarClienteCupon(5);
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
