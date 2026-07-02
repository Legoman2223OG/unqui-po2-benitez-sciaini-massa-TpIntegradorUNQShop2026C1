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
	private MailSender mailSender;
	private LocalDate fechaEntrega;
	private int id;

	public Pedido(int id, String mailCliente, MetodoPago metodoDePago, MetodoEnvio envio, MailSender mailSender,
			Direccion direccion) {
		super();
		this.items = new ArrayList<ItemCatalogo>();
		this.subSistemas = new ArrayList<ObserverPedido>();
		this.contexto = new Borrador();
		this.notasDeCredito = new ArrayList<NotaDeCredito>();
		this.mailCliente = mailCliente;
		this.metodoDePago = metodoDePago;
		this.modoDeEnvio = envio;
		this.mailSender = mailSender;
		this.direccion = direccion;
		
		this.agregarSubsistema(new NotificadorDeEmail());
		
	}

	/* Operaciones del pedido durante su ciclo de vida */

	public void agregarSubsistema(ObserverPedido subsistema) {
		this.getSubSistemas().add(subsistema);
	}

	public void confirmar() {/* Valido solo en BORRADOR */
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
	/* Operaciones internas */

	public void agregarItemPriv(ItemCatalogo item) {
		this.getItems().add(item);

	}

	public void quitarItemPriv(ItemCatalogo item) {
		this.getItems().remove(item);
	}

	public void cancelarPriv() {
		System.out.println("pedido cancelado");
		this.cambiarContexto(new Cancelado());
	}

	public void cambiarContexto(Contexto contexto) {
		Contexto estadoAnterior = this.getContexto();
		this.setContexto(contexto);
		this.actualizarSubsistemas(estadoAnterior, contexto);
	}

	public void actualizarSubsistemas(Contexto estadoAnterior, Contexto estadoNuevo) {
		this.getSubSistemas().stream().forEach(sub -> sub.actualizar(estadoAnterior, estadoNuevo, this));
	}

	public void descrementarStock() {
		for (ItemCatalogo item : this.getItems()) {
			try {
				item.decrementarStock();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public double precioItems() {
		return this.getItems().stream().mapToDouble(item -> item.getPrecioFinal()).sum();
	}

	public double precioEnvio() {
//	return this.getModoDeEnvio().calcularCosto(new PedidoEnviable(this, this.direccion));
		return this.getModoDeEnvio().calcularCosto(this);
	}

	public double precioPedido() {
		return this.precioItems() + this.precioEnvio();
	}

	public void pagarPedido() {
		this.getMetodoDePago().setMonto(this.precioPedido());
		this.getMetodoDePago().procesar_el_pago();
	}

	public void agregarNotaDeCredito(NotaDeCredito notaDeCredito) {
		this.getNotasDeCredito().add(notaDeCredito);
	}

//encapsulamiento de cancelacion en los estados 
//-------------------------------------------
	public void cancelarEnConfirmado() {
		this.reponerStock();
		this.cancelarPriv();
	}

	public void cancelarEnEn_Preparacion() {
		this.generarReembolso(this.precioPedido());
		this.reponerStock();
		this.cancelarPriv();
	}

	public void cancelarEnEnvio() {
		this.generarReembolso(this.precioItems());
		this.cancelarPriv();
	}

//-------------------------------------------

	public void generarReembolso(double montoDeReembolso) {
		this.agregarNotaDeCredito(new NotaDeCredito(montoDeReembolso));
	}

	public void reponerStock() {
		this.getItems().stream().forEach(item -> item.incrementarStock());
	}

	public String getMailCliente() {
		return this.mailCliente;
	}

//Metodos relacionados a subSistemas

	public void notificarCambioACliente(ContextoTipo contexto) {
		this.getMailSender().enviarMail(this.getMailCliente(), "Pedido " + contexto,
				"su pedido se encuentra " + contexto, null);
	}

	public void notificarClienteCupon(double porcientoCupon) {
		this.getMailSender().enviarMail(this.getMailCliente(), "Cupon de Descuento",
				"Por la cancelacion de su pedido se le envia un cupon del " + porcientoCupon + "%", null);
	}

	public void generarComprobanteFizcal() {
		System.out.print("Generando Comprobante fizcal");
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

	public List<ObserverPedido> getSubSistemas() {
		return subSistemas;
	}

	public Contexto getContexto() {
		return contexto;
	}

	public void setContexto(Contexto contexto) {
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

	public ContextoTipo getContextoTipo() {
		return this.getContexto().contexto();
	}

	public MailSender getMailSender() {
		return this.mailSender;
	}

	public LocalDate getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDate fecha) {
		this.fechaEntrega = fecha;
	}

	public Direccion getDireccion() {
		return direccion;
	}

}
