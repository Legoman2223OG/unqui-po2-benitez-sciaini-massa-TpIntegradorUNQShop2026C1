package main.java.unqshop.busqueda.combinadores;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.unqshop.busqueda.CriterioCatalogo;
import main.java.unqshop.catalogo.ItemCatalogo;

class OrTest {

	private Or sutOr;
	private CriterioCatalogo docCC1,docCC2;
	private ItemCatalogo docItCat;
	
	/**
	 * Crea un escenario donde un combinador de criterio OR se combina con dos criterios diferentes.
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		docCC1 = mock(CriterioCatalogo.class);
		docCC2 = mock(CriterioCatalogo.class);
		sutOr = new Or(docCC1, docCC2);
	}

	/**
	 * Indica que si uno de los criterios es satisfecho, entonces devuelve True.
	 */
	@Test
	void test01_TrueSiUnoDeLosCriteriosEsTrue() {
		//DOC
		docItCat = mock(ItemCatalogo.class);
		when(docCC1.isSatisfiedBy(docItCat)).thenReturn(true);
		when(docCC2.isSatisfiedBy(docItCat)).thenReturn(false);
		//EXERCISE
		boolean esTrueUnoDeLosDos = sutOr.isSatisfiedBy(docItCat);
		//VERIFY
		Assertions.assertTrue(esTrueUnoDeLosDos);
	}

	/**
	 * Indica que si los dos criterios no son satisfechos, entonces devuelve False.
	 */
	@Test
	void test01_FalseSiLosDosCriteriosSonFalse() {
		//DOC
		docItCat = mock(ItemCatalogo.class);
		when(docCC1.isSatisfiedBy(docItCat)).thenReturn(false);
		when(docCC2.isSatisfiedBy(docItCat)).thenReturn(false);
		//EXERCISE
		boolean esFalseLosDos = sutOr.isSatisfiedBy(docItCat);
		//VERIFY
		Assertions.assertFalse(esFalseLosDos);
	}
}
