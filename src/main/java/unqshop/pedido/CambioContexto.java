package main.java.unqshop.pedido;
/*
 * clase la cual guarda el cambio de contexto de un pedido
 * guardando su estado anterior y nuevo. 
 * */
public class CambioContexto {
	ContextoTipo anterior;
	ContextoTipo nuevo;
	
	public CambioContexto() {
		super();
		
	}

	public ContextoTipo getAnterior() {
		return anterior;
	}

	public void setAnterior(ContextoTipo anterior) {
		this.anterior = anterior;
	}

	public ContextoTipo getNuevo() {
		return nuevo;
	}

	public void setNuevo(ContextoTipo nuevo) {
		this.nuevo = nuevo;
	}
	
	
	
	
}
