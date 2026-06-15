package main.java.unqshop.busqueda.criterios;

import main.java.unqshop.busqueda.CriterioCatalogo;
import main.java.unqshop.catalogo.ItemCatalogo;
import main.java.unqshop.catalogo.Paquete;
import main.java.unqshop.catalogo.Producto;

/**
 * Una clase que representa un criterio por nombre para la busqueda de items del catalogo.
 *
 * <p>Patrón de diseño: Specification - Concrete Specification</p>
 *
 * @author Fabrizio Lautaro Massa
 * @see CriterioCatalogo
 * @see ItemCatalogo
 */
public class PorNombre implements CriterioCatalogo {
	
	private String nombre;
	
	/**
	 * Crea una instancia del criterio para buscar según un nombre en particular.
	 * 
	 * @param El nombre que se quiere buscar en String.
	 */
	public PorNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public boolean isSatisfiedBy(ItemCatalogo item) {
		return item.getNombre().equals(this.nombre);
	}

}
