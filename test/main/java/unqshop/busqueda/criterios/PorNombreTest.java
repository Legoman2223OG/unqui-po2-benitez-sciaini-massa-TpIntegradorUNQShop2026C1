package main.java.unqshop.busqueda.criterios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.unqshop.catalogo.ItemCatalogo;

class PorNombreTest {

	private PorNombre sutPorNom;
	private ItemCatalogo docItCat;
	
	/**
	 * Crea un escenario para un criterio de nombre, donde se centra en buscar si un item tiene nombre de "Jabon".
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		sutPorNom = new PorNombre("Jabon");
	}

	/**
	 * Indica que al preguntarle si un item se llama "Jabon", si se llama así, devuelve true.
	 */
	@Test
	void test01_ElItemSeLlamaComoLoIndicaElCriterio() {
		//DOC
		docItCat = mock(ItemCatalogo.class);
		when(docItCat.getNombre()).thenReturn("Jabon");
		//EXERCISE
		boolean seLlamaAsi = sutPorNom.isSatisfiedBy(docItCat);
		//VERIFY
		Assertions.assertTrue(seLlamaAsi);
	}
	
	/**
	 * Indica que al preguntarle si un item se llama "Jabon", si no se llama así, devuelve false.
	 */
	@Test
	void test01_ElItemNoSeLlamaComoLoIndicaElCriterio() {
		//DOC
		docItCat = mock(ItemCatalogo.class);
		when(docItCat.getNombre()).thenReturn("TV");
		//EXERCISE
		boolean seLlamaAsi = sutPorNom.isSatisfiedBy(docItCat);
		//VERIFY
		Assertions.assertFalse(seLlamaAsi);
	}

}
