package main.java.unqshop.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoriaTest {
	
	/**
	 * Al preguntarle la descripcion de la categoria ELECTRONICA, describe "Electronica y Tecnologia"
	 */
	@Test
	void test01_DescripcionElectronica() {
		//EXERCISE
		String desc = Categoria.ELECTRONICA.getDescripcion();
		//VERIFY
		Assertions.assertEquals("Electronica y Tecnologia", desc);
	}
	
	/**
	 * Al preguntarle el toString de la categoria ELECTRONICA, describe "Electronica y Tecnologia".
	 */
	@Test
	void test02_ToStringEletronica() {
		//VERIFY
		Assertions.assertEquals("Electronica y Tecnologia", Categoria.ELECTRONICA.toString());
	}

}
