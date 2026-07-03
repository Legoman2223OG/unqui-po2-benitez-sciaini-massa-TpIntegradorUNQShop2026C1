package main.java.unqshop;

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
	//@Mock private EnvioFacade envioFacadeMock; //DEPRECATED 03/07/2026 @17.00hs
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
	 * @throws Exception 
	 */
	@Test
	void test04_CrearPedidoEnvioEstandar() throws Exception {
		//DOC
		MetodoPago docMetPago = mock(MetodoPago.class);
		MailSender docMailSender = mock(MailSender.class);
		Direccion docDir = mock(Direccion.class);
		//EXERCISE
		Pedido pedido = sutUnqShop.crearPedidoConEnvioEstandar("abc@gmail.com", docMetPago, docMailSender, docDir);
		//VERIFY
		Assertions.assertEquals(0, pedido.getId());
	}
	
	/**
	 * Se verifica que al crear un pedido de envio express en la UnqShop sin ningun pedido anterior, devuelve un id 0.
	 * @throws Exception 
	 */
	@Test
	void test05_CrearPedidoEnvioExpress() throws Exception {
		//DOC
		MetodoPago docMetPago = mock(MetodoPago.class);
		MailSender docMailSender = mock(MailSender.class);
		Direccion docDir = mock(Direccion.class);
		//EXERCISE
		Pedido pedido = sutUnqShop.crearPedidoConEnvioExpress("abc@gmail.com", docMetPago, docMailSender, docDir);
		//VERIFY
		Assertions.assertEquals(0, pedido.getId());
	}
	
	/**
	 * Se verifica que al crear un pedido de retiro en una sucursal en la UnqShop sin ningun pedido anterior, devuelve un id 0.
	 * @throws Exception 
	 */
	@Test
	void test06_CrearPedidoRetiroEnSucursal() throws Exception {
		//DOC
		MetodoPago docMetPago = mock(MetodoPago.class);
		MailSender docMailSender = mock(MailSender.class);
		Direccion docDir = mock(Direccion.class);
		Sucursal docSucursal = mock(Sucursal.class);
		//EXERCISE
		Pedido pedido = sutUnqShop.crearPedidoConRetiroEnSucursal("abc@gmail.com", docMetPago, docMailSender, docDir,docSucursal);
		//VERIFY
		Assertions.assertEquals(0, pedido.getId());
	}
	
	/**
	 * Verifica que al generar un reporte se genera un string en un formato especifico
	 */
	@Test
    void test07_GenerarReporte() {
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
	 * Verifica que al instanciar un UnqShop con items, al filtrar los items 1 y 2 excepto el 3, devuelve esos items.
	 */
	@Test
	void test08_UnqShopConItemsYaAgregados() {
		//DOC
		CriterioCatalogo cc = mock(CriterioCatalogo.class);
		ItemCatalogo item1 = mock(ItemCatalogo.class);
		ItemCatalogo item2 = mock(ItemCatalogo.class);
		ItemCatalogo item3 = mock(ItemCatalogo.class);
		when(cc.isSatisfiedBy(item1)).thenReturn(true);
		when(cc.isSatisfiedBy(item2)).thenReturn(true);
		when(cc.isSatisfiedBy(item3)).thenReturn(false);
		//SUT
		UnqShop unqShopConItems = new UnqShop(item1,item2,item3);
		//EXERCISE
		List<ItemCatalogo> items = unqShopConItems.buscarItems(cc);
		//VERIFY
		Assertions.assertEquals(item1, items.get(0));
		Assertions.assertEquals(item2, items.get(1));
		Assertions.assertEquals(2, items.size());
	}
	
	/**
	 * Verifica que al insertar un email sin arrobas o nulo o vacio en la creación de un pedido, da una excepcion. 
	 */
	@Test
	void test09_PedidoConEmailInvalido(){
		//DOC
		MetodoPago docMetPago = mock(MetodoPago.class);
		MailSender docMailSender = mock(MailSender.class);
		Direccion docDir = mock(Direccion.class);
		Sucursal docSucursal = mock(Sucursal.class);
		//VERIFY
		//Mail vacio.
		assertThrows(Exception.class, () -> sutUnqShop.crearPedidoConEnvioExpress("", docMetPago, docMailSender, docDir));
		assertThrows(Exception.class, () -> sutUnqShop.crearPedidoConEnvioEstandar("", docMetPago, docMailSender, docDir));
		assertThrows(Exception.class, () -> sutUnqShop.crearPedidoConRetiroEnSucursal("", docMetPago, docMailSender, docDir,docSucursal));
		//Mail nulo.
		assertThrows(Exception.class, () -> sutUnqShop.crearPedidoConEnvioExpress(null, docMetPago, docMailSender, docDir));
		assertThrows(Exception.class, () -> sutUnqShop.crearPedidoConEnvioEstandar(null, docMetPago, docMailSender, docDir));
		assertThrows(Exception.class, () -> sutUnqShop.crearPedidoConRetiroEnSucursal(null, docMetPago, docMailSender, docDir,docSucursal));
		//Mail sin arroba
		assertThrows(Exception.class, () -> sutUnqShop.crearPedidoConEnvioExpress("Mail.com", docMetPago, docMailSender, docDir));
		assertThrows(Exception.class, () -> sutUnqShop.crearPedidoConEnvioEstandar("Mail.com", docMetPago, docMailSender, docDir));
		assertThrows(Exception.class, () -> sutUnqShop.crearPedidoConRetiroEnSucursal("Mail.com", docMetPago, docMailSender, docDir,docSucursal));
	}
}
