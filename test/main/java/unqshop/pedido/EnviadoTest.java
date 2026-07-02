package main.java.unqshop.pedido;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EnviadoTest {
	
	Enviado enviado;
    Pedido pedido;

	@BeforeEach
	void setUp() throws Exception {
		enviado = new Enviado();
		pedido  = Mockito.mock(Pedido.class); 
	}

	@Test
	void contextoEsENVIADO() {
		assertEquals(ContextoTipo.ENVIADO, enviado.contexto());
	}
	
	@Test
	void entregarCambiaAEntregadoYCambiaFechaDeEntrega() {
		enviado.entregar(pedido);
		
		Mockito.verify(pedido).cambiarContexto(new Entregado());
		Mockito.verify(pedido).setFechaEntrega(LocalDate.now());
	}
	
	@Test
	void cancelarCancelaElPedido() {
		enviado.cancelar(pedido);
		
		Mockito.verify(pedido).cancelarEnEnvio();
	}
	
	@Test
	void seNotificaCambioDeestadoAlCliente() {
		enviado.notificarCambio(pedido);
		
		Mockito.verify(pedido).notificarCambioACliente(enviado.contexto());
		
	}
	
	@Test
	void notificarCuponNoHaceNada() {
		enviado.notificarCupon5Porciento(pedido);

		Mockito.verifyNoInteractions(pedido);
	}

	@Test
	void generarComprobanteFiscalNoHaceNada() {
		enviado.generarComprobanteFizcal(pedido);

		Mockito.verifyNoInteractions(pedido);
	}

}
