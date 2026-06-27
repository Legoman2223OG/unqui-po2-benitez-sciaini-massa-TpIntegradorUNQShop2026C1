package main.java.unqshop.busqueda.criterios;

import main.java.unqshop.busqueda.CriterioCatalogo;
import main.java.unqshop.catalogo.Categoria;
import main.java.unqshop.catalogo.ItemCatalogo;

/**
 * Una clase que representa un criterio por categoria para la busqueda de items del catalogo.
 *
 * <p>Patrón de diseño: Composite - Leaf</p>
 *
 * @author Fabrizio Lautaro Massa
 * @see CriterioCatalogo
 * @see Categoria
 * @see ItemCatalogo
 */
public class PorCategoria implements CriterioCatalogo {

	private Categoria categoria;
	
	/**
	 * Crea una instancia del criterio para buscar según una categoria en particular.
	 * 
	 * @param La categoria del item que se quiere evaluar en Categoria.
	 */
	public PorCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	@Override
	public boolean isSatisfiedBy(ItemCatalogo item) {
		// TODO Auto-generated method stub
		return item.getCategoria() == this.categoria;
	}

}
