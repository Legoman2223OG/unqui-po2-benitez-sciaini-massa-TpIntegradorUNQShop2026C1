package main.java.unqshop.pedido;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.java.unqshop.catalogo.Categoria;
import main.java.unqshop.catalogo.ItemCatalogo;
import main.java.unqshop.catalogo.Producto;
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
    Producto productoMock;
    
    
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
        productoMock = Mockito.mock(Producto.class);
        
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
        
        Mockito.when(item1.getPeso()).thenReturn(10.0);
        Mockito.when(item2.getPeso()).thenReturn(5.0);
        
        Mockito.when(productoMock.getStock()).thenReturn(0);
        
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
        pedido.agregarItemPriv(item1);
        pedido.agregarItemPriv(item2);

        assertEquals(350.0, pedido.precioItems());
    }
	
	@Test
	void precioPedidoEsPrecioItemsMasPrecioEnvio() {
		
		pedido.agregarItemPriv(item1);
		
		assertEquals(200.0, pedido.precioPedido());
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
	void pedidoEmpizaConTresSubsistema() {
		
		assertEquals(3, pedido.getSubSistemas().size());
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
		
		Mockito.verify(mailSender).enviarMail("cliente@gmail.com", "Cupon de Descuento" , "Por la cancelacion de su pedido se le envia un cupon del " + 50.0 + "%", null);
		
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
	
	
	//Test para confirmar que el estado recibe los mensajes de manera correcta
	@Test
	void estadoBorradorRecibeConfirmado() {
		pedido.setContexto(borrador);
		pedido.confirmar();
		
		Mockito.verify(borrador).confirmar(pedido);
	}
	
	@Test
	void estadoBorradorRecibePrepararPedido() {
		pedido.setContexto(borrador);
		pedido.prepararPedido();
		
		Mockito.verify(borrador).prepararPedido(pedido);
	}
	
	@Test
	void estadoBorradorRecibeEnviar() {
		pedido.setContexto(borrador);
		pedido.enviar();
		
		Mockito.verify(borrador).enviar(pedido);
	}
	
	@Test
	void estadoBorradorRecibeEntregar() {
		pedido.setContexto(borrador);
		pedido.entregar();
		
		Mockito.verify(borrador).entregar(pedido);
	}
	
	@Test
	void estadoBorradorRecibeCancelar() {
		pedido.setContexto(borrador);
		pedido.cancelar();
		
		Mockito.verify(borrador).cancelar(pedido);
	}
	
	@Test
	void estadoBorradorRecibeAgregarItem() {
		pedido.setContexto(borrador);
		pedido.agregarItem(item1);
		
		Mockito.verify(borrador).agregarItem(pedido, item1);
	}
	
	@Test
	void estadoBorradorRecibeQuitarItem() {
		pedido.setContexto(borrador);
		pedido.agregarItem(item1);
		pedido.quitarItem(item1);
		
		Mockito.verify(borrador).quitarItem(pedido, item1);
	}
	
	
	@Test
	void estadoConfirmadoRecibeConfirmar() {
		pedido.setContexto(confirmado);
		pedido.confirmar();
		
		Mockito.verify(confirmado).confirmar(pedido);
	}
	
	@Test
	void estadoConfirmadoRecibePrepararPedido() {
		pedido.setContexto(confirmado);
		pedido.prepararPedido();
		
		Mockito.verify(confirmado).prepararPedido(pedido);
	}
	
	@Test
	void estadoConfirmadoRecibeEnviar() {
		pedido.setContexto(confirmado);
		pedido.enviar();
		
		Mockito.verify(confirmado).enviar(pedido);
	}
	
	@Test
	void estadoConfirmadoRecibeEntregar() {
		pedido.setContexto(confirmado);
		pedido.entregar();
		
		Mockito.verify(confirmado).entregar(pedido);
	}
	
	@Test
	void estadoConfirmadoRecibeCancelar() {
		pedido.setContexto(confirmado);
		pedido.cancelar();
		
		Mockito.verify(confirmado).cancelar(pedido);
	}
	
	@Test
	void estadoConfirmadoRecibeAgregarItem() {
		pedido.setContexto(confirmado);
		pedido.agregarItem(item1);
		
		Mockito.verify(confirmado).agregarItem(pedido, item1);
	}
	
	@Test
	void estadoConfirmadoRecibeQuitarItem() {
		pedido.setContexto(confirmado);
		pedido.agregarItem(item1);
		pedido.quitarItem(item1);
		
		Mockito.verify(confirmado).quitarItem(pedido, item1);
	}
	
	//
	
	@Test
	void estadoEn_PreparacionRecibeConfirmar() {
		pedido.setContexto(en_preparacion);
		pedido.confirmar();
		
		Mockito.verify(en_preparacion).confirmar(pedido);
	}
	
	@Test
	void estadoEn_PreparacionRecibePrepararPedido() {
		pedido.setContexto(en_preparacion);
		pedido.prepararPedido();
		
		Mockito.verify(en_preparacion).prepararPedido(pedido);
	}
	
	@Test
	void estadoEn_PreparacionRecibeEnviar() {
		pedido.setContexto(en_preparacion);
		pedido.enviar();
		
		Mockito.verify(en_preparacion).enviar(pedido);
	}
	
	@Test
	void estadoEn_PreparacionRecibeEntregar() {
		pedido.setContexto(en_preparacion);
		pedido.entregar();
		
		Mockito.verify(en_preparacion).entregar(pedido);
	}
	
	@Test
	void estadoEn_PreparacionRecibeCancelar() {
		pedido.setContexto(en_preparacion);
		pedido.cancelar();
		
		Mockito.verify(en_preparacion).cancelar(pedido);
	}
	
	@Test
	void estadoEn_PreparacionRecibeAgregarItem() {
		pedido.setContexto(en_preparacion);
		pedido.agregarItem(item1);
		
		Mockito.verify(en_preparacion).agregarItem(pedido, item1);
	}
	
	@Test
	void estadoEn_PreparacionRecibeQuitarItem() {
		pedido.setContexto(en_preparacion);
		pedido.agregarItem(item1);
		pedido.quitarItem(item1);
		
		Mockito.verify(en_preparacion).quitarItem(pedido, item1);
	}
	
	//
	
	@Test
	void estadoEnviadoRecibeConfirmado() {
		pedido.setContexto(enviado);
		pedido.confirmar();
		
		Mockito.verify(enviado).confirmar(pedido);
	}
	
	@Test
	void estadoEnviadoRecibePrepararPedido() {
		pedido.setContexto(enviado);
		pedido.prepararPedido();
		
		Mockito.verify(enviado).prepararPedido(pedido);
	}
	
	@Test
	void estadoEnviadoRecibeEnviar() {
		pedido.setContexto(enviado);
		pedido.enviar();
		
		Mockito.verify(enviado).enviar(pedido);
	}
	
	@Test
	void estadoEnviadoRecibeEntreagr() {
		pedido.setContexto(enviado);
		pedido.entregar();
		
		Mockito.verify(enviado).entregar(pedido);
	}
	
	@Test
	void estadoEnviadoRecibeCancelar() {
		pedido.setContexto(enviado);
		pedido.cancelar();
		
		Mockito.verify(enviado).cancelar(pedido);
	}
	
	@Test
	void estadoEnviadoRecibeAgregarItem() {
		pedido.setContexto(enviado);
		pedido.agregarItem(item1);
		
		Mockito.verify(enviado).agregarItem(pedido, item1);
	}
	
	@Test
	void estadoEnviadoRecibeQuitarItem() {
		pedido.setContexto(enviado);
		pedido.agregarItem(item1);
		pedido.quitarItem(item1);
		
		Mockito.verify(enviado).quitarItem(pedido, item1);
	}
	
	//
	
	@Test
	void estadoEntregadoRecibeConfirmar() {
		pedido.setContexto(entregado);
		pedido.confirmar();
		
		Mockito.verify(entregado).confirmar(pedido);
	}
	
	@Test
	void estadoEntregadoRecibePrepararPedido() {
		pedido.setContexto(entregado);
		pedido.prepararPedido();
		
		Mockito.verify(entregado).prepararPedido(pedido);
	}
	
	@Test
	void estadoEntregadoRecibeEnviar() {
		pedido.setContexto(entregado);
		pedido.enviar();
		
		Mockito.verify(entregado).enviar(pedido);
	}
	
	@Test
	void estadoEntregadoRecibeEntregar() {
		pedido.setContexto(entregado);
		pedido.entregar();
		
		Mockito.verify(entregado).entregar(pedido);
	}
	
	@Test
	void estadoEntregadoRecibeCancelar() {
		pedido.setContexto(entregado);
		pedido.cancelar();
		
		Mockito.verify(entregado).cancelar(pedido);
	}
	
	@Test
	void estadoEntregadoRecibeAgregarItem() {
		pedido.setContexto(entregado);
		pedido.agregarItem(item1);
		
		Mockito.verify(entregado).agregarItem(pedido, item1);
	}
	
	@Test
	void estadoEntregadoRecibeQuitarItem() {
		pedido.setContexto(entregado);
		pedido.agregarItem(item1);
		pedido.quitarItem(item1);
		
		Mockito.verify(entregado).quitarItem(pedido, item1);
	}
	
	//
	
	@Test
	void estadoCanceladoRecibeConfirmar() {
		pedido.setContexto(cancelado);
		pedido.confirmar();
		
		Mockito.verify(cancelado).confirmar(pedido);
	}
	
	@Test
	void estadoCanceladoRecibePrepararPedido() {
		pedido.setContexto(cancelado);
		pedido.prepararPedido();
		
		Mockito.verify(cancelado).prepararPedido(pedido);
	}
	
	@Test
	void estadoCanceladoRecibeEnviar() {
		pedido.setContexto(cancelado);
		pedido.enviar();
		
		Mockito.verify(cancelado).enviar(pedido);
	}
	
	@Test
	void estadoCanceladoRecibeEntregar() {
		pedido.setContexto(cancelado);
		pedido.entregar();
		
		Mockito.verify(cancelado).entregar(pedido);
	}
	
	@Test
	void estadoCanceladoRecibeCancelar() {
		pedido.setContexto(cancelado);
		pedido.cancelar();
		
		Mockito.verify(cancelado).cancelar(pedido);
	}
	
	@Test
	void estadoCanceladoRecibeAgregarItem() {
		pedido.setContexto(cancelado);
		pedido.agregarItem(item1);
		
		Mockito.verify(cancelado).agregarItem(pedido, item1);
	}
	
	@Test
	void estadoCanceladoRecibeQuitarItem() {
		pedido.setContexto(cancelado);
		pedido.agregarItem(item1);
		pedido.quitarItem(item1);
		
		Mockito.verify(cancelado).quitarItem(pedido, item1);
	}
	
	//
	@Test
	void seImprimeMensajeCorrectoAlCancelarPedido() {
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        
		pedido.cancelar();
		
		assertEquals("pedido cancelado" + System.lineSeparator(), output.toString());
		
	}
	
	@Test
	void seGeneraComprobandeFizcal() {
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        
		pedido.generarComprobanteFizcal();
		
		assertEquals("Generando Comprobante fizcal", output.toString());
		
	}
	
	@Test
	void cambiarContextoFuncionaCorrectamente() {
		pedido.cambiarContexto(new Cancelado());
		
		assertEquals(ContextoTipo.CANCELADO, pedido.getContextoTipo());
	}
	
	@Test
	void pesoTotalEslaSumaDelPesoDeLosItems() {
		pedido.agregarItem(item1);
		pedido.agregarItem(item2);
		
		assertEquals(15.0, pedido.pesoTotal());
	}
	
	@Test
	void totalEsIgualAlPrecioSumadoDeLosItems() {
		pedido.agregarItem(item1);
		pedido.agregarItem(item2);
		
		assertEquals(pedido.total(), pedido.precioItems());
	}
	
	@Test
	void pedidoEmpiezaEnBorrador() {
		assertEquals(ContextoTipo.BORRADOR, pedido.getContextoTipo());
	}
	
	@Test
	void tiraExcepcionSiSeTrataDeDecrementarUnItemSinStock() throws Exception {
		Producto productoSinStockMock = new Producto("A","I","D",Categoria.ALIMENTOS,100.0,10.0,2,300.0) {
			@Override
			public void decrementarStock() throws Exception {
				throw new Exception("No es posible desincrementar el Stock, ya que no hay mas stock disponible para el item");
			}
		};
		pedido.agregarItem(productoSinStockMock);
		
		Exception excepcion = assertThrows(Exception.class, () -> pedido.descrementarStock());
		Assertions.assertEquals("No es posible desincrementar el Stock, ya que no hay mas stock disponible para el item", excepcion.getMessage());
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

