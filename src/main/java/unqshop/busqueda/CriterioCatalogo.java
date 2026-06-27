package main.java.unqshop.busqueda;

import main.java.unqshop.catalogo.ItemCatalogo;

/**
 * Una interfaz que representa el criterio para determinar si un item del catalogo cumple con los criterios indicados.
 * Esta interfaz permite hacer consultas anidadas usando "AND", "OR" y "NOT" combinandolas con diferentes criterios para items de catalogo.
 *
 * <p>Patrón de diseño: Composite - Component</p>
 *
 * @author Fabrizio Lautaro Massa
 * 
 * Criterios de busqueda:
 * 
 * @see PorNombre
 * @see PorPrecioMaximo
 * @see PorCategoria
 * @see PorDisponibilidad
 * 
 * Combinadores logicos:
 * 
 * @see And
 * @see Or
 * @see Not
 */
public interface CriterioCatalogo {
	/**
	 * Indica si el item cumple con la condición necesaria según el criterio elegido.
	 * 
	 * @param El item del cual se desea saber si cumple con el criterio.
	 * 
	 * @return La confirmación de si el item cumple con el criterio en Boolean.
	 */
	public boolean isSatisfiedBy(ItemCatalogo item);
}
