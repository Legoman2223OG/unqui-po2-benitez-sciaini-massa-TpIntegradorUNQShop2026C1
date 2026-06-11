package main.java.unqshop.Pedido;
import java.util.List;

public class Pedido {
	private List <CatalogoItem> items; /*items que pide el cliente en el pedido*/
	private List <ObserverPedido> subSistemas; /*se les notifica cuando hay cambio de estado en el pedido*/
	private Contexto contexto; /*etapa del ciclo de vida del pedido*/
	private Cliente cliente; /*cliente que realizo el pedido*/
	
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
	
	
	public void agregarItem(Pedido pedido, CatalogoItem item) {
		this.getContexto().agregarItem(this, item);
	}
		
	
	public void quitarItem(Pedido pedido, CatalogoItem item) {
		this.getContexto().quitarItem(this, item);
	}
	

	
	/*Operaciones internas*/
	
	public void agregarItemPriv(CatalogoItem item) {
		this.getItems().add(item);
		
	}
	
	public void quitarItemPriv(CatalogoItem item) {
		this.getItems().remove(item);
	}
	
	public void cancelarPriv() {/*al cancelar un pedido se reinicia los items del pedido y se vuelve al BORRADOR*/
		System.out.println("pedido cancelado");
		this.getItems().clean();
		this.cambiarContexto(contexto);/*por implementar*/
	}
	
	public void cambiarContexto(Contexto contexto) {
		this.setContexto(contexto);
	}
	
	/*Getters y setters*/

	public List<CatalogoItem> getItems() {
		return items;
	}

	public void setItems(List<CatalogoItem> items) {
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
