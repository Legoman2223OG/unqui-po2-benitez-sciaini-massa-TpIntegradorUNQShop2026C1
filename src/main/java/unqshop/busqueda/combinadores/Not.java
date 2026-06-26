package main.java.unqshop.busqueda.combinadores;

import main.java.unqshop.busqueda.CriterioCatalogo;
import main.java.unqshop.catalogo.ItemCatalogo;
/**
 * Una clase que representa un combinador de criterio NOT para la busqueda de items del catalogo.
 *
 * <p>Patrón de diseño: Specification - Composite Specification</p>
 *
 * @author Fabrizio Lautaro Massa
 * @see CriterioCatalogo
 * @see ItemCatalogo
 */
public class Not implements CriterioCatalogo {

	private CriterioCatalogo criterio;
	/**
	 * Crea una instancia del criterio para buscar un criterio de un item negandolo con el operador lógico NOT.
	 * 
	 * @param El criterio que se desea negar con NOT en CriterioCatalogo.
	 */
	public Not(CriterioCatalogo criterio) {
		this.criterio = criterio;
	}
	
	@Override
	public boolean isSatisfiedBy(ItemCatalogo item) {
		return !this.criterio.isSatisfiedBy(item);
	}

}
