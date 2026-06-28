package unqshop.reportes;



/*
 * ---------------------------------------------------------------
 * MODULO REPORTES — resumen general
 *
 * Este módulo genera reportes sobre lo que pasó en la tienda.
 * Por ahora implementa uno solo: productos más vendidos en un período.
 *
 * Patrón usado: VISITOR
 * La idea es separar los DATOS del reporte de cómo se Muestra.
 * El reporte sabe qué productos vendió y cuánto, pero *no sabe* si eso va a terminar siendo un CSV, un HTML o texto plano.
 * Eso lo decide el visitor (FormatoCSV, FormatoHTML, FormatoDeTexto),que se le pasa por parámetro al momento de generar la salida.
 * Así si mañana quiero agregar un formato PDF, no toco el reporte.
 *
 * Clases principales:
 *  - Reporte: interfaz común de todos los reportes (el "enchufe")
 *  - ReporteVisitor: interfaz de los formatos de salida
 *  - ReporteDeProductosMasVendidos: el único reporte concreto por ahora, recibe una lista de pedidos y un rango de fechas,
 *     filtra los entregados en ese período y acumula cantidades y precios por SKU
 *  - FormatoCSV / FormatoHTML / FormatoDeTexto: los visitors CONCRETOS,
 *    cada uno genera/formatea el reporte a su respectivo formato
 *
 * Paquete datosAuxiliaresPorAhora:
 *  PedidoReporte, ItemReporte y ProductoReporte son clases de datos simples
 *  que uso internamente para no depender directamente de las clases de
 *  mis compañeros todavía. Son básicamente los datos que el reporte necesita
 *  y nada más. La ReportesFacade se encarga de convertir los objetos reales
 *  a estos DTOs antes de pasárselos al reporte.
 *
 * ---------------------------------------------------------------
 *
 * */
/*
 * INTERFAZ COMUN ENTRE TODOS LOS REPORTES
 * Segun Gof == 'ELEMENT' (elemento de la estructura sobre el cual el visitor opera)
 * evita,por ejemplo :  if (reporte instanceof ...), con esta interfaz se llama nada mas a aceptar
 * y el patron resuelve su comportamiento
 * */


/// el patron evita violar el principio de responsabilidad única
/// los datos viven en el reporte, las operaciones viven en los visitors.
/*
* • Element (Node)
- defines an Accept operation that takes a visitor as an argument.
* */

//"Todo reporte tiene el mismo “enchufe”:"
public interface Reporte {
    String aceptar(ReporteVisitor visitor);
}
