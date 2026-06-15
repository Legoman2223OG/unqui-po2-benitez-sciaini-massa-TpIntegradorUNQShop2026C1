package main.java.unqshop.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaqueteTest {
	
	private Paquete sutPaq;
	private Producto docProd;
	private Paquete docPaq;
	
	/**
	 * Crea un escenario donde hay un paquete que cuenta con la siguiente información:
	 * Paquete: Escritorio con computadora
	 * SKU: SKU-09
	 * Categoria: Electronica y Tecnologia
	 * Peso: 102.2
	 * Precio Base: 200000.0
	 * Precio Final: 180000.0
     * Items Incluidos:
	 * --------------------
	 * Producto: Gabinete Gamer
	 * SKU: SKU-100
	 * Marca: OG
	 * Categoria: Electronica y Tecnologia
	 * Peso: 50.0
	 * Procesador: Pentium
	 * Memoria RAM: 4GB
	 * Almacenamiento: 1TB
	 * 
	 * Stock actual: 300
	 * Precio Base: 100000.0
	 * Precio Final: 90000.0
	 * 
	 * Paquete: Escritorio, Teclado y Monitor
	 * SKU: SKU-30
	 * Categoria: Electronica y Tecnologia
	 * Peso: 52.2
	 * Precio Base: 100000.0
	 * Precio Final: 90000.0
	 * Items Incluidos:
	 * --------------------
	 * Producto: Monitor
	 * SKU: SKU-149
	 * Marca: OG
	 * Categoria: Electronica y Tecnologia
	 * Peso: 30.0
	 * Stock actual: 290
	 * Precio Base: 30000.0
	 * Precio Final: 27000.0
	 * 
	 * Producto: Teclado
	 * SKU: SKU-199
	 * Marca: OG
	 * Categoria: Electronica y Tecnologia
	 * Peso: 10.0
	 * Stock actual: 120
	 * Precio Base: 50000.0
	 * Precio Final: 45000.0
	 * 
	 * Producto: Escritorio
	 * SKU: SKU-130
	 * Marca: OG
	 * Categoria: Articulos para el Hogar
	 * Peso: 12.2
	 * Stock actual: 30
	 * Precio Base: 20000.0
	 * Precio Final: 18000.0
	 * 
	 * --------------------
	 * 
	 * --------------------
	 */
	@BeforeEach
	void setUp() throws Exception {
		//DOC
		docProd = mock(Producto.class);
		docPaq = mock(Paquete.class);
		//SUT
		sutPaq = new Paquete("SKU-09", "Escritorio con computadora", 10.0, Categoria.ELECTRONICA, docProd, docPaq);
	}

	/**
	 * Indica que el SKU Del paquete es SKU-09
	 */
	@Test
	void test01_SKUDelPaquete() {
		//EXERCISE
		String sku = sutPaq.getSku();
		//VERIFY
		Assertions.assertEquals("SKU-09", sku);
	}

	/**
	 * Indica que el nombre del paquete es "Escritorio con computadora"
	 */
	@Test
	void test02_NombreDelPaquete() {
		//EXERCISE
		String nombre = sutPaq.getNombre();
		//VERIFY
		Assertions.assertEquals("Escritorio con computadora", nombre);
	}
	
	/**
	 * Indica que el peso del paquete es de 102.2
	 */
	@Test
	void test03_PesoDelPaquete() {
		//DOC
		when(docProd.getPeso()).thenReturn(50.0);
		when(docPaq.getPeso()).thenReturn(52.2);
		//EXERCISE
		double peso = sutPaq.getPeso();
		//VERIFY
		Assertions.assertEquals(102.2, peso);
	}
	
	/**
	 * Indica que la descripcion del paquete es:
	 * Paquete: Escritorio con computadora
	 * SKU: SKU-09
	 * Categoria: Electronica y Tecnologia
	 * Peso: 102.2
	 * Precio Base: 200000.0
	 * Precio Final: 180000.0
     * Items Incluidos:
	 * --------------------
	 * Producto: Gabinete Gamer
	 * SKU: SKU-100
	 * Marca: OG
	 * Categoria: Electronica y Tecnologia
	 * Peso: 50.0
	 * Procesador: Pentium
	 * Memoria RAM: 4GB
	 * Almacenamiento: 1TB
	 * 
	 * Stock actual: 300
	 * Precio Base: 100000.0
	 * Precio Final: 90000.0
	 * 
	 * Paquete: Escritorio, Teclado y Monitor
	 * SKU: SKU-30
	 * Categoria: Electronica y Tecnologia
	 * Peso: 52.2
	 * Precio Base: 100000.0
	 * Precio Final: 90000.0
	 * Items Incluidos:
	 * --------------------
	 * Producto: Monitor
	 * SKU: SKU-149
	 * Marca: OG
	 * Categoria: Electronica y Tecnologia
	 * Peso: 30.0
	 * Stock actual: 290
	 * Precio Base: 30000.0
	 * Precio Final: 27000.0
	 * 
	 * Producto: Teclado
	 * SKU: SKU-199
	 * Marca: OG
	 * Categoria: Electronica y Tecnologia
	 * Peso: 10.0
	 * Stock actual: 120
	 * Precio Base: 50000.0
	 * Precio Final: 45000.0
	 * 
	 * Producto: Escritorio
	 * SKU: SKU-130
	 * Marca: OG
	 * Categoria: Articulos para el Hogar
	 * Peso: 12.2
	 * Stock actual: 30
	 * Precio Base: 20000.0
	 * Precio Final: 18000.0
	 * 
	 * --------------------
	 * 
	 * --------------------
	 */
	@Test
	void test04_DescripcionDelPaquete() {
		//DOC
		when(docProd.getPeso()).thenReturn(50.0);
		when(docProd.getPrecioBase()).thenReturn(100000.0);
		when(docPaq.getPeso()).thenReturn(52.2);
		when(docPaq.getPrecioBase()).thenReturn(100000.0);
		when(docProd.getDescripcion()).thenReturn(
				   "Producto: Gabinete Gamer\n" +
				   "SKU: SKU-100\n" +
				   "Marca: OG\n" +
				   "Categoria: Electronica y Tecnologia\n" +
				   "Peso: 50.0\n" +
				   "Procesador: Pentium\n" +
				   "Memoria RAM: 4GB\n" +
				   "Almacenamiento: 1TB\n\n" +
				   "Stock actual: 300\n" +
				   "Precio Base: 100000.0\n" +
				   "Precio Final: 90000.0");
		when(docPaq.getDescripcion()).thenReturn(
				   "Paquete: Escritorio, Teclado y Monitor\n" +
				   "SKU: SKU-30\n" +
				   "Categoria: Electronica y Tecnologia\n" +
				   "Peso: 52.2\n" +
				   "Precio Base: 100000.0\n" +
				   "Precio Final: 90000.0\n" +
				   "Items Incluidos:\n" +
				   "--------------------\n" +
				   "Producto: Monitor\n" +
				   "SKU: SKU-149\n" +
				   "Marca: OG\n" +
				   "Categoria: Electronica y Tecnologia\n" +
				   "Peso: 30.0\n" +
				   "Stock actual: 290\n" +
				   "Precio Base: 30000.0\n" +
				   "Precio Final: 27000.0\n\n" +
				   "Producto: Teclado\n" +
				   "SKU: SKU-199\n" +
				   "Marca: OG\n" +
				   "Categoria: Electronica y Tecnologia\n" +
				   "Peso: 10.0\n" +
				   "Stock actual: 120\n" +
				   "Precio Base: 50000.0\n" +
				   "Precio Final: 45000.0\n\n" +
				   "Producto: Escritorio\n" +
				   "SKU: SKU-130\n" +
				   "Marca: OG\n" +
				   "Categoria: Articulos para el Hogar\n" +
				   "Peso: 12.2\n" +
				   "Stock actual: 120\n" +
				   "Precio Base: 20000.0\n" +
				   "Precio Final: 18000.0\n\n" +
				   "--------------------");
		//EXERCISE
		String descripcion = sutPaq.getDescripcion();
		//VERIFY
		Assertions.assertEquals(
				   "Paquete: Escritorio con computadora\n" +
				   "SKU: SKU-09\n" +
				   "Categoria: Electronica y Tecnologia\n" +
				   "Peso: 102.2\n" +
				   "Precio Base: 200000.0\n" +
				   "Precio Final: 180000.0\n" +
				   "Items Incluidos:\n" +
				   "--------------------\n" +
				   "Producto: Gabinete Gamer\n" +
				   "SKU: SKU-100\n" +
				   "Marca: OG\n" +
				   "Categoria: Electronica y Tecnologia\n" +
				   "Peso: 50.0\n" +
				   "Procesador: Pentium\n" +
				   "Memoria RAM: 4GB\n" +
				   "Almacenamiento: 1TB\n\n" +
				   "Stock actual: 300\n" +
				   "Precio Base: 100000.0\n" +
				   "Precio Final: 90000.0\n\n"+
				   "Paquete: Escritorio, Teclado y Monitor\n" +
				   "SKU: SKU-30\n" +
				   "Categoria: Electronica y Tecnologia\n" +
				   "Peso: 52.2\n" +
				   "Precio Base: 100000.0\n" +
				   "Precio Final: 90000.0\n" +
				   "Items Incluidos:\n" +
				   "--------------------\n" +
				   "Producto: Monitor\n" +
				   "SKU: SKU-149\n" +
				   "Marca: OG\n" +
				   "Categoria: Electronica y Tecnologia\n" +
				   "Peso: 30.0\n" +
				   "Stock actual: 290\n" +
				   "Precio Base: 30000.0\n" +
				   "Precio Final: 27000.0\n\n" +
				   "Producto: Teclado\n" +
				   "SKU: SKU-199\n" +
				   "Marca: OG\n" +
				   "Categoria: Electronica y Tecnologia\n" +
				   "Peso: 10.0\n" +
				   "Stock actual: 120\n" +
				   "Precio Base: 50000.0\n" +
				   "Precio Final: 45000.0\n\n" +
				   "Producto: Escritorio\n" +
				   "SKU: SKU-130\n" +
				   "Marca: OG\n" +
				   "Categoria: Articulos para el Hogar\n" +
				   "Peso: 12.2\n" +
				   "Stock actual: 120\n" +
				   "Precio Base: 20000.0\n" +
				   "Precio Final: 18000.0\n\n" +
				   "--------------------\n" +
				   "--------------------", descripcion);
	}
	
	/**
	 * Indica que el precio base del paquete es de 200000.0
	 */
	@Test
	void test05_PrecioBasePaquete() {
		//DOC
		when(docProd.getPrecioBase()).thenReturn(100000.0);
		when(docPaq.getPrecioBase()).thenReturn(100000.0);
		//EXERCISE
		double precioBase = sutPaq.getPrecioBase();
		//VERIFY
		Assertions.assertEquals(100000.0 + 100000.0, precioBase);
	}
	
	/**
	 * Indica que el precio final del paquete es de 180000.0
	 */
	@Test
	void test06_PrecioFinalPaquete() {
		//DOC
		when(docProd.getPrecioBase()).thenReturn(100000.0);
		when(docPaq.getPrecioBase()).thenReturn(100000.0);
		//EXERCISE
		double precioFinal = sutPaq.getPrecioFinal();
		//VERIFY
		Assertions.assertEquals((100000.0 + 100000.0) - (((100000.0 + 100000.0) * 10.0) / 100.0), precioFinal);
	}
	
	/**
	 * Indica que al incrementar el stock del paquete, los productos del paquete incrementan su stock.
	 */
	@Test
	void test07_IncrementarStockPaquete() {
		//EXERCISE
		sutPaq.incrementarStock();
		//VERIFY
		verify(docProd, times(1)).incrementarStock();
		verify(docPaq, times(1)).incrementarStock();
	}
	
	/**
	 * Indica que al desincrementar el stock del paquete, los productos del paquete desincrementan su stock por 1.
	 * @throws Exception 
	 */
	@Test
	void test08_DesincrementarStockPaquete() throws Exception {
		//EXERCISE
		sutPaq.decrementarStock();
		//VERIFY
		verify(docProd,times(1)).decrementarStock();
		verify(docPaq,times(1)).decrementarStock();
	}
	
	/**
	 * Indica que al preguntarle la categoria al paquete, su categoria es de ELECTRONICA
	 */
	@Test
	void test09_CategoriaDePaquete() {
		//EXERCISE
		Categoria categoria = sutPaq.getCategoria();
		//VERIFY
		Assertions.assertEquals(Categoria.ELECTRONICA, categoria);
	}
	
	/**
	 * Indica que al preguntarle si tiene stock al paquete, indica que sí
	 */
	@Test
	void test10_TieneStockPaquete() {
		//DOC
		when(docProd.tieneStock()).thenReturn(true);
		when(docPaq.tieneStock()).thenReturn(true);
		//EXERCISE
		boolean tieneStock = sutPaq.tieneStock();
		//VERIFY
		Assertions.assertTrue(tieneStock);
	}
	
	/**
	 * Indica que al agregar un nuevo item, si incrementamos el stock del paquete, entonces el nuevo item deberia de incrementar su stock tambien.
	 */
	@Test
	void test11_AgregarNuevoItemYIncrementarStockPaquete() {
		//DOC
		ItemCatalogo docItem = mock(ItemCatalogo.class);
		//EXERCISE
		sutPaq.agregarItem(docItem);
		sutPaq.incrementarStock();
		//VERIFY
		verify(docItem, times(1)).incrementarStock();
	}
	
	/**
	 * Indica que al tratar de instanciar un paquete, no podemos dejar vacios lo que seria su SKU ni su nombre.
	 */
	@Test
	void test12_NoSePuedePonerNombreNiSKUVaciosEnPaquete() {
		//EXERCISE
		Exception nombreVacio = assertThrows(Exception.class, ()->{
			new Paquete("SKU-09", "", 10.0, Categoria.ELECTRONICA);
		});
		
		Exception skuVacio = assertThrows(Exception.class, ()->{
			new Paquete("SKU-09", "", 10.0, Categoria.ELECTRONICA);
		});
		
		//VERIFY
		Assertions.assertEquals("Uno de los parametros String es vacío", nombreVacio.getMessage());
		Assertions.assertEquals("Uno de los parametros String es vacío", skuVacio.getMessage());
	}
	
	/**
	 * Indica que al tratar de instanciar un paquete, no podemos poner un descuento negativo.
	 */
	@Test
	void test13_NoSePuedePonerDescuentoNegativoEnPaquete() {
		//EXERCISE
		Exception descuentoNegativo = assertThrows(Exception.class, ()->{
			new Paquete("SKU-09", "Escritorio con computadora", -1, Categoria.ELECTRONICA);
		});
		
		//VERIFY
		Assertions.assertEquals("El descuento indicado no es positivo o 0", descuentoNegativo.getMessage());
	}
	
	/**
	 * Indica que al instanciar un paquete sin items incluidos, su precio base es de 0.
	 * @throws Exception 
	 */
	@Test
	void test14_PaqueteSinItems() throws Exception {
		//EXERCISE
		Paquete sutPaqSinItems = new Paquete("SKU-09", "Escritorio con computadora", 10.0, Categoria.ELECTRONICA);
		double precioBase = sutPaqSinItems.getPrecioBase();
		//VERIFY
		Assertions.assertEquals(0, precioBase);
	}
}
