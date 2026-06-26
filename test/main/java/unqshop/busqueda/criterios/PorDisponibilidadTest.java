package main.java.unqshop.busqueda.criterios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.unqshop.catalogo.Categoria;
import main.java.unqshop.catalogo.ItemCatalogo;

class PorDisponibilidadTest {

	private PorDisponibilidad sutPorDis;
	private ItemCatalogo docItCat;
	
	/**
	 * Crea un escenario para un criterio de disponbilidad.
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		sutPorDis = new PorDisponibilidad();
	}

	/**
	 * Indica que al preguntarle a un item si tiene stock, si lo tiene, devuelve true.
	 */
	@Test
	void test01_TieneStock() {
		//DOC
		docItCat = mock(ItemCatalogo.class);
		when(docItCat.tieneStock()).thenReturn(true);
		//EXERCISE
		boolean tieneStock = sutPorDis.isSatisfiedBy(docItCat);
		//VERIFY
		Assertions.assertTrue(tieneStock);
	}
	
	/**
	 * Indica que al preguntarle a un item si tiene stock, si no tiene, devuelve false.
	 */
	@Test
	void test02_NoTieneStock() {
		//DOC
		docItCat = mock(ItemCatalogo.class);
		when(docItCat.tieneStock()).thenReturn(false);
		//EXERCISE
		boolean tieneStock = sutPorDis.isSatisfiedBy(docItCat);
		//VERIFY
		Assertions.assertFalse(tieneStock);
	}

}
