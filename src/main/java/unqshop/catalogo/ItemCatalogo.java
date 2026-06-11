package main.java.unqshop.catalogo;

/**
 * Una interfaz que representa un item de un catalogo, cubre el protocolo necesario para consultar
 * informacion crucial sobre los items.
 *
 * <p>Patrón de diseño: Composite - Component</p>
 *
 * @author Fabrizio Lautaro Massa
 * @see Producto
 * @see Paquete
 */
public interface ItemCatalogo {
	
	/**
	 * Describe el identificador SKU del item de catalogo.
	 *
	 * @return El identificador SKU en formato String.
	 */
	public String getSku();
	
	/**
	 * Describe el nombre del item de catalogo.
	 *
	 * @return El nombre del item de catalogo en String.
	 */
	public String getNombre();

	/**
	 * Describe el peso del item de catalogo.
	 *
	 * @return El peso total del item de catalogo en double.
	 */
	public double getPeso();
	
	/**
	 * Describe la descripcion del item de catalogo.
	 *
	 * @return La descripcion del item de catalogo en String.
	 */
	public String getDescripcion();
	
	/**
	 * Describe el precio base del item de catalogo.
	 * 
	 * @return El precio base del item en double.
	 */
	public double getPrecioBase();
	
	/**
	 * Describe el precio final (Se aplica un descuento si es que posee uno) del item de catalogo.
	 * 
	 * @return El precio final del item en double
	 */
	public double getPrecioFinal();
	
	/**
	 * Incrementa el Stock del item.
	 */
	public void incrementarStock();
	
	/**
	 * Desincrementa el Stock del item.
	 * @throws Exception 
	 * 
	 * @exception Si ya no habia Stock disponible.
	 */
	public void decrementarStock() throws Exception;
}
