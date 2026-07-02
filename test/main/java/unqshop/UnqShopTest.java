package main.java.unqshop;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import main.java.unqshop.reportes.ReporteVisitor;
import main.java.unqshop.busqueda.CriterioCatalogo;
import main.java.unqshop.catalogo.ItemCatalogo;
import main.java.unqshop.envios.Direccion;
import main.java.unqshop.envios.EnvioEstandar;
import main.java.unqshop.envios.EnvioFacade;
import main.java.unqshop.envios.Sucursal;
import main.java.unqshop.pagos.MetodoPago;
import main.java.unqshop.pedido.MailSender;
import main.java.unqshop.pedido.Pedido;
import main.java.unqshop.reportes.ReportesFacade;

class UnqShopTest {
	//DOCS
	@Mock private EnvioFacade envioFacadeMock;
    @Mock private ReportesFacade reportesFacadeMock;
    @Mock private MetodoPago metodoPagoMock;
    @Mock private MailSender mailSenderMock;
    @Mock private Direccion direccionMock;
    @Mock private EnvioEstandar envioEstandarMock;
    
    @Spy private List<Pedido> pedidos = new ArrayList<>();
    @Spy private List<ItemCatalogo> inventario = new ArrayList<>();

    @Mock private Pedido pedidoMock;
    @Mock private ItemCatalogo itemMock;
    private ItemCatalogo mockitemPrimero;
    
    //SUT
	@InjectMocks private UnqShop sutUnqShop = new UnqShop();
	
	/**
	 * Por cada escenario donde se necesiten hacer test, se limpian las listas de pedidos y inventario del UnqShop.
	 * @throws Exception
	 */
	@BeforeEach 
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		// Limpiamos las listas antes de cada test por si acaso
        pedidos.clear();
        inventario.clear();
        mockitemPrimero = mock(ItemCatalogo.class);
        inventario.add(mockitemPrimero);
	}

	/**
	 * Se verifica que al agregar un item al catalogo y buscarlo, este deberia de aparecer.
	 */
	@Test
	void test01_AgregarUnItemAlCatalogo() {
		//DOC
		ItemCatalogo docItem1 = mock(ItemCatalogo.class);
		CriterioCatalogo cc = mock(CriterioCatalogo.class);
		when(cc.isSatisfiedBy(docItem1)).thenReturn(true);
		
		when(cc.isSatisfiedBy(mockitemPrimero)).thenReturn(false);
		//Exercise
		sutUnqShop.agregarItem(docItem1);
		ItemCatalogo itemNuevo = sutUnqShop.buscarItems(cc).get(0);
		//Verify
		Assertions.assertEquals(docItem1, itemNuevo);
	}
	
	/**
	 * Se verifica que al hacer un criterio de busqueda para 2 items, devuelve exactamente esos items.
	 */
	@Test
	void test02_BuscarItemCatalogo() {
		//DOC
		ItemCatalogo docItem1 = mock(ItemCatalogo.class);
		inventario.add(docItem1);
        CriterioCatalogo cc = mock(CriterioCatalogo.class);
        when(cc.isSatisfiedBy(docItem1)).thenReturn(true);
     
        when(cc.isSatisfiedBy(mockitemPrimero)).thenReturn(true);
        
		//EXERCISE
		List<ItemCatalogo> items = sutUnqShop.buscarItems(cc);
		
		//VERIFY
		Assertions.assertEquals(docItem1, items.get(1));
		Assertions.assertEquals(mockitemPrimero, items.getFirst());
	}
	
	/**
	 * Se verifica que al instanciar un UnqShop sin objetos y al agregarle 1 y buscarlo, deberia de retornar el objeto en la lista.
	 */
	@Test
	void test03_UnqShopCon1Soloitem() {
		//SUT
		UnqShop sutUnqShopVacio = new UnqShop();
		//DOC
		ItemCatalogo i5 = mock(ItemCatalogo.class);
		CriterioCatalogo cc = mock(CriterioCatalogo.class);
		when(cc.isSatisfiedBy(i5)).thenReturn(true);
		//EXERCISE
		sutUnqShopVacio.agregarItem(i5);
		ItemCatalogo itemNuevo = sutUnqShopVacio.buscarItems(cc).getFirst();
		//VERIFY
		Assertions.assertEquals(i5, itemNuevo);
	}
	
	/**
	 * Se verifica que al crear un pedido de envio estandar en la UnqShop sin ningun pedido anterior, devuelve un id 0.
	 */
	@Test
	void test04_CrearPedidoEnvioEstandar() {
		//DOC
		MetodoPago docMetPago = mock(MetodoPago.class);
		MailSender docMailSender = mock(MailSender.class);
		Direccion docDir = mock(Direccion.class);
		//EXERCISE
		int idPedido = sutUnqShop.crearPedidoConEnvioEstandar("abc@gmail.com", docMetPago, docMailSender, docDir);
		//VERIFY
		Assertions.assertEquals(0, idPedido);
	}
	
	/**
	 * Se verifica que al crear un pedido de envio express en la UnqShop sin ningun pedido anterior, devuelve un id 0.
	 */
	@Test
	void test05_CrearPedidoEnvioExpress() {
		//DOC
		MetodoPago docMetPago = mock(MetodoPago.class);
		MailSender docMailSender = mock(MailSender.class);
		Direccion docDir = mock(Direccion.class);
		//EXERCISE
		int idPedido = sutUnqShop.crearPedidoConEnvioExpress("abc@gmail.com", docMetPago, docMailSender, docDir);
		//VERIFY
		Assertions.assertEquals(0, idPedido);
	}
	
	/**
	 * Se verifica que al crear un pedido de retiro en una sucursal en la UnqShop sin ningun pedido anterior, devuelve un id 0.
	 */
	@Test
	void test06_CrearPedidoRetiroEnSucursal() {
		//DOC
		MetodoPago docMetPago = mock(MetodoPago.class);
		MailSender docMailSender = mock(MailSender.class);
		Direccion docDir = mock(Direccion.class);
		Sucursal docSucursal = mock(Sucursal.class);
		//EXERCISE
		int idPedido = sutUnqShop.crearPedidoConRetiroEnSucursal("abc@gmail.com", docMetPago, docMailSender, docDir,docSucursal);
		//VERIFY
		Assertions.assertEquals(0, idPedido);
	}
	
	/**
	 * Se verifica que al confirmar un pedido, se verifica que hubo una interaccion existosa con el pedido para confirmarlo.
	 * @throws Exception 
	 */
	@Test
	void test07_ConfirmarPedido() throws Exception {
		//DOC
		int idPedido = 0;
		when(pedidoMock.getId()).thenReturn(idPedido);
		pedidos.add(pedidoMock);
		//EXERCISE
		sutUnqShop.confirmarPedido(idPedido);
		//VERIFY
		verify(pedidoMock, times(1)).confirmar();
	}
	
	/**
	 * Se verifica que al preparar un pedido valido, se verifica que hubo una interaccion existosa con el pedido para prepararlo.
	 * @throws Exception 
	 */
	@Test
	void test08_PrepararPedido() throws Exception {
		//DOC
		int idPedido = 0;
		when(pedidoMock.getId()).thenReturn(idPedido);
		pedidos.add(pedidoMock);
		//EXERCISE
		sutUnqShop.prepararPedido(idPedido);
		//VERIFY
		verify(pedidoMock, times(1)).prepararPedido();
	}
	
	/**
	 * Se verifica que al enviar un pedido valido, se verifica que hubo una interaccion existosa con el pedido para enviarlo.
	 * @throws Exception 
	 */
	@Test
	void test09_EnviarPedido() throws Exception {
		//DOC
		int idPedido = 0;
		when(pedidoMock.getId()).thenReturn(idPedido);
		pedidos.add(pedidoMock);
		//EXERCISE
		sutUnqShop.enviarPedido(idPedido);
		//VERIFY
		verify(pedidoMock, times(1)).enviar();
	}
	
	/**
	 * Se verifica que al entregar un pedido valido, se verifica que hubo una interaccion existosa con el pedido para entregarlo.
	 * @throws Exception 
	 */
	@Test
	void test10_EntregarPedido() throws Exception {
		//DOC
		int idPedido = 0;
		when(pedidoMock.getId()).thenReturn(idPedido);
		pedidos.add(pedidoMock);
		//EXERCISE
		sutUnqShop.entregarPedido(idPedido);
		//VERIFY
		verify(pedidoMock, times(1)).entregar();
	}
	
	/**
	 * Se verifica que al cancelar un pedido valido, se verifica que hubo una interaccion existosa con el pedido para cancelarlo.
	 * @throws Exception 
	 */
	@Test
	void test11_CancelarPedido() throws Exception {
		//DOC
		int idPedido = 0;
		when(pedidoMock.getId()).thenReturn(idPedido);
		pedidos.add(pedidoMock);
		//EXERCISE
		sutUnqShop.cancelarPedido(idPedido);
		//VERIFY
		verify(pedidoMock, times(1)).cancelar();
	}
	
	/**
	 * Se verifica que se logra agregar un item del pedido al ingresar un SKU y un ID correcto de pedido e item.
	 * @throws Exception
	 */
	@Test
    void test12_AgregarItemAUnPedido() throws Exception {
        //DOC
        int idPedido = 0;
        String sku = "SKU-123";
        when(pedidoMock.getId()).thenReturn(idPedido);
        pedidos.add(pedidoMock);
        when(itemMock.getSku()).thenReturn(sku);
        inventario.add(itemMock);
        //EXERCISE
        sutUnqShop.agregarItemPedido(idPedido, sku);
        //VERIFY
        verify(pedidoMock, times(1)).agregarItem(itemMock);
    }
	
	/**
	 * Se verifica que se logra quitar un item del pedido al ingresar un SKU y un ID correcto de pedido e item.
	 * @throws Exception
	 */
	@Test
    void test13_EliminarUnItemAUnPedido() throws Exception {
        //DOC
        int idPedido = 0;
        String sku = "SKU-123";
        when(pedidoMock.getId()).thenReturn(idPedido);
        pedidos.add(pedidoMock);
        when(itemMock.getSku()).thenReturn(sku);
        inventario.add(itemMock);
        //EXERCISE
        sutUnqShop.quitarItemPedido(idPedido, sku);
        //VERIFY
        verify(pedidoMock, times(1)).quitarItem(itemMock);
    }
	
	/**
	 * Verifica que al generar un reporte se genera un string en un formato especifico
	 */
	@Test
    void test14_GenerarReporte() {
        //DOC
        LocalDate desde = LocalDate.now().minusDays(5);
        LocalDate hasta = LocalDate.now();
        ReporteVisitor formatoMock = mock(ReporteVisitor.class);
        String reporteEsperado = "Reporte Test";

        when(reportesFacadeMock.productosMasVendidos(pedidos, desde, hasta, formatoMock))
                .thenReturn(reporteEsperado);
        //EXERCISE
        String resultado = sutUnqShop.generarReporte(desde, hasta, formatoMock);

        //VERIFY
        assertEquals(reporteEsperado, resultado);
        verify(reportesFacadeMock, times(1)).productosMasVendidos(pedidos, desde, hasta, formatoMock);
    }
	
	/**
	 * Verifica que lanza una excepcion si se trata de confirmar un pedido de un pedido que no existe.
	 */
	@Test
    void test15_LanzaExcepcionSiElPedidoNoExiste() {
        //DOC
        int idInexistente = 99; 
        // EXERCISE
        assertThrows(NoSuchElementException.class, () -> {
            sutUnqShop.confirmarPedido(idInexistente);
        });
    }
	
	/**
	 * Verifica que lanza una excepcion si al intentar agregar un item del catalogo que no existe a un pedido.
	 */
	@Test
    void test16_LanzaExcepcionSiElItemNoExiste() {
        //DOC
        String skuNoExistente = "SkDDD";
        // EXERCISE
        assertThrows(NoSuchElementException.class, () -> {
            sutUnqShop.agregarItemPedido(0,skuNoExistente);
        });
    }
}
