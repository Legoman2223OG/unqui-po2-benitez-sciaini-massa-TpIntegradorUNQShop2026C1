package main.java.unqshop;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.unqshop.busqueda.CriterioCatalogo;
import main.java.unqshop.catalogo.ItemCatalogo;
import main.java.unqshop.envios.CorreoArgentina;
import main.java.unqshop.envios.Direccion;
import main.java.unqshop.envios.EnvioEstandar;
import main.java.unqshop.envios.EnvioExpress;
import main.java.unqshop.envios.EnvioExpressAPI;
import main.java.unqshop.envios.EnvioFacade;
import main.java.unqshop.envios.MetodoEnvio;
import main.java.unqshop.envios.RetiroEnSucursal;
import main.java.unqshop.envios.Sucursal;
import main.java.unqshop.pagos.MetodoPago;
import main.java.unqshop.pedido.MailSender;
import main.java.unqshop.pedido.Pedido;
import main.java.unqshop.reportes.ReporteVisitor;
import main.java.unqshop.reportes.ReportesFacade;

/**
 * Clase Root que se encarga de los pedidos del cliente hacia el sistema de Unq Shop.
 * Un cliente puede realizar pedidos y buscar items del catalogo.
 * 
 * (No usa algún patrón de diseño).
 * 
 * @author Jara Lautaro Benitez, Tomas Sciani, Fabrizio Lautaro Massa
 * @see CriterioCatalogo
 * @see ItemCatalogo
 */ 
public class UnqShop {
	private List<ItemCatalogo> inventario  = new ArrayList<ItemCatalogo>();
	private List<Pedido> pedidos           = new ArrayList<Pedido>();
	private EnvioFacade envioFacade        = new EnvioFacade();
	private ReportesFacade reporteFacade   = new ReportesFacade();
	
	/**
	 * Crea una instancia de la clase Root de la UnqShop sin ningun item de catalogo cargado.
	 */
	public UnqShop() {}
	
	/**
	 * Crea una instancia de la clase Root de la UnqShop con cierta cantidad de items del catalogo.
	 * @param Los items que se desean cargar, Ninguno puede ser null.
	 */
	public UnqShop(ItemCatalogo ... items) {
		this.inventario.addAll(Arrays.asList(items));
	}
	
	/**
	 * Agrega un item al catalogo de la UnqShop.
	 * @param Un item a agregar al catalogo, no puede ser null.
	 */ 
	public void agregarItem(ItemCatalogo item) {
		this.inventario.add(item);
	}
	
	//DEPRECATED, Rompe encapsulamiento. Un cliente no deberia de poder armar los pedidos por fuera de la clase root.
	///*
	// * agrega un pedido al pedidos de la UnqShop.
	// * @param Un pedido a agregar a pedidos, no puede ser null. 
	// * */
	//public void agregarPedido(Pedido pedido) {
	//	pedidos.add(pedido);
	//}
	
	/**
	 * Busca items del catalogo que cumplan con el criterio indicado por el cliente.
	 * @param El criterio dado por el cliente, no puede ser null.
	 * @return La lista de los items que cumplen con el criterio del cliente, si ninguno coincide, devuelve una lista vacia.
	 */
	public List<ItemCatalogo> buscarItems(CriterioCatalogo criterio){
		return inventario.stream().filter(i -> criterio.isSatisfiedBy(i)).toList();
	}
	
	
	/**
	 * Crea un pedido para el cliente usando el metodo de envio estandar y describe el id resultante para el cliente.
	 * @param El mail del cliente que desea realizar el pedido, no puede ser vacio o nulo o no tener un arroba.
	 * @param El metodo de pago que el cliente quiere usar. No puede ser nulo.
	 * @param El tipo de servicio de envio de mails que usara el cliente. No puede ser nulo.
	 * @param Una direccion para el pedido. No puede ser nulo.
	 * @return La id del pedido en int.
	 * @throws Exception Si el Email no respeta las condiciones dichas.
	 */
	public int crearPedidoConEnvioEstandar(String mailCliente, MetodoPago metodoDePago, MailSender mailSender, Direccion dir) throws Exception {
		Pedido pedidoNuevo = crearPedido(mailCliente, metodoDePago, this.envioFacade.envioEstandar(), mailSender, dir);
		this.pedidos.add(pedidoNuevo);
		return pedidoNuevo.getId();
	}
	
	/**
	 * Crea un pedido para el cliente usando el metodo de envio express.
	 * @param El mail del cliente que desea realizar el pedido, no puede ser vacio o nulo o no tener un arroba.
	 * @param El metodo de pago que el cliente quiere usar. No puede ser nulo.
	 * @param El tipo de servicio de envio de mails que usara el cliente. No puede ser nulo.
	 * @param Una direccion para el pedido. No puede ser nulo.
	 * @return La id del pedido en int.
	 * @throws Exception Si el Email no respeta las condiciones dichas.
	 */
	public int crearPedidoConEnvioExpress(String mailCliente, MetodoPago metodoDePago, MailSender mailSender, Direccion dir) throws Exception {
		Pedido pedidoNuevo = crearPedido(mailCliente, metodoDePago, this.envioFacade.envioExpress(), mailSender, dir);
		this.pedidos.add(pedidoNuevo);
		return pedidoNuevo.getId();
	}
	
	/**
	 * Crea un pedido para el cliente usando el metodo de retiro en sucursal especificada por el cliente.
	 * @param El mail del cliente que desea realizar el pedido, no puede ser vacio o nulo o no tener un arroba.
	 * @param El metodo de pago que el cliente quiere usar. No puede ser nulo.
	 * @param El tipo de servicio de envio de mails que usara el cliente. No puede ser nulo.
	 * @param Una direccion para el pedido. No puede ser nulo.
	 * @param Una sucursal en donde se debe retirar el pedido. No puede ser nulo.
	 * @return La id del pedido en int.
	 * @throws Exception Si el Email no respeta las condiciones dichas.
	 */
	public int crearPedidoConRetiroEnSucursal(String mailCliente, MetodoPago metodoDePago, MailSender mailSender, Direccion dir,Sucursal sucursal) throws Exception {
		Pedido pedidoNuevo = crearPedido(mailCliente, metodoDePago, this.envioFacade.retiroEnSucursal(sucursal), mailSender, dir);
		this.pedidos.add(pedidoNuevo);
		return pedidoNuevo.getId();
	}
	
	/**
	 * Crea un pedido apartir de los argumentos dados.
	 * @param El mail del cliente. No puede ser nulo, vacio y debe tener un arroba.
	 * @param El Metodo de pago del cliente. No puede ser nulo o vacio.
	 * @param El Metodo de envio que desea el cliente. No puede ser nulo.
	 * @param El Metodo de envio de emails que desea el cliente. No puede ser nulo.
	 * @param La direccion que especifica el cliente. No puede ser nulo.
	 * @return Un pedido a partir de los datos especificados
	 * @throws Exception Si el Email no respeta las condiciones dichas.
	 */
	private Pedido crearPedido(String mailCliente, MetodoPago metodoDePago, MetodoEnvio metodoEnvio ,MailSender mailSender, Direccion dir) throws Exception {
		assertEmailValido(mailCliente);
		int id = (int) this.pedidos.stream().count();
		return new Pedido(id, mailCliente, metodoDePago, metodoEnvio, mailSender, dir);
	}
	
	/**
	 * Busca un pedido según la id especificada. 
	 * @param La id del pedido que se quiere buscar, no puede ser menor a 0.
	 * @return El pedido que se necesita buscar.
	 * @throws Exception 
	 */
	private Pedido buscarPedido(int id) throws Exception {
		assertIdPositivoOCero(id);
		return this.pedidos.stream().filter(p -> p.getId() == id).findFirst().orElseThrow();
	}
	
	/**
	 * Confirma el pedido del id correspondiente.
	 * @param El id del pedido, debe ser un id valido.
	 * @throws Exception 
	 */
	public void confirmarPedido(int id) throws Exception {
		assertIdPositivoOCero(id);
		Pedido pedido = this.buscarPedido(id);
		pedido.confirmar();
	}
	
	/**
	 * Prepara el pedido del id correspondiente.
	 * @param El id del pedido, debe ser un id valido.
	 * @throws Exception 
	 */
	public void prepararPedido(int id) throws Exception {
		assertIdPositivoOCero(id);
		Pedido pedido = this.buscarPedido(id);
		pedido.prepararPedido();
	}
	
	/**
	 * Envia el pedido del id correspondiente.
	 * @param El id del pedido, debe ser un id valido.
	 * @throws Exception 
	 */
	public void enviarPedido(int id) throws Exception {
		assertIdPositivoOCero(id);
		Pedido pedido = this.buscarPedido(id);
		pedido.enviar();
	}
	
	/**
	 * Entrega el pedido del id correspondiente.
	 * @param El id del pedido, debe ser un id valido.
	 * @throws Exception 
	 */
	public void entregarPedido(int id) throws Exception {
		assertIdPositivoOCero(id);
		Pedido pedido = this.buscarPedido(id);
		pedido.entregar();
	}
	
	/**
	 * Cancela el pedido del id correspondiente.
	 * @param El id del pedido, debe ser un id valido.
	 * @throws Exception 
	 */
	public void cancelarPedido(int id) throws Exception {
		assertIdPositivoOCero(id);
		Pedido pedido = this.buscarPedido(id);
		pedido.cancelar();
	}
	
	/**
	 * Agrega un item al pedido del id correspondiente.
	 * @param El id del pedido, debe ser un id valido.
	 * @param Un sku de un item del catalogo valido. No puede ser nulo o vacio.
	 * @throws Exception 
	 */
	public void agregarItemPedido(int id, String sku) throws Exception {
		assertIdPositivoOCero(id);
		assertStringNoVacio(sku);
		Pedido pedido = this.buscarPedido(id);
		ItemCatalogo item = this.buscarItem(sku);
		pedido.agregarItem(item);
	}
	
	/**
	 * Elimina el item en el pedido del id correspondiente.
	 * @param El id del pedido, debe ser un id valido.
	 * @param El sku del item que debe ser eliminado del pedido. No puede ser nulo o vacio.
	 * @throws Exception 
	 */
	public void quitarItemPedido(int id, String sku) throws Exception {
		assertIdPositivoOCero(id);
		assertStringNoVacio(sku);
		Pedido pedido = this.buscarPedido(id);
		ItemCatalogo item = this.buscarItem(sku);
		pedido.quitarItem(item);
	}
	
	/**
	 * Genera un reporte de productos más vendidos a partir de un rango de fechas y un formato en particular.
	 * @param La fecha minima, no puede ser null.
	 * @param La fecha maxima, no puede ser null.
	 * @param El formato en como se debe de imprimir el reporte, no puede ser null.
	 * @return El reporte en String dado un formato en especifico.
	 */
	public String generarReporte(LocalDate desde, LocalDate hasta, ReporteVisitor formato) {
		return this.reporteFacade.productosMasVendidos(this.pedidos, desde, hasta, formato);
	}
	
	/**
	 * Busca el item del catalogo que corresponda con el SKU ingresado.
	 * @param Un SKU de item, no puede ser nulo o vacio.
	 * @return El item que se quiere buscar.
	 * @throws Exception 
	 */
	private ItemCatalogo buscarItem(String sku) throws Exception{
		assertStringNoVacio(sku);
		return this.inventario.stream().filter(i -> sku.equals(i.getSku())).findFirst().orElseThrow();
	}
	
	
	private void assertIdPositivoOCero(int id) throws Exception{
		if(id < 0)
			throw new Exception("El id ingresado es menor a 0");
	}
	
	private void assertStringNoVacio(String str) throws Exception{
		if(str.isBlank())
			throw new Exception("El string ingresado es vacio");
	}
	
	private void assertEmailValido(String email) throws Exception {
		if(!email.contains("@") || email.isBlank())
			throw new Exception("El email es vacio o le falta un arroba");
	}
	
}