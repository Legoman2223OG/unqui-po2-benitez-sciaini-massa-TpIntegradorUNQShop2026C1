package main.java.unqshop.pedido;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ConfirmadoTest {
	Confirmado confirmado;
    Pedido pedido;
     
	@BeforeEach
	void setUp() throws Exception {
		confirmado = new Confirmado();
		pedido = Mockito.mock(Pedido.class);
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
	}
	
	@Test
	void seNotificaCambioDeestadoAlCliente() {
		confirmado.notificarCambio(pedido);
		
		Mockito.verify(pedido).notificarCambioACliente(confirmado.contexto());
		
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
