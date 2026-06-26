package main.java.unqshop.catalogo;

/**
 * Un Enumerativo para Categorias de items de la tienda.
 * 
 * @author Fabrizio Lautaro Massa
 * @see Producto
 */
public enum Categoria {
	ELECTRONICA("Electronica y Tecnologia"),
    ROPA("Indumentaria y Accesorios"),
    HOGAR("Articulos para el Hogar"),
    ALIMENTOS("Alimentos y Bebidas"),
    JUGUETES("Juguetes y Entretenimiento");

    private final String descripcion;

    /**
     * Crea una Categoria con su descripción en texto.
     * 
     * @param descripcion, la descripción en texto de la categoria.
     */
    Categoria(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }
}
