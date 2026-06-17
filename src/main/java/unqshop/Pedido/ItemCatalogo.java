package main.java.unqshop.Pedido;

public interface ItemCatalogo {/*interface solo para que no salte error por momento para usar los metodos de List y stream sin equivocasion*/

	void decrementarStock();
	
	double getPrecioFinal();

	void incrementarStock();
}
