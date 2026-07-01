package main.java.unqshop.pedido;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.java.unqshop.catalogo.ItemCatalogo;

class BorradorTest {
	
	Borrador borrador;
    Pedido pedido;
    ItemCatalogo item1;
    ItemCatalogo item2;
    
    
	@BeforeEach
	void setUp() throws Exception {
		borrador = new Borrador();
        pedido   = Mockito.mock(Pedido.class);
        item1    = Mockito.mock(ItemCatalogo.class);
        item2    = Mockito.mock(ItemCatalogo.class);
	}

	@Test
    void contextoEsBorrador() {
        assertEquals(ContextoTipo.BORRADOR, borrador.contexto());
    }
	
	@Test
    void confirmarCambiaAConfirmadoYDescuentaStock() {
        borrador.confirmar(pedido);

        Mockito.verify(pedido).cambiarContexto(new Confirmado());
        Mockito.verify(pedido).descrementarStock();
    }
	
	@Test
    void cancelarCancelaElPedido() {
        borrador.cancelar(pedido);

        Mockito.verify(pedido).cancelarPriv();
    }
	
	@Test
    void agregarItemAgregaItemPriv() {
        borrador.agregarItem(pedido, item1);

        Mockito.verify(pedido).agregarItemPriv(item1);
    }
	
	@Test
    void quitarItemQuitaItemPriv() {
        borrador.quitarItem(pedido, item1);

        Mockito.verify(pedido).quitarItemPriv(item1);
    }

}
