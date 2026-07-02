package main.java.unqshop.pedido;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EnviadoTest {
	
	Enviado enviado;
    Pedido pedido;
    GestorSubsistemas gestor;
    
	@BeforeEach
	void setUp() throws Exception {
		enviado = new Enviado();
		pedido  = Mockito.mock(Pedido.class); 
		gestor = Mockito.mock(GestorSubsistemas.class);
	}

	@Test
	void contextoEsENVIADO() {
		assertEquals(ContextoTipo.ENVIADO, enviado.contexto());
	}
	
	@Test
	void entregarCambiaAEntregadoYCambiaFechaDeEntrega() {
		enviado.entregar(pedido);
		
		Mockito.verify(pedido).cambiarContexto(any(Entregado.class));
		Mockito.verify(pedido).setFechaEntrega(LocalDate.now());
	}
	
	@Test
	void seNotificaCambioDeestadoAlCliente() {
		Mockito.when(pedido.getGestor()).thenReturn(gestor);
		enviado.notificarCambio(pedido);
		
		Mockito.verify(gestor).notificarCambioACliente(pedido, enviado.contexto());
		
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
