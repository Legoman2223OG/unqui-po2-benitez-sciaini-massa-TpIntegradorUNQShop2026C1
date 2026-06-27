package main.java.unqshop.busqueda.criterios;

import main.java.unqshop.busqueda.CriterioCatalogo;
import main.java.unqshop.catalogo.ItemCatalogo;

/**
 * Una clase que representa un criterio por precio maximo para la busqueda de items del catalogo.
 * Se considera valido un item que tenga un precio maximo base menor o igual al valor indicado.
 *
 * <p>Patrón de diseño: Composite - Leaf</p>
 *
 * @author Fabrizio Lautaro Massa
 * @see CriterioCatalogo
 * @see ItemCatalogo
 */
public class PorPrecioMaximo implements CriterioCatalogo {
	
	private double precioMaximo;
	
	/**
	 * Crea una instancia del criterio para buscar según precio maximo en particular.
	 * Se busca items que tengan un precio menor o igual al valor indicado.
	 * 
	 * @param El precio maximo que se desea buscar en double.
	 */
	public PorPrecioMaximo(double precioMaximo) {
		this.precioMaximo = precioMaximo;
	}
	
	@Override
	public boolean isSatisfiedBy(ItemCatalogo item) {
		// TODO Auto-generated method stub
		return item.getPrecioBase() <= this.precioMaximo;
	}

}
