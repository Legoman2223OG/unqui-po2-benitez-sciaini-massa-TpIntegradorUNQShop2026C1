package main.java.unqshop.pedido;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ConfirmadoTest {
	Confirmado confirmado;
    Pedido pedido;
    GestorSubsistemas gestor;
     
	@BeforeEach
	void setUp() throws Exception {
		confirmado = new Confirmado();
		pedido = Mockito.mock(Pedido.class);
		gestor = Mockito.mock(GestorSubsistemas.class);
	}
	
	
	@Test
	void contextoEsCONFIRMADO() {
		assertEquals(ContextoTipo.CONFIRMADO, confirmado.contexto());
	}
	
	@Test
	void prepararPedidoCambiaAEn_PreparacionYPedidoPaga() {
		confirmado.prepararPedido(pedido);
		
		Mockito.verify(pedido).cambiarContexto(any(En_Preparacion.class));
		Mockito.verify(pedido).pagarPedido();
	}
	
	@Test
    void cancelarCancelaElPedido() {
		confirmado.cancelar(pedido);
		
		Mockito.verify(pedido).cancelarEnConfirmado();
		Mockito.verify(pedido).reponerStock();
		Mockito.verify(pedido).cancelarPriv();
	}
	
	@Test
	void seNotificaCambioDeestadoAlCliente() {
		Mockito.when(pedido.getGestor()).thenReturn(gestor);
		
		confirmado.notificarCambio(pedido);
		
		Mockito.verify(gestor).notificarCambioACliente(pedido, confirmado.contexto());
		
	}
	
	@Test
	void notificarCuponNoHaceNada() {
		confirmado.notificarCupon5Porciento(pedido);

		Mockito.verifyNoInteractions(pedido);
	}

	@Test
	void generarComprobanteFiscalNoHaceNada() {
		confirmado.generarComprobanteFizcal(pedido);

		Mockito.verifyNoInteractions(pedido);
	}

}
