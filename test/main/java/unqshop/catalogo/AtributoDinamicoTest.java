package main.java.unqshop.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AtributoDinamicoTest {
	
	private AtributoDinamico sutAtrD;
	
	/**
	 * Crea un escenario donde un atributo dinamico tiene 2 clave y valor:
	 * Ancho: 2
	 * Altura: 2
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		sutAtrD = new AtributoDinamico();
		sutAtrD.ingresarAtributo("Ancho", "2");
		sutAtrD.ingresarAtributo("Altura", "2");
	}

	/**
	 * Indica que al preguntarle sobre los dos atributos del atributo dinamico, sus valores son: 2 y 2.
	 */
	@Test
	void test01_DosValoresDeAtributoDinamico() {
		//EXERCISE
		String valor1 = sutAtrD.obtenerValor("Ancho");
		String valor2 = sutAtrD.obtenerValor("Altura");
		//VERIFY
		Assertions.assertEquals("2", valor1);
		Assertions.assertEquals("2", valor2);
	}
	
	/**
	 * Indica que la descripcion del Atributo Dinamico es:
	 * Ancho: 2
	 * Altura: 2
	 * 
	 */
	@Test
	void test02_DescripcionAtributoDinamico() {
		//EXERCISE
		String descripcion = sutAtrD.toString();
		//VERIFY
		Assertions.assertEquals("Altura: "+"2\n"+"Ancho: "+"2\n", descripcion);
	}
	
	/**
	 * Indica que al ingresar un nuevo atributo llamado "Dimension" y preguntarle su valor, es de 4.
	 * @throws Exception 
	 */
	@Test
	void test03_IngresarNuevoAtributoYValorEnAtributoDinamico() throws Exception {
		//EXERCISE
		sutAtrD.ingresarAtributo("Dimension", "4");
		String atrib = sutAtrD.obtenerValor("Dimension");
		//VERIFY
		Assertions.assertEquals("4", atrib);
		
	}
	
	/**
	 * Indica que no se puede ingresar un atributo ya sea con un nombre vacio o valor vacio.
	 */
	@Test
	void test04_NoSePuedeIngresarUnValorYNombreAtributoVacioEnAtributoDinamico() {
		//EXERCISE
		Exception nombreVacio = assertThrows(Exception.class, ()->{
			sutAtrD.ingresarAtributo("", "2");
		});
		
		Exception valorVacio = assertThrows(Exception.class, ()->{
			sutAtrD.ingresarAtributo("Atributo", "");
		});
		
		//VERIFY
		Assertions.assertEquals("Uno de los Strings ingresados es vacio", nombreVacio.getMessage());
		Assertions.assertEquals("Uno de los Strings ingresados es vacio", valorVacio.getMessage());
	}
}
