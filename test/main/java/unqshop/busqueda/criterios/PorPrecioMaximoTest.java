package main.java.unqshop.busqueda.criterios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.unqshop.catalogo.ItemCatalogo;

class PorPrecioMaximoTest {

	private PorPrecioMaximo sutPorPrMax;
	private ItemCatalogo docItCat;
	/**
	 * Crea un escenario con un criterio por precio maximo, donde el criterio debe ser <= 20000.0
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		sutPorPrMax = new PorPrecioMaximo(20000.0);
	}

	/**
	 * Indica que al preguntarle si un item si su prexio maximo esta entre 20000.0, si lo esta, devuelve true.
	 */
	@Test
	void test01_ElItemTienePrecioMaximoEnElRangoDelCriterio() {
		//DOC
		docItCat = mock(ItemCatalogo.class);
		when(docItCat.getPrecioBase()).thenReturn(18000.0);
		//EXERCISE
		boolean estaEnElRango = sutPorPrMax.isSatisfiedBy(docItCat);
		//VERIFY
		Assertions.assertTrue(estaEnElRango);
	}

	/**
	 * Indica que al preguntarle si un item si su prexio maximo esta entre 20000.0, si no lo esta, devuelve false.
	 */
	@Test
	void test01_ElItemNoTienePrecioMaximoEnElRangoDelCriterio() {
		//DOC
		docItCat = mock(ItemCatalogo.class);
		when(docItCat.getPrecioBase()).thenReturn(30000.0);
		//EXERCISE
		boolean estaEnElRango = sutPorPrMax.isSatisfiedBy(docItCat);
		//VERIFY
		Assertions.assertFalse(estaEnElRango);
	}
}
