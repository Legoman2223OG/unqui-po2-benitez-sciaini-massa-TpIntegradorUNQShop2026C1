package main.java.unqshop.catalogo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Una clase que representa un Paquete del catalogo de la tienda.
 * Un Paquete contiene a otros Paquetes y Productos.
 *
 * <p>Patrón de diseño: Composite — Composite</p>
 *
 * @author Fabrizio Lautaro Massa
 * @see ItemCatalogo
 * @see Paquete
 */
public class Paquete implements ItemCatalogo {
	
	private ArrayList<ItemCatalogo> items = new ArrayList<ItemCatalogo>();
	private String sku,nombre;
	private double descuento;
	private Categoria categoria;
	/**
	 * Crea una instancia de Paquete sin ningun producto u otros paquetes asignados.
	 * @param El SKU del paquete. Debe ser no vacío.
	 * @param El nombre del paquete. Debe ser no vacío.
	 * @param El descuento del paquete. Debe ser >= 0.
	 * @param La categoria del paquete.
	 * @throws Si uno de los datos ingresados no respeta las condiciones necesarias.
	 */
	public Paquete(String sku, String nombre, double descuento, Categoria categoria) throws Exception {
		validarPaquete(sku, nombre, descuento);
		this.sku = sku;
		this.nombre = nombre;
		this.descuento = descuento;
		this.categoria = categoria;
	}
	
	/**
	 * Crea una instancia de Paquete con una cierta cantidad de items del catalogo.
	 * @param El SKU del paquete. Debe ser no vacío.
	 * @param El nombre del paquete. Debe ser no vacío.
	 * @param El descuento del paquete. Debe ser >= 0.
	 * @param La categoria del paquete.
	 * @param Los items del catalogo que se desean incluir en el paquete. No pueden ser null
	 * @throws Si uno de los datos ingresados no respeta las condiciones necesarias.
	 */
	public Paquete(String sku, String nombre, double descuento, Categoria categoria, ItemCatalogo ... items ) throws Exception {
		validarPaquete(sku, nombre, descuento);
		this.sku = sku;
		this.nombre = nombre;
		this.descuento = descuento;
		this.categoria = categoria;
		this.items.addAll(Arrays.asList(items));
	}
	
	/**
	 * Valida que los datos ingresados para la instancia del Paquete sean validos.
	 * @param sku, Debe ser no vacío.
	 * @param nombre, Debe ser no vacío.
	 * @param descuento, Debe ser >= 0.
	 * @throws Exception Si uno de los parametros no cumple las condiciones requeridas.
	 */
	private void validarPaquete(String sku, String nombre, double descuento) throws Exception {
		validarStringNoVacio(sku);
		validarStringNoVacio(nombre);
		validarDescuentoPositivoO0(descuento);
	}

	private void validarDescuentoPositivoO0(double num) throws Exception {
		if(num < 0)
			throw new Exception("El descuento indicado no es positivo o 0");
	}

	private void validarStringNoVacio(String str) throws Exception {
		if(str.isBlank())
			throw new Exception("Uno de los parametros String es vacío");
	}
	

	@Override
	public String getSku() {
		// TODO Auto-generated method stub
		return this.sku;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return this.nombre;
	}

	@Override
	public double getPeso() {
		// TODO Auto-generated method stub
		return items.stream().mapToDouble(i -> i.getPeso()).sum();
	}

	@Override
	public String getDescripcion() {
		// TODO Auto-generated method stub
		String descripcionesItems = "--------------------\n" + this.items.stream().map(i -> i.getDescripcion()).collect(Collectors.joining("\n\n")) + "\n--------------------";
		
		return "Paquete: " + this.nombre + "\n" +
			   "SKU: " + this.sku + "\n" +
			   "Categoria: " + this.categoria.getDescripcion() + "\n" +
			   "Peso: " + this.getPeso() + "\n" + 
			   "Precio Base: " + this.getPrecioBase() + "\n" +
			   "Precio Final: " + this.getPrecioFinal() + "\n" + 
			   "Items Incluidos:\n"+
			   descripcionesItems;
	}

	@Override
	public double getPrecioBase() {
		// TODO Auto-generated method stub
		return items.stream().mapToDouble(i -> i.getPrecioBase()).sum();
	}

	@Override
	public double getPrecioFinal() {
		double descuento = (this.getPrecioBase() * this.descuento) / 100.0;
		return this.getPrecioBase() - descuento;
	}

	@Override
	public void incrementarStock() {
		items.forEach(i -> i.incrementarStock());
	}

	@Override
	public void decrementarStock() throws Exception {
		for(ItemCatalogo i : this.items) {
			i.decrementarStock();
		}
	}

	@Override
	public Categoria getCategoria() {
		// TODO Auto-generated method stub
		return this.categoria;
	}

	@Override
	public boolean tieneStock() {
		// TODO Auto-generated method stub
		return this.items.stream().allMatch(i -> i.tieneStock());
	}
	
	/**
	 * Agrega un nuevo item al paquete.
	 * 
	 * @param El item a agregar en ItemCatalogo.
	 */
	public void agregarItem(ItemCatalogo item) {
		this.items.add(item);
	}
}
