package main.java.unqshop.busqueda.combinadores;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.unqshop.busqueda.CriterioCatalogo;
import main.java.unqshop.catalogo.ItemCatalogo;

class AndTest {
	
	private And sutAnd;
	private CriterioCatalogo docCC1,docCC2;
	private ItemCatalogo docItCat;
	
	/**
	 * Crea un escenario donde un combinador de criterio AND se combina con dos criterios diferentes.
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		docCC1 = mock(CriterioCatalogo.class);
		docCC2 = mock(CriterioCatalogo.class);
		sutAnd = new And(docCC1, docCC2);
	}

	/**
	 * Indica que si los dos criterios son satisfechos, entonces devuelve True.
	 */
	@Test
	void test01_TrueSiLosDosCriteriosSonTrue() {
		//DOC
		docItCat = mock(ItemCatalogo.class);
		when(docCC1.isSatisfiedBy(docItCat)).thenReturn(true);
		when(docCC2.isSatisfiedBy(docItCat)).thenReturn(true);
		//EXERCISE
		boolean losDosSonTrue = sutAnd.isSatisfiedBy(docItCat);
		//VERIFY
		Assertions.assertTrue(losDosSonTrue);
	}

	/**
	 * Indica que si uno de los dos criterios no es satisfecho, entonces devuelve False.
	 */
	@Test
	void test02_FalseSiUnoDeLosDosCriteriosEsFalse() {
		//DOC
		docItCat = mock(ItemCatalogo.class);
		when(docCC1.isSatisfiedBy(docItCat)).thenReturn(false);
		when(docCC2.isSatisfiedBy(docItCat)).thenReturn(true);
		//EXERCISE
		boolean losDosSonTrue = sutAnd.isSatisfiedBy(docItCat);
		//VERIFY
		Assertions.assertFalse(losDosSonTrue);
	}
}
