package main.java.unqshop.Pedido;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.*;

	/*
	 * Clase que representa un pedido.
	 * 
	 * Se utiliza el patron de diseño State para controlar los diferentes comportamientos
	 * dependiendo del contexto(estado) en el que se encuentre, sin que el pedido lo sepa.
	 * 
	 * Se utiliza el patron de diseño Observer para notificar a los subsistemas cuando 
	 * ocurre un cambio de contexto en el pedido, sin que este conozca o dependa
	 * de los subsistemas.
	 * */

public class Pedido {
	private List <ItemCatalogo> items; /*items que pide el cliente en el pedido*/
	private List <ObserverPedido> subSistemas; /*se les notifica cuando hay cambio de estado en el pedido*/
	private Contexto contexto; /*etapa del ciclo de vida del pedido*/
	private Cliente cliente; /*cliente que realizo el pedido*/
	private PagoFacade pagofacade; //Facade de MetodoDePago
	private Envio modoDeEnvio;
	private List <NotaDeCredito> notasDeCredito;
	
	
	
	public Pedido(Cliente cliente,Envio envio) {
		super();
		this.items        = new ArrayList<>();
		this.subSistemas  = new ArrayList<>();
		this.contexto     = new Borrador();
		this.cliente      = cliente;
		this.pagofacade   = new pagoFacade();
		this.modoDeEnvio  = envio;
	}

	/*Operaciones del pedido durante su ciclo de vida*/
	
	public void agregarSubsistema(ObserverPedido subsistema) {
		this.getSubSistemas().add(subsistema);
	}
	
	
	public void confirmar() {/*Valido solo en BORRADOR*/
		this.getContexto().confirmar(this);
	}
	
	public void prepararPedido(MetodoDePago metodoDePago, Envio envio) {/*Valido solo en CONFIRMADO*/
		this.getContexto().prepararPedido(this, metodoDePago, envio);
	}
	
	public void enviar() {/*Valido solo eb EN_PREPARACION*/
		this.getContexto().enviar(this);
	}
	
	public void entregar() {/*Solo valido en ENVIADO*/
		this.getContexto().entregar(this);
	}
	
	public void cancelar() {/*valido en BORRADOR, CONFIRMADO, EN_PREPARACION, ENVIADO*/
		this.getContexto().cancelar(this);
	}
	
	
	public void agregarItem(Pedido pedido, ItemCatalogo item) {
		this.getContexto().agregarItem(this, item);
	}
		
	
	public void quitarItem(Pedido pedido, ItemCatalogo item) {
		this.getContexto().quitarItem(this, item);
	}
		

	
	/*Operaciones internas*/
	
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
		CambioContexto evento = new CambioContexto();
		evento.setAnterior(this.getContextoTipo());
		
		this.setContexto(contexto);
		
		evento.setNuevo(this.getContextoTipo());
		
		this.getSubSistemas().stream().forEach(sub -> sub.actualizar(evento, this));
	}
	
	public void descrementarStock() {
		this.getItems()
			.stream()
			.forEach(item -> item.decrementarStock());
	}
	
	public double precioItems() {
		return this
				.getItems()
				.stream()
				.mapToDouble(item -> item.getPrecioFinal()).sum();
	}
	
	public double precioEnvio() {
		return this.getModoDeEnvio().calcularCosto(this);
	}
	
	public double precioPedido() {/*para pasar como parametro al constructor del MetodoDePago a usar*/
		return this.precioItems() + this.precioEnvio();
	}
	
	public void pagarPedido(MetodoDePago metodoDePago) { 
		this.pagofacade.pagarCon_(metodoDePago);
		
	}
	
	public void agregarNotaDeCredito(NotaDeCredito notaDeCredito) {
		this.getNotasDeCredito().add(notaDeCredito);
	}
	
	public void cancelarEnConfirmado() {
		this.reponerStock();
		this.cancelarPriv();
	}
	
	public void cancelarEnEn_Preparacion() {
		this.generarReembolso(this.precioItems());
		this.reponerStock();
		this.cancelarPriv();
	}
	
	public void cancelarEnEnvio() {
		this.generarReembolso(this.precioItems());
		this.reponerStock();
		this.cancelarPriv();
	}
	
	public void generarReembolso(double montoDeReembolso) {
		this.agregarNotaDeCredito(new NotaDeCredito(montoDeReembolso));
	}
	
	public void reponerStock() {
		this.getItems().stream().forEach(item -> item.incrementarStock());
	}
	
	
	public String getMailCliente() {
		return this.getCliente().getMail();
	}
	
	/*Getters y setters*/

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

	public Cliente getCliente() {
		return cliente;
	}

	public List <NotaDeCredito> getNotasDeCredito() {
		return this.notasDeCredito;
	}
	
	public Envio getModoDeEnvio() {
		return this.modoDeEnvio;
	}
	
	public ContextoTipo getContextoTipo() {
		return this.getContexto().contexto();
	}
	
	
}
