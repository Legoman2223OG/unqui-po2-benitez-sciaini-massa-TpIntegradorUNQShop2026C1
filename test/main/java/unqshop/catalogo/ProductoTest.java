package main.java.unqshop.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductoTest {
	
	private Producto sutProd;
	private AtributoDinamico docAtrD;

	/**
	 * Crea un escenario donde hay un Producto del cual tenemos la siguiente información:
	 * Producto: Jabon
	 * SKU: SKU-0
	 * Marca: ABC
	 * Categoria: Articulos para el Hogar
	 * Peso: 1.4
	 * Productos Quimicos: Etc
	 *
	 * Stock actual: 3
	 * Precio Base: 4000.0
	 * Precio Final: 3600.0
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		docAtrD = mock(AtributoDinamico.class);
		sutProd = new Producto("SKU-0", "Jabon", "ABC", Categoria.HOGAR, 4000.0, 10.0, docAtrD, 3, 1.4);
	}

	/**
	 * Indica que el SKU del producto es "SKU-0".
	 */
	@Test
	void test01_SKUDelProducto() {
		//EXERCISE
		String sku = sutProd.getSku();
		//VERIFY
		Assertions.assertEquals("SKU-0",sku);
	}
	
	/**
	 * Indica que el nombre del producto es "Jabon"
	 */
	@Test
	void test02_NombreDelProducto() {
		//EXERCISE
		String nombre = sutProd.getNombre();
		//VERIFY
		Assertions.assertEquals("Jabon", nombre);
	}
	
	/**
	 * Indica que la marca del producto es "ABC"
	 */
	@Test
	void test03_MarcaDelProducto() {
		//EXERCISE
		String marca = sutProd.getMarca();
		//VERIFY
		Assertions.assertEquals("ABC", marca);
	}
	
	/**
	 * Indica que la categoria del producto es de Hogar.
	 */
	@Test
	void test04_CategoriaDelProducto() {
		//EXERCISE
		Categoria categoria = sutProd.getCategoria();
		//VERIFY
		Assertions.assertEquals(Categoria.HOGAR, categoria);
	}
	
	/**
	 * Indica que el peso del producto es de 1.4.
	 */
	@Test
	void test05_PesoDelProducto() {
		//EXERCISE
		double peso = sutProd.getPeso();
		//VERIFY
		Assertions.assertEquals(1.4, peso);
	}
	
	/**
	 * Indica que la descripcion del producto es:
	 * Producto: Jabon
	 * SKU: SKU-0
	 * Marca: ABC
	 * Categoria: Articulos para el Hogar
	 * Peso: 1.4
	 * Productos Quimicos: Etc
	 *
	 * Stock actual: 3
	 * Precio Base: 4000.0
	 * Precio Final: 3600.0
	 */
	@Test
	void test06_DescripcionDelProducto() {
		//DOC
		when(docAtrD.toString()).thenReturn("Productos Quimicos: Etc\n\n");
		//EXERCISE
		String descripcion = sutProd.getDescripcion();
		//VERIFY
		Assertions.assertEquals("Producto: Jabon\n"
				              + "SKU: SKU-0\n"
				              + "Marca: ABC\n"
				              + "Categoria: Articulos para el Hogar\n"
				              + "Peso: 1.4\n"
				              + "Productos Quimicos: Etc\n\n"
				              + "Stock actual: 3\n"
				              + "Precio Base: 4000.0\n"
				              + "Precio Final: 3600.0", descripcion);
	}
	
	/**
	 * Indica que el precio base del producto es de 4000.0
	 */
	@Test
	void test07_PrecioBaseDelProducto() {
		//EXERCISE
		double precioBase = sutProd.getPrecioBase();
		//VERIFY
		Assertions.assertEquals(4000.0, precioBase);
	}
	
	/**
	 * Indica que el precio final del producto es de 3600.0
	 */
	@Test
	void test08_PrecioFinalDelProducto() {
		//EXERCISE
		double precioFinal = sutProd.getPrecioFinal();
		//VERIFY
		Assertions.assertEquals(4000.0 - (4000.0 * 10.0 / 100.0), precioFinal);
	}
	
	/**
	 * Indica que el stock del producto es de 3.
	 */
	@Test
	void test09_StockDelProducto() {
		//EXERCISE
		int stock = sutProd.getStock();
		//VERIFY
		Assertions.assertEquals(3, stock);
	}
	
	/**
	 * Indica que el stock al settearlo a 6, deberia de tener 6 de stock.
	 * @throws Exception 
	 */
	@Test
	void test10_SettearStockDelProductoA6() throws Exception {
		//EXERCISE
		sutProd.setStock(6);
		int stock = sutProd.getStock();
		//VERIFY
		Assertions.assertEquals(6, stock);
	}
	
	/**
	 * Indica que el descuento aplicado en el producto es de 10.0
	 */
	@Test
	void test11_DescuentoDelProducto() {
		//EXERCISE
		double descuento = sutProd.getDescuento();
		//VERIFY
		Assertions.assertEquals(10.0, descuento);
	}
	
	/**
	 * Indica que el atributo dinamico del producto es el mismo DOC incluido en el setup
	 */
	@Test
	void test12_AtributosDinamicosDelProducto() {
		//EXERCISE
		AtributoDinamico atrD = sutProd.getAtributos();
		//VERIFY
		Assertions.assertEquals(docAtrD, atrD);
	}
	
	/**
	 * Indica que al incrementar el stock del producto, deberia de ser de 4
	 */
	@Test
	void test13_IncrementarStockDelProducto() {
		//EXERCISE
		sutProd.incrementarStock();
		int stock = sutProd.getStock();
		//VERIFY
		Assertions.assertEquals(3 + 1, stock);
	}
	
	/**
	 * Indica que al desincrementar el stock del producto, deberia ser de 2
	 * @throws Exception 
	 */
	@Test
	void test14_DesincrementarStockDelProducto() throws Exception {
		//EXERCISE
		sutProd.decrementarStock();
		int stock = sutProd.getStock();
		//VERIFY
		Assertions.assertEquals(3 - 1, stock);
	}
	
	/**
	 * Indica que el producto si posee stock
	 */
	@Test
	void test15_ElProductoTieneStock() {
		//EXERCISE
		boolean siTieneStock = sutProd.tieneStock();
		//VERIFY
		Assertions.assertTrue(siTieneStock);
	}
	
	/**
	 * Indica que un producto que no tiene atributos dinamicos tiene la siguiente descripcion:
	 * Producto: TV
	 * SKU: SKU-1
	 * Marca: DBC
	 * Categoria: Electronica y Tecnologia
	 * Peso: 6
	 * Stock actual: 1
	 * Precio Base: 10000.0
	 * Precio Final: 9500.0
	 * @throws Exception 
	 */
	@Test
	void test16_ProductoSinAtributosDinamicos() throws Exception {
		//SUT
		Producto sutProdSinAtrD = new Producto("SKU-1", "TV", "DBC", Categoria.ELECTRONICA, 10000.0, 5, 1, 6);
		//EXERCISE
		String descripcion = sutProdSinAtrD.getDescripcion();
		//VERIFY
		Assertions.assertEquals("Producto: TV\n"
	              + "SKU: SKU-1\n"
	              + "Marca: DBC\n"
	              + "Categoria: Electronica y Tecnologia\n"
	              + "Peso: 6.0\n"
	              + "Stock actual: 1\n"
	              + "Precio Base: 10000.0\n"
	              + "Precio Final: 9500.0", descripcion);
	}
	
	/**
	 * Indica que no se puede settear el stock menor a 0.
	 */
	@Test
	void test17_NoSePuedeSettearStockMenorACero() {
		//EXERCISE
		Exception excepcion = assertThrows(Exception.class, ()->{
			sutProd.setStock(-1);
		});
		//VERIFY
		Assertions.assertEquals("No se puede settear el stock a numeros negativos", excepcion.getMessage());
	}
	
	/**
	 * Indica que al preguntarle a un producto que no tiene stock, deberia de devolver false.
	 * @throws Exception 
	 */
	@Test
	void test18_ProductoSinStockDaFalse() throws Exception {
		//SUT
		Producto sutProdSinStock = new Producto("SKU-2", "TV", "FBC", Categoria.ELECTRONICA, 10000.0, 5, 0, 6);
		//EXERCISE
		boolean tieneStock = sutProdSinStock.tieneStock();
		//VERIFY
		Assertions.assertFalse(tieneStock);
	}
	
	/**
	 * Indica que no es posible desincrementar el stock, si el stock es 0.
	 * @throws Exception 
	 */
	@Test
	void test19_NoSePuedeDesincrementarStock() throws Exception {
		//SUT
		Producto sutProdSinStock = new Producto("SKU-2", "TV", "FBC", Categoria.ELECTRONICA, 10000.0, 5, 0, 6);
		//EXERCISE
		Exception excepcion = assertThrows(Exception.class, ()->{
			sutProdSinStock.decrementarStock();
		});
		//VERIFY
		Assertions.assertEquals("No es posible desincrementar el Stock, ya que no hay mas stock disponible para el item", excepcion.getMessage());
	}
	
	/**
	 * Indica que no es posible ingresar un Nombre, SKU o Marca vacía o nula al Producto.
	 */
	@Test
	void test20_NoSePuedeIngresarProductoSKUMarcaVacioONull() {
		//EXERCISE
		
		Exception skuVacio = assertThrows(Exception.class, ()->{
			new Producto("", "TV", "FBC", Categoria.ELECTRONICA, 10000.0, 5, 0, 6);
		});
		
		Exception nombreVacio = assertThrows(Exception.class, ()->{
			new Producto("SKU-02", "", "FBC", Categoria.ELECTRONICA, 10000.0, 5, 0, 6);
		});
		
		Exception marcaVacia = assertThrows(Exception.class, ()->{
			new Producto("SKU-02", "TV", "", Categoria.ELECTRONICA, 10000.0, 5, 0, 6);
		});
		
		//VERIFY
		Assertions.assertEquals("Uno de los parametros String es vacio", skuVacio.getMessage());
		Assertions.assertEquals("Uno de los parametros String es vacio", nombreVacio.getMessage());
		Assertions.assertEquals("Uno de los parametros String es vacio", marcaVacia.getMessage());
	}
	
	/**
	 * Indica que no es posible ingresar numeros negativos para los parametros del producto.
	 */
	@Test
	void test21_NoSePuedenIngresarNumerosNegativosEnProducto() {
		//EXERCISE
		Exception pesoNeg = assertThrows(Exception.class, ()->{
			new Producto("SKU-02", "TV", "FBC", Categoria.ELECTRONICA, 10000.0, 5, 0, -1);
		});
		
		Exception stockNeg = assertThrows(Exception.class, ()->{
			new Producto("SKU-02", "TV", "FBC", Categoria.ELECTRONICA, 10000.0, 5, -1, 6);
		});
		
		Exception descuentoNeg = assertThrows(Exception.class, ()->{
			new Producto("SKU-02", "TV", "FBC", Categoria.ELECTRONICA, 10000.0, -1, 0, 6);
		});
		
		Exception precioNeg = assertThrows(Exception.class, ()->{
			new Producto("SKU-02", "TV", "FBC", Categoria.ELECTRONICA, -1, 5, 0, 6);
		});
		//VERIFY
		Assertions.assertEquals("El peso asignado es negativo", pesoNeg.getMessage());
		Assertions.assertEquals("El stock asignado es negativo", stockNeg.getMessage());
		Assertions.assertEquals("El descuento no puede ser negativo", descuentoNeg.getMessage());
		Assertions.assertEquals("El precio no puede ser negativo", precioNeg.getMessage());
	}
}
