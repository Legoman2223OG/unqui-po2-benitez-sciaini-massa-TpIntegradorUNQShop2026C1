package main.java.unqshop.pedido;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.*;

import main.java.unqshop.catalogo.ItemCatalogo;
import main.java.unqshop.envios.MetodoEnvio;
import main.java.unqshop.pagos.MetodoPago;

import main.java.unqshop.envios.Direccion;
import main.java.unqshop.envios.Enviable;
import main.java.unqshop.envios.PedidoEnviable;

import java.time.LocalDate;

/*
 * Clase que representa un pedido.
 * 
 * Se utiliza el patron de diseño State para controlar los diferentes comportamientos
 * dependiendo del contexto(estado) en el que se encuentre, sin que el pedido lo sepa.
 * 
 * Se utiliza el patron de diseño Observer para notificar a los subsistemas cuando 
 * ocurre un cambio de contexto en el pedido, sin que este conozca o dependa
 * de los subsistemas.
 * @author Jara Lautaro Benitez
 * */

public class Pedido implements Enviable {
	private List<ItemCatalogo> items; /* items que pide el cliente en el pedido */
	private List<ObserverPedido> subSistemas; /* se les notifica cuando hay cambio de estado en el pedido */
	private Contexto contexto; /* etapa del ciclo de vida del pedido */
	private String mailCliente;
	private MetodoPago metodoDePago;
	private MetodoEnvio modoDeEnvio;
	private Direccion direccion;
	private List<NotaDeCredito> notasDeCredito;
	private LocalDate fechaEntrega;
	private int id;
	private GestorSubsistemas gestor;

	public Pedido(int id, String mailCliente, MetodoPago metodoDePago, MetodoEnvio envio, MailSender mailSender,
			Direccion direccion) {
		super();
		this.id = id;
		this.items = new ArrayList<ItemCatalogo>();
		this.subSistemas = new ArrayList<ObserverPedido>();
		this.contexto = new Borrador();
		this.notasDeCredito = new ArrayList<NotaDeCredito>();
		this.mailCliente = mailCliente;
		this.metodoDePago = metodoDePago;
		this.modoDeEnvio = envio;
		this.direccion = direccion;
		
		this.gestor = new GestorSubsistemas(mailSender);
		
		this.agregarSubsistema(new NotificadorDeEmail());
		this.agregarSubsistema(new GeneradorDeFactura());
		this.agregarSubsistema(new Fidelizacion());
	}

	/* Operaciones del pedido durante su ciclo de vida */

	public void agregarSubsistema(ObserverPedido subsistema) {
		this.getSubSistemas().add(subsistema);
	}

	public void confirmar() throws Exception {/* Valido solo en BORRADOR */
		this.getContexto().confirmar(this);
	} 

	public void prepararPedido() {/* Valido solo en CONFIRMADO */
		this.getContexto().prepararPedido(this);
	}

	public void enviar() {/* Valido solo eb EN_PREPARACION */
		this.getContexto().enviar(this);
	}

	public void entregar() {/* Solo valido en ENVIADO */
		this.getContexto().entregar(this);
	}

	public void cancelar() {/* valido en BORRADOR, CONFIRMADO, EN_PREPARACION, ENVIADO */
		this.getContexto().cancelar(this);
	}

	public void agregarItem(ItemCatalogo item) {
		this.getContexto().agregarItem(this, item);
	}

	public void quitarItem(ItemCatalogo item) {
		this.getContexto().quitarItem(this, item);
	}
	
	/* Metodos relacionado a los precios*/
	
	public double precioItems() {
		return this.getItems().stream().mapToDouble(item -> item.getPrecioFinal()).sum();
	}

	public double precioEnvio() {
		return this.getModoDeEnvio().calcularCosto(this);
	}

	public double precioPedido() {
		return this.precioItems() + this.precioEnvio();
	}
	
	
	/* Operaciones internas */

	void agregarItemPriv(ItemCatalogo item) {
		this.getItems().add(item);

	}

	void quitarItemPriv(ItemCatalogo item) {
		this.getItems().remove(item);
	}

	protected void cancelarPriv() {
		System.out.println("pedido cancelado");
		this.cambiarContexto(new Cancelado());
	}
 
	protected void cambiarContexto(Contexto contexto) {
		Contexto estadoAnterior = this.getContexto();
		this.setContexto(contexto);
		this.actualizarSubsistemas(estadoAnterior, contexto);
	}

	protected void actualizarSubsistemas(Contexto estadoAnterior, Contexto estadoNuevo) {
		this.getSubSistemas().stream().forEach(sub -> sub.actualizar(estadoAnterior, estadoNuevo, this));
	}

	protected void descrementarStock() throws Exception {
		for (ItemCatalogo item : this.getItems()) {
			item.decrementarStock();
		}
	} 

	protected void pagarPedido() {
		this.getMetodoDePago().setMonto(this.precioPedido());
		this.getMetodoDePago().procesar_el_pago();
	}

	protected void agregarNotaDeCredito(NotaDeCredito notaDeCredito) {
		this.getNotasDeCredito().add(notaDeCredito);
	}

//encapsulamiento de cancelacion en los estados 
//-------------------------------------------
	protected void cancelarEnConfirmado() {
		this.reponerStock();
		this.cancelarPriv();
	}

	protected void cancelarEnEn_Preparacion() {
		this.generarReembolso(this.precioPedido());
		this.reponerStock();
		this.cancelarPriv();
	}

	protected void cancelarEnEnvio() {
		this.generarReembolso(this.precioItems());
		this.cancelarPriv();
	}

//-------------------------------------------

	protected void generarReembolso(double montoDeReembolso) {
		this.agregarNotaDeCredito(new NotaDeCredito(montoDeReembolso));
	}

	protected void reponerStock() {
		this.getItems().stream().forEach(item -> item.incrementarStock());
	}

	protected String getMailCliente() {
		return this.mailCliente;
	}



//Metodos interface Enviable
	@Override
	public double total() {
		return this.precioItems();
	}

	@Override
	public double pesoTotal() {
		return this.getItems().stream().mapToDouble(item -> item.getPeso()).sum();
	}

	@Override
	public Direccion direccionEntrega() {
		return direccion;
	}
	
	

	/* Getters y setters */

	public int getId() {
		return id;
	}

	public List<ItemCatalogo> getItems() {
		return items;
	}

	protected List<ObserverPedido> getSubSistemas() {
		return subSistemas;
	}

	protected Contexto getContexto() {
		return contexto;
	}

	protected void setContexto(Contexto contexto) {
		this.contexto = contexto;
	}

	public MetodoPago getMetodoDePago() {
		return this.metodoDePago;
	}
 
	public List<NotaDeCredito> getNotasDeCredito() {
		return this.notasDeCredito;
	}

	public MetodoEnvio getModoDeEnvio() {
		return this.modoDeEnvio;
	}

	protected ContextoTipo getContextoTipo() {
		return this.getContexto().contexto();
	}

	public LocalDate getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDate fecha) {
		this.fechaEntrega = fecha;
	}

	protected Direccion getDireccion() {
		return direccion;
	}
	
	protected GestorSubsistemas getGestor() {
		return this.gestor;
	}

}
