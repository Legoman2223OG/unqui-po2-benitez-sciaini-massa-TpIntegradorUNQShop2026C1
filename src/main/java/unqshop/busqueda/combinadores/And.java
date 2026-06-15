package main.java.unqshop.busqueda.combinadores;

import main.java.unqshop.busqueda.CriterioCatalogo;
import main.java.unqshop.catalogo.Categoria;
import main.java.unqshop.catalogo.ItemCatalogo;

/**
 * Una clase que representa un combinador de criterios AND para la busqueda de items del catalogo.
 *
 * <p>Patrón de diseño: Specification - Composite Specification</p>
 *
 * @author Fabrizio Lautaro Massa
 * @see CriterioCatalogo
 * @see ItemCatalogo
 */
public class And implements CriterioCatalogo {

	private CriterioCatalogo criterio1;
	private CriterioCatalogo criterio2;
	
	/**
	 * Crea una instancia del criterio para combinar dos criterios que deben ser evaluados en sentido lógico AND.
	 * 
	 * @param El primer criterio en CriterioCatalogo.
	 * @param El segundo criterio en CriterioCatalogo.
	 */
	public And(CriterioCatalogo criterio1, CriterioCatalogo criterio2) {
		this.criterio1 = criterio1;
		this.criterio2 = criterio2;
	}
	
	@Override
	public boolean isSatisfiedBy(ItemCatalogo item) {
		// TODO Auto-generated method stub
		return this.criterio1.isSatisfiedBy(item) && this.criterio2.isSatisfiedBy(item);
	}

}
