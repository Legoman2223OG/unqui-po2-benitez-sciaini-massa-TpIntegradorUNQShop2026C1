package main.java.unqshop.Pedido;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import main.java.unqshop.pagos.BilleteraVirtual;
import main.java.unqshop.pagos.MetodoPago;


class PedidoTest {
	MetodoPago pagoUnq = new BilleteraVirtual(0, 100000);
	Pedido pedidoUnq   = new Pedido("cliente.Test11@gmail.com", pagoUnq, null, null, null);
	
	
	@BeforeEach
	void setUp() throws Exception {}
	
	@Test
	void seTesteaLasOperacionesInvalidasEnElEstadoBORRADOR() {
		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.prepararPedido();
        });
		assertEquals("No se puede preparar un pedido que aun no fue CONFIRMADO", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.enviar();
        });
		assertEquals("No se puede enviar un pedido en BORRADOR", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.entregar();
        });
		assertEquals("No se puede se puede entregar un pedido en BORRADOR", ex.getMessage());
	}
	
	@Test
	void seTesteaLasOperacionesInvalidasEnElEstadoCONFIRMADO() {
		pedidoUnq.cambiarContexto(new Confirmado());
		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.confirmar();
        });
		assertEquals("No se puede confirmar un pedido ya confirmado", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.enviar();
        });
		assertEquals("No se puede enviar un pedido sin prepararlo", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.entregar();
        });
		assertEquals("No se puede se puede entregar un pedido en CONFIRMADO", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.quitarItem(null, null);
        });
		assertEquals("Solo se pueden quitar items del pedido en BORRADOR", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.agregarItem(null, null);
        });
		assertEquals("Solo se pueden agregar items del pedido en BORRADOR", ex.getMessage());
	}
	
	@Test
	void seTesteaLasOperacionesInvalidasEnElEstadoEN_PREPARACION() {
		pedidoUnq.cambiarContexto(new En_Preparacion());
		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.confirmar();
        });
		assertEquals("No se puede confirmar un pedido en preparacion", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.prepararPedido();
        });
		assertEquals("No se puede preparar un pedido que esta siendo preparado", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.entregar();
        });
		assertEquals("No se puede se puede entregar un pedido en EN_PREPARACION", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.quitarItem(null, null);
        });
		assertEquals("Solo se pueden quitar items del pedido en BORRADOR", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.agregarItem(null, null);
        });
		assertEquals("Solo se pueden agregar items del pedido en BORRADOR", ex.getMessage());
	}
	
	@Test
	void seTesteaLasOperacionesInvalidasEnElEstadoENVIADO() {
		pedidoUnq.cambiarContexto(new Enviado());
		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.confirmar();
        });
		assertEquals("No se puede enviar un pedido que se esta enviando", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.prepararPedido();
        });
		assertEquals("No se puede preparar un pedido que esta siendo enviado", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.enviar();
        });
		assertEquals("No se puede enviar un pedido que esta siendo enviado", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.quitarItem(null, null);
        });
		assertEquals("Solo se pueden quitar items del pedido en BORRADOR", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.agregarItem(null, null);
        });
		assertEquals("Solo se pueden agregar items del pedido en BORRADOR", ex.getMessage());
	}
	
	@Test
	void seTesteaLasOperacionesInvalidasEnElEstadoENTREGADO() {
		pedidoUnq.cambiarContexto(new Entregado());
		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.confirmar();
        });
		assertEquals("el pedido ya fue entregado", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.prepararPedido();
        });
		assertEquals("el pedido ya fue entregado", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.enviar();
        });
		assertEquals("el pedido ya fue entregado", ex.getMessage());
			
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.entregar();
        });
		assertEquals("el pedido ya fue entregado", ex.getMessage());
			
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.cancelar();
        });
		assertEquals("el pedido ya fue entregado", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.quitarItem(null, null);
        });
		assertEquals("el pedido ya fue entregado", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.agregarItem(null, null);
        });
		assertEquals("el pedido ya fue entregado", ex.getMessage());
	}
	
	@Test
	void seTesteaLasOperacionesInvalidasEnElEstadoCANCELADO() {
		pedidoUnq.cambiarContexto(new Cancelado());
		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.confirmar();
        });
		assertEquals("El pedido ah sido cancelado", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.prepararPedido();
        });
		assertEquals("El pedido ah sido cancelado", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.enviar();
        });
		assertEquals("El pedido ah sido cancelado", ex.getMessage());
			
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.entregar();
        });
		assertEquals("El pedido ah sido cancelado", ex.getMessage());
			
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.cancelar();
        });
		assertEquals("El pedido ah sido cancelado", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.quitarItem(null, null);
        });
		assertEquals("El pedido ah sido cancelado", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.agregarItem(null, null);
        });
		assertEquals("El pedido ah sido cancelado", ex.getMessage());
	}
	

	

}
