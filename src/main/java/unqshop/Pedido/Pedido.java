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
	
	
	
	public Pedido(List<ItemCatalogo> items, List<ObserverPedido> subSistemas, Contexto contexto, Cliente cliente) {
		super();
		this.items = new ArrayList<>();
		this.subSistemas = new ArrayList<>();
		this.contexto = contexto;
		this.cliente = cliente;
	}

	/*Operaciones del pedido durante su ciclo de vida*/
	
	public void confirmar() {/*Valido solo en BORRADOR*/
		this.getContexto().confirmar(this);
	}
	
	public void prepararEnvio() {/*Valido solo en CONFIRMADO*/
		this.getContexto().prepararEnvio(this);
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
		System.out.println("pedido cancelado, volviendo a BORRADOR");
		this.getItems().clear();
		this.cambiarContexto(contexto);
	}
	
	public void cambiarContexto(Contexto contexto) {
		this.setContexto(contexto);
	}
	
	public void descrementarStock() {
		this.getItems()
			.stream()
			.forEach(item -> item.decrementar());/*TODO: el metodo decrementar puede cambiar*/
	}
	
	public void pagarPedido() {
		double precio = this
						.getItems()
						.stream()
						.mapToDouble(item -> item.getPrecioFinal()).sum();
	}
	
	/*Getters y setters*/

	public List<ItemCatalogo> getItems() {
		return items;
	}

	public void setItems(List<ItemCatalogo> items) {
		this.items = items;
	}

	public List<ObserverPedido> getSubSistemas() {
		return subSistemas;
	}

	public void setSubSistemas(List<ObserverPedido> subSistemas) {
		this.subSistemas = subSistemas;
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

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
}
