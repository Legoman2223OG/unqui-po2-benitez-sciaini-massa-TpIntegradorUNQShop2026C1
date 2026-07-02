package main.java.unqshop.pedido;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class En_PreparacionTest {
	
	En_Preparacion en_preparacion;
    Pedido pedido;
	
	@BeforeEach
	void setUp() throws Exception {
		en_preparacion = new En_Preparacion();
		pedido = Mockito.mock(Pedido.class);
	}

	@Test
	void contextoEsEN_PREPARACION() {
		assertEquals(ContextoTipo.EN_PREPARACION, en_preparacion.contexto());
	}
	
	@Test
	void enviarCambiaAEnviado() {
		en_preparacion.enviar(pedido);
		
		Mockito.verify(pedido).cambiarContexto(any(Enviado.class));
	}
	
	@Test
	void cancelarCancelaElPdido() {
		en_preparacion.cancelar(pedido);
		
		Mockito.verify(pedido).cancelarEnEn_Preparacion();
	}
	
	@Test
	void notificarCambioNoHaceNada() {
		en_preparacion.notificarCambio(pedido);

		Mockito.verifyNoInteractions(pedido);
	}

	@Test
	void notificarCuponNoHaceNada() {
		en_preparacion.notificarCupon5Porciento(pedido);

		Mockito.verifyNoInteractions(pedido);
	}

	@Test
	void generarComprobanteFiscalNoHaceNada() {
		en_preparacion.generarComprobanteFizcal(pedido);

		Mockito.verifyNoInteractions(pedido);
	}

}
