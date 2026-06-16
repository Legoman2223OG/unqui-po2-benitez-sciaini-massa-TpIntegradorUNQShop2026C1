package main.java.unqshop.busqueda.criterios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.unqshop.catalogo.Categoria;
import main.java.unqshop.catalogo.ItemCatalogo;

class PorCategoriaTest {

	private PorCategoria sutPorCat;
	private ItemCatalogo docItCat;
	
	/**
	 * Crea un escenario para un criterio de categoria para la categoria de ELECTRONICA.
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		sutPorCat = new PorCategoria(Categoria.ELECTRONICA);
	}

	/**
	 * Indica que al preguntarle a un item si es de categoria de electronica, si lo es, devuelve true.
	 */
	@Test
	void test01_EsUnItemDeEletronica() {
		//DOC
		docItCat = mock(ItemCatalogo.class);
		when(docItCat.getCategoria()).thenReturn(Categoria.ELECTRONICA);
		//EXERCISE
		boolean esDeElectronica = sutPorCat.isSatisfiedBy(docItCat);
		//VERIFY
		Assertions.assertTrue(esDeElectronica);
	}
	
	/**
	 * Indica que al preguntarle a un item si es de categoria de electronica, si no lo es, devuelve false.
	 */
	@Test
	void test02_NoEsUnItemDeEletronica() {
		//DOC
		docItCat = mock(ItemCatalogo.class);
		when(docItCat.getCategoria()).thenReturn(Categoria.HOGAR);
		//EXERCISE
		boolean esDeElectronica = sutPorCat.isSatisfiedBy(docItCat);
		//VERIFY
		Assertions.assertFalse(esDeElectronica);
	}

}
