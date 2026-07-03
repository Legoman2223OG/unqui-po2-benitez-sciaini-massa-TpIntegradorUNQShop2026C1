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
	 * Describe la categoria a la que pertenece el item de catalogo.
	 * 
	 * @return La categoria del item en Categoria.
	 */
	public Categoria getCategoria();
	
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
	
	/**
	 * Indica si el item posee stock.
	 * 
	 * @return La validación de si el item tiene Stock en boolean.
	 */
	public boolean tieneStock();
	
	/**
	 * Indica la cantidad de stock que posee el item.
	 * @return La cantidad de stock del item en int.
	 */
	public int getStock();
	
	/**
	 * Indica si el item posee stock suficiente para comprar las n unidades especificadas.
	 * @param La cantidad de unidades que se quiere comprar del item.
	 * @return La validación de si el item posee suficiente stock para comprar la cantidad especificada.
	 */
	public boolean tieneStock(int n);
}
