package main.java.unqshop;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.unqshop.busqueda.CriterioCatalogo;
import main.java.unqshop.catalogo.ItemCatalogo;

class UnqShopTest {
	
	private UnqShop sutUnqShop;
	private ItemCatalogo i1,i2,i3; //DOC de items
	
	/**
	 * Crea un escenario donde se instancia un root de la UnqShop con 3 items cargados.
	 * @throws Exception
	 */
	@BeforeEach 
	void setUp() throws Exception {
		i1 = mock(ItemCatalogo.class);
		i2 = mock(ItemCatalogo.class);
		i3 = mock(ItemCatalogo.class);
		sutUnqShop = new UnqShop(i1,i2,i3);
	}

	/**
	 * Se verifica que al agregar un item al catalogo y buscarlo, este deberia de aparecer.
	 */
	@Test
	void test01_AgregarUnItemAlCatalogo() {
		//DOC
		ItemCatalogo i4 = mock(ItemCatalogo.class);
		CriterioCatalogo cc = mock(CriterioCatalogo.class);
		when(cc.isSatisfiedBy(i4)).thenReturn(true);
		when(cc.isSatisfiedBy(i3)).thenReturn(false);
		when(cc.isSatisfiedBy(i2)).thenReturn(false);
		when(cc.isSatisfiedBy(i1)).thenReturn(false);
		//Exercise
		sutUnqShop.agregarItem(i4);
		ItemCatalogo itemNuevo = sutUnqShop.buscarItems(cc).get(0);
		//Verify
		Assertions.assertEquals(i4, itemNuevo);
	}
	
	/**
	 * Se verifica que al hacer un criterio de busqueda solo para los items 1 y 2, devuelve exactamente esos items.
	 */
	@Test
	void test02_BuscarItemCatalogo() {
		//DOC
		CriterioCatalogo cc = mock(CriterioCatalogo.class);
		when(cc.isSatisfiedBy(i1)).thenReturn(true);
		when(cc.isSatisfiedBy(i2)).thenReturn(true);
		when(cc.isSatisfiedBy(i3)).thenReturn(false);
		//EXERCISE
		List<ItemCatalogo> items = sutUnqShop.buscarItems(cc);
		//VERIFY
		Assertions.assertEquals(i1, items.getFirst());
		Assertions.assertEquals(i2, items.getLast());
	}
	
	/**
	 * Se verifica que al instanciar un UnqShop sin objetos y al agregarle 1 y buscarlo, deberia de retornar el objeto en la lista.
	 */
	@Test
	void test03_UnqShopCon1Soloitem() {
		//SUT
		UnqShop sutUnqShopVacio = new UnqShop();
		//DOC
		ItemCatalogo i5 = mock(ItemCatalogo.class);
		CriterioCatalogo cc = mock(CriterioCatalogo.class);
		when(cc.isSatisfiedBy(i5)).thenReturn(true);
		//EXERCISE
		sutUnqShopVacio.agregarItem(i5);
		ItemCatalogo itemNuevo = sutUnqShopVacio.buscarItems(cc).getFirst();
		//VERIFY
		Assertions.assertEquals(i5, itemNuevo);
	}

}
