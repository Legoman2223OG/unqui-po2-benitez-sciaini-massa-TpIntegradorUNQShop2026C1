package main.java.unqshop.pedido;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.java.unqshop.catalogo.ItemCatalogo;
import main.java.unqshop.envios.Direccion;
import main.java.unqshop.envios.MetodoEnvio;
import main.java.unqshop.pagos.BilleteraVirtual;
import main.java.unqshop.pagos.MetodoPago;
import main.java.unqshop.pedido.Cancelado;
import main.java.unqshop.pedido.Confirmado;
import main.java.unqshop.pedido.En_Preparacion;
import main.java.unqshop.pedido.Entregado;
import main.java.unqshop.pedido.Enviado;
import main.java.unqshop.pedido.Pedido;


class PedidoTest { 
	
	MetodoPago  metodoPago;
    MetodoEnvio metodoEnvio;
    MailSender  mailSender;
    Direccion   direccion;
    
    ItemCatalogo item1;
    ItemCatalogo item2;
    
    ObserverPedido notificadorMail;
    ObserverPedido generadorDeFactura;
    ObserverPedido fidelizacion;

    Pedido pedido;
    
    Contexto borrador;
    Contexto confirmado;
    Contexto en_preparacion;
    Contexto enviado;
    Contexto entregado;
    Contexto cancelado;
	
	
	@BeforeEach
    void setUp() {
		
        metodoPago   = Mockito.mock(MetodoPago.class);
        metodoEnvio  = Mockito.mock(MetodoEnvio.class);
        
        item1 = Mockito.mock(ItemCatalogo.class);
        item2 = Mockito.mock(ItemCatalogo.class);
        
        mailSender   = Mockito.mock(MailSender.class);
        direccion    = Mockito.mock(Direccion.class);
        
        notificadorMail    = Mockito.mock(NotificadorDeEmail.class);
        generadorDeFactura = Mockito.mock(GeneradorDeFactura.class);
        fidelizacion       = Mockito.mock(Fidelizacion.class);

        pedido = new Pedido(11,
                "cliente@gmail.com",
                metodoPago,
                metodoEnvio,
                mailSender,
                direccion);
        
        Mockito.when(item1.getPrecioFinal()).thenReturn(100.0);
        Mockito.when(item2.getPrecioFinal()).thenReturn(250.0);
        
        Mockito.when(metodoEnvio.calcularCosto(pedido)).thenReturn(100.0);
        
        borrador       = Mockito.mock(Borrador.class);
        confirmado     = Mockito.mock(Confirmado.class);
        en_preparacion = Mockito.mock(En_Preparacion.class);
        enviado        = Mockito.mock(Enviado.class);
        entregado      = Mockito.mock(Entregado.class);
        cancelado      = Mockito.mock(Cancelado.class);
    }
	
	@Test
    void pedidoComienzaSinItems() {

        assertTrue(pedido.getItems().isEmpty());
    }
	
	@Test
    void agregarItemPrivAgregaCorrectamente() {

        pedido.agregarItemPriv(item1);

        assertTrue( !(pedido.getItems().isEmpty()) );
    }
	
	@Test
	void quitarItemPrivQuitaCorrectamente() {
		
		pedido.agregarItemPriv(item1);
		pedido.quitarItemPriv(item1);
		
		assertTrue(pedido.getItems().isEmpty());
	}
	
	@Test
    void precioItemsEsLaSumaDelPrecioFinalDeCadaItem() {

        ItemCatalogo item1 = Mockito.mock(ItemCatalogo.class);
        ItemCatalogo item2 = Mockito.mock(ItemCatalogo.class);


        pedido.agregarItemPriv(item1);
        pedido.agregarItemPriv(item2);

        assertEquals(350.0, pedido.precioItems());
    }
	
	@Test
	void precioPedidoEsPrecioItemsMasPrecioEnvio() {
		
		pedido.agregarItemPriv(item1);
		
		assertEquals(200, pedido.precioPedido());
	}
	
	@Test
	void metodoPagoRecibeLosMensajesSetMontoYProcesar_el_pagoAlPagarElPedido() {
		
		pedido.agregarItem(item1);
		
		pedido.pagarPedido();
		
		Mockito.verify(metodoPago).setMonto(pedido.precioPedido());
		Mockito.verify(metodoPago).procesar_el_pago();	
	}
	
	@Test
	void descrementarStockDecrementaACadaItem() throws Exception {
		pedido.agregarItemPriv(item1);
        pedido.agregarItemPriv(item2);
        
        pedido.descrementarStock();
        
        Mockito.verify(item1).decrementarStock();
        Mockito.verify(item2).decrementarStock();
	}
	
	@Test
	void reponerStockreponeCadaItem() {
		pedido.agregarItemPriv(item1);
        pedido.agregarItemPriv(item2);
        
        pedido.reponerStock();
        
        Mockito.verify(item1).incrementarStock();
        Mockito.verify(item2).incrementarStock();
		
	}
	
	@Test
	void pedidoEmpizaSinNingunSubsistema() {
		assertTrue(pedido.getSubSistemas().isEmpty());
	}
	
	@Test
	void agregarSubsistemaAgregaCorrectamente() {
		pedido.agregarSubsistema(notificadorMail);
		
		assertTrue( !(pedido.getSubSistemas().isEmpty()) );
	}
	
	@Test
	void alCambiarDeContextoSeNotificaALosSubsistemas() {
		
		pedido.agregarSubsistema(notificadorMail);
		pedido.agregarSubsistema(generadorDeFactura);
		pedido.agregarSubsistema(fidelizacion);
		
		Contexto anterior = pedido.getContexto();
		pedido.cambiarContexto(confirmado);
		
		Mockito.verify(notificadorMail).actualizar(anterior, confirmado, pedido);
		Mockito.verify(generadorDeFactura).actualizar(anterior, confirmado, pedido);
		Mockito.verify(fidelizacion).actualizar(anterior, confirmado, pedido);
	}
	
	@Test
	void notificarCambioAClienteEnviaMailAtravesDeMailSender() {
		ContextoTipo confir = ContextoTipo.CONFIRMADO;
		
		pedido.notificarCambioACliente(confir);
		
		Mockito.verify(mailSender).enviarMail("cliente@gmail.com", "Pedido " + confir , "su pedido se encuentra " + confir, null);
	}
	
	@Test
	void notificarClienteCuponEnviaMailAtravesDeMailSender() {
		
		pedido.notificarClienteCupon(50);
		
		Mockito.verify(mailSender).enviarMail("cliente@gmail.com", "Cupon de Descuento" , "Por la cancelacion de su pedido se le envia un cupon del " + 50 + "%", null);
		
	}
	
	@Test
	void generarReembolsoEmpiezaVacio() {
		assertTrue(pedido.getNotasDeCredito().isEmpty());
	}
	
	@Test
	void generarReembolsoAgregaUnaNota() {

	    pedido.generarReembolso(1500);

	    assertTrue( !(pedido.getNotasDeCredito().isEmpty()) );
	}
	
	
	// -----------------------------------------------------------
	//Metodos Sin Mockito
	
	MetodoPago pagoUnq = new BilleteraVirtual(0, 100000);
	Pedido pedidoUnq   = new Pedido(10,"cliente.Test11@gmail.com", pagoUnq, null, null, null);
	
	
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
			pedidoUnq.quitarItem(null);
        });
		assertEquals("Solo se pueden quitar items del pedido en BORRADOR", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.agregarItem(null);
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
			pedidoUnq.quitarItem(null);
        });
		assertEquals("Solo se pueden quitar items del pedido en BORRADOR", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.agregarItem(null);
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
			pedidoUnq.quitarItem(null);
        });
		assertEquals("Solo se pueden quitar items del pedido en BORRADOR", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.agregarItem(null);
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
			pedidoUnq.quitarItem(null);
        });
		assertEquals("el pedido ya fue entregado", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.agregarItem(null);
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
			pedidoUnq.quitarItem(null);
        });
		assertEquals("El pedido ah sido cancelado", ex.getMessage());
		
		ex = assertThrows(RuntimeException.class, () -> {
			pedidoUnq.agregarItem(null);
        });
		assertEquals("El pedido ah sido cancelado", ex.getMessage());
	}
	
	// -----------------------------------------------------------

	

}

