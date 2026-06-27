package main.java.unqshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.unqshop.busqueda.CriterioCatalogo;
import main.java.unqshop.catalogo.ItemCatalogo;

/**
 * Clase Root que se encarga de los pedidos del cliente hacia el sistema de Unq Shop.
 * Un cliente puede realizar pedidos y buscar items del catalogo.
 * 
 * (No usa algún patrón de diseño).
 * 
 * @author Jara Lautaro Benitez, Tomas Sciani, Fabrizio Lautaro Massa
 * @see CriterioCatalogo
 * @see ItemCatalogo
 */
public class UnqShop {
	private List<ItemCatalogo> inventario = new ArrayList<ItemCatalogo>();
	
	/**
	 * Crea una instancia de la clase Root de la UnqShop sin ningun item de catalogo cargado.
	 */
	public UnqShop() {}
	
	/**
	 * Crea una instancia de la clase Root de la UnqShop con cierta cantidad de items del catalogo.
	 * @param Los items que se desean cargar, Ninguno puede ser null.
	 */
	public UnqShop(ItemCatalogo ... items) {
		this.inventario.addAll(Arrays.asList(items));
	}
	
	/**
	 * Agrega un item al catalogo de la UnqShop.
	 * @param Un item a agregar al catalogo, no puede ser null.
	 */
	public void agregarItem(ItemCatalogo item) {
		this.inventario.add(item);
	}
	
	/**
	 * Busca items del catalogo que cumplan con el criterio indicado por el cliente.
	 * @param El criterio dado por el cliente, no puede ser null.
	 * @return La lista de los items que cumplen con el criterio del cliente, si ninguno coincide, devuelve una lista vacia.
	 */
	public List<ItemCatalogo> buscarItems(CriterioCatalogo criterio){
		return inventario.stream().filter(i -> criterio.isSatisfiedBy(i)).toList();
	}
}
