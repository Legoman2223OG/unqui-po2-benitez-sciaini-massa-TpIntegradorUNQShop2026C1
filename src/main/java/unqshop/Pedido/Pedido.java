package main.java.unqshop.Pedido;

public class Pedido {
	private List <CatalogoItem> items; /*items que pide el cliente en el pedido*/
	private List <ObserverPedido> subSistemas; /*se les notifica cuando hay cambio de estado en el pedido*/
	private EstadoPedido estado; /*etapa del ciclo de vida del pedido*/
	private Cliente cliente; /*cliente que realizo el pedido*/
	
	/*Transiciones validas:  
	 * BORRADOR -> CONFIRMADO
	 * BORRADOR -> CANCELADO
	 * 
	 * CONFIRMADO -> EN_PREPARACION
	 * */
	
	public void confirmar() {/*Valido solo en BORRADOR*/
		
	}
	
	public void preparar() {/*Valido solo en CONFIRMADO*/
		
	}
	
	public void enviar() {/*Valido solo eb EN_PREPARACION*/
		
	}
	
	public void entregar() {/*Solo valido en ENVIADO*/
		
	}
	
	public void cancelar() {/*valido en BORRADOR, CONFIRMADO, EN_PREPARACION, ENVIADO*/
		
	}
	
}
