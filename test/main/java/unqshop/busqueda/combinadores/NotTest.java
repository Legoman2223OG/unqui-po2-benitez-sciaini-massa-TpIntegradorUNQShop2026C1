package main.java.unqshop.busqueda.combinadores;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.unqshop.busqueda.CriterioCatalogo;
import main.java.unqshop.catalogo.ItemCatalogo;

class NotTest {

	private Not sutNot;
	private CriterioCatalogo docCC;
	private ItemCatalogo docItCat;
	
	/**
	 * Crea un escenario donde un combinador de criterio NOT se combina con otro criterio diferente.
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		docCC = mock(CriterioCatalogo.class);
		sutNot = new Not(docCC);
	}
	
	/**
	 * Indica que sí el criterio da falso con el item que se paso como argumento, entonces describe True.
	 */
	@Test
	void test01_TrueSiCriterioEsFalse() {
		//DOC
		docItCat = mock(ItemCatalogo.class);
		when(docCC.isSatisfiedBy(docItCat)).thenReturn(false);
		//EXERCISE
		boolean esFalse = sutNot.isSatisfiedBy(docItCat);
		//VERIFY
		Assertions.assertTrue(esFalse);
	}

	/**
	 * Indica que sí el criterio da true con el item que se paso como argumento, entonces describe False.
	 */
	@Test
	void test01_FalseSiCriterioEsTrue() {
		//DOC
		docItCat = mock(ItemCatalogo.class);
		when(docCC.isSatisfiedBy(docItCat)).thenReturn(true);
		//EXERCISE
		boolean esTrue = sutNot.isSatisfiedBy(docItCat);
		//VERIFY
		Assertions.assertFalse(esTrue);
	}
}
