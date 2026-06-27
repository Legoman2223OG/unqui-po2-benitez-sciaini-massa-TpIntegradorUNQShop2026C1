package main.java.unqshop.busqueda.criterios;

import main.java.unqshop.busqueda.CriterioCatalogo;
import main.java.unqshop.catalogo.ItemCatalogo;

/**
 * Una clase que representa un criterio de verificar si el item tiene stock o no.
 *
 * <p>Patrón de diseño: Composite - Leaf</p>
 *
 * @author Fabrizio Lautaro Massa
 * @see CriterioCatalogo
 * @see ItemCatalogo
 */
public class PorDisponibilidad implements CriterioCatalogo {
	
	@Override
	public boolean isSatisfiedBy(ItemCatalogo item) {
		// TODO Auto-generated method stub
		return item.tieneStock();
	}

}
