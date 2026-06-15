package main.java.unqshop.catalogo;

/**
 * Una clase que representa un Producto del catalogo de la tienda.
 * Un Producto puede contener otros atributos más alla de los default si es necesario.
 * Se puede aplicarle un descuento al producto.
 * Un producto tiene un stock disponible.
 *
 * <p>Patrón de diseño: Composite — Leaf</p>
 *
 * @author Fabrizio Lautaro Massa
 * @see ItemCatalogo
 * @see Paquete
 */
public class Producto implements ItemCatalogo {
	
	private String sku,nombre,marca;
	private Categoria categoria;
	private double precio, descuento, peso;
	private AtributoDinamico atributos;
	private int stock;
	
	/**
	 * Crea una instancia de Producto con atributos extra.
	 * 
	 * @param El SKU del producto, no puede ser vacío.
	 * @param El nombre del producto, no puede ser vacío.
	 * @param La marca del producto, no puede ser vacía.
	 * @param La categoria del producto.
	 * @param El precio del producto, debe ser > 0.
	 * @param El descuento aplicado en el producto, debe ser >= 0.
	 * @param Los atributos dinamicos del producto.
	 * @param El stock actual del producto, debe ser >= 0.
	 * @param El peso del producto, debe ser >= 0.
	 * @throws Exception Si uno de los parametros ingresados no respetan las condiciones necesarias.
	 */
	public Producto(String sku, String nombre, String marca, Categoria categoria, double precio, double descuento, AtributoDinamico atributos, int stock, double peso) throws Exception {
		validarProducto(sku, nombre, marca, precio, descuento, stock, peso);
		this.sku = sku;
		this.nombre = nombre;
		this.marca = marca;
		this.categoria = categoria;
		this.precio = precio;
		this.descuento = descuento;
		this.atributos = atributos;
		this.stock = stock;
	}
	
	/**
	 * Crea una instancia de Producto sin atributos extra.
	 * 
	 * @param El SKU del producto, no puede ser vacío.
	 * @param El nombre del producto, no puede ser vacío.
	 * @param La marca del producto, no puede ser vacía.
	 * @param La categoria del producto.
	 * @param El precio del producto, debe ser > 0.
	 * @param El descuento aplicado en el producto, debe ser >= 0.
	 * @param El stock actual del producto, debe ser >= 0.
	 * @param El peso del producto, debe ser >= 0.
	 * @throws Exception Si uno de los parametros ingresados no respetan las condiciones necesarias.
	 */
	public Producto(String sku, String nombre, String marca, Categoria categoria, double precio, double descuento, int stock, double peso) throws Exception {
		validarProducto(sku, nombre, marca, precio, descuento, stock, peso);
		this.sku = sku;
		this.nombre = nombre;
		this.marca = marca;
		this.categoria = categoria;
		this.precio = precio;
		this.descuento = descuento;
		this.atributos = new AtributoDinamico();
		this.stock = stock;
	}
	
	/**
	 * Valida que los datos ingresados en el producto sean validos.
	 * @param sku, no puede ser vacío.
	 * @param nombre, no puede ser vacío.
	 * @param marca, no puede ser vacío.
	 * @param precio, Debe ser > 0.
	 * @param descuento, Debe ser >= 0.
	 * @param stock, Debe ser >= 0.
	 * @param peso, Debe ser >= 0.
	 * @throws Exception Si uno de los parametros es un String vacio o si uno de los parametros numericos es negativo o 0.
	 */
	private void validarProducto(String sku, String nombre, String marca, double precio, double descuento, int stock, double peso) throws Exception {
		validarStringNoVacio(sku);
		validarStringNoVacio(nombre);
		validarStringNoVacio(marca);
		validarPrecioPositivo(precio);
		validarDescuentoPositivoO0(descuento);
		validarStockAsignado(stock);
		validarPesoAsignado(peso);
	}
	
	private void validarPesoAsignado(double num) throws Exception {
		if (num < 0)
			throw new Exception("El peso asignado es negativo");
	}

	private void validarStockAsignado(int num) throws Exception {
		if (num < 0)
			throw new Exception("El stock asignado es negativo");
	}

	private void validarDescuentoPositivoO0(double num) throws Exception {
		if(num < 0)
			throw new Exception("El descuento no puede ser negativo");
	}

	private void validarPrecioPositivo(double num) throws Exception {
		if(num <= 0)
			throw new Exception("El precio no puede ser negativo o 0");
	}

	private void validarStringNoVacio(String st) throws Exception {
		if(st.isBlank())
			throw new Exception("Uno de los parametros String es vacio");
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

	public String getMarca() {
		// TODO Auto-generated method stub
		return this.marca;
	}

	public Categoria getCategoria() {
		// TODO Auto-generated method stub
		return this.categoria;
	}

	@Override
	public double getPeso() {
		// TODO Auto-generated method stub
		return this.peso;
	}

	@Override
	public String getDescripcion() {
		// TODO Auto-generated method stub
		return "Producto: " + this.nombre + "\n" +
			   "SKU: " + this.sku + "\n" +
			   "Marca: " + this.marca + "\n" +
			   "Categoria: " + this.categoria + "\n" +
			   "Peso: " + this.peso + "\n" +
			   this.atributos +
			   "Stock actual: " + this.stock + "\n" +
			   "Precio Base: " + this.precio + "\n" +
			   "Precio Final: " + this.getPrecioFinal();
	}

	@Override
	public double getPrecioBase() {
		// TODO Auto-generated method stub
		return this.precio;
	}

	@Override
	public double getPrecioFinal() {
		double descuento = (this.precio * this.descuento) / 100;
		return this.precio - descuento;
	}

	public int getStock() {
		return this.stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getDescuento() {
		return descuento;
	}

	public AtributoDinamico getAtributos() {
		return atributos;
	}

	@Override
	public void incrementarStock() {
		++this.stock;
	}

	@Override
	public void decrementarStock() throws Exception {
		validarStockDisponible();
		--this.stock;
	}

	private void validarStockDisponible() throws Exception {
		if(this.stock == 0)
			throw new Exception("No es posible desincrementar el Stock, ya que no hay mas stock disponible para el item");
	}

	@Override
	public boolean tieneStock() {
		return this.stock > 0;
	}
}
