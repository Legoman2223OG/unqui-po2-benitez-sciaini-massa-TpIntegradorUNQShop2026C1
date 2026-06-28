package main.java.unqshop.reportes;

/*
Visitor (NodeVisitor):
 declares a Visit operation for each class of ConcreteElement in the object
 structure. The operation's name and signature identifies the class that sends
 the Visit request to the visitor. That lets the visitor determine the concrete
 class of the element being visited. Then the visitor can access the element
 directly through its particular interface.
*/

/*
 * Segun GoF == 'VISITOR'
 * declara una operacion por cada tipo concreto de reporte.
 * */
public interface ReporteVisitor {

    String visitarProductos(ReporteDeProductosMasVendidos reporte);
}
