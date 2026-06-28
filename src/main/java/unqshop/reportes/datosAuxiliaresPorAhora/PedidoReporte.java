package unqshop.reportes.datosAuxiliaresPorAhora;

import java.time.LocalDate;
import java.util.List;

// pedido simplificado para armar reportes sin depender del modulo pedido todavia.
public class PedidoReporte {

    private LocalDate fecha_entrega;
    private List<ItemReporte> items;

    public PedidoReporte(LocalDate fecha_entrega, List<ItemReporte> items) {
        this.fecha_entrega = fecha_entrega;
        this.items = items;
    }

    //-----------------
    // getter & setter

    public LocalDate getFechaEntrega() {
        return fecha_entrega;
    }

    public List<ItemReporte> getItems() {
        return items;
    }
    //-----------------
}
