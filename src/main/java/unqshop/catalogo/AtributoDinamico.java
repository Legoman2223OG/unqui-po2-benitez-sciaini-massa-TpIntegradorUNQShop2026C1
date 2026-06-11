package main.java.unqshop.catalogo;

import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Representa la posible lista de atributos extra que un Producto puede poseer.
 *
 * @author Fabrizio Lautaro Massa
 * @see Producto
 */
public class AtributoDinamico {
	HashMap<String,String> atribs = new HashMap<String,String>();
	
	/**
	 * Genera un nuevo atributo con un valor predefinido.
	 * 
	 * @param El nombre del atributo, no puede ser vacío.
	 * @param El valor del atributo, no puede ser vacío.
	 * 
	 * @throws Exception Si una de las condiciones especificadas no es respetada.
	 */
	public void ingresarAtributo(String nombre, String valor) throws Exception {
		validarStringNoVacio(nombre);
		validarStringNoVacio(valor);
		atribs.put(nombre, valor);
	}
	
	private void validarStringNoVacio(String str) throws Exception {
		if(str.isBlank())
			throw new Exception("Uno de los Strings ingresados es vacio");
	}

	/**
	 * Obtiene el valor del atributo especificado.
	 * 
	 * @param El nombre del atributo.
	 * 
	 * @return El valor del atributo.
	 */
	public String obtenerValor(String atributo) {
		return atribs.get(atributo);
	}

	@Override
	public String toString() {
		return this.atribs.entrySet().stream().map(atri -> atri.getKey() + ": " + atri.getValue() + "\n").collect(Collectors.joining());
	}
	
}
