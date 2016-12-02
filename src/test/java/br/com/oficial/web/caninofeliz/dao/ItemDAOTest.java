package br.com.oficial.web.caninofeliz.dao;

import java.math.BigDecimal;

import org.junit.Test;

import br.com.oficial.web.caninofeliz.domain.Fabricante;
import br.com.oficial.web.caninofeliz.domain.Item;

public class ItemDAOTest {
	@Test
	public void salvar() {
		Fabricante fabricante = new Fabricante();
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		fabricante = fabricanteDAO.buscarComCodigo(1L);
		Item item = new Item();
		item.setDescricao("Cataflan");
		item.setFabricante(fabricante);
		item.setPreco(new BigDecimal("18.50"));
		item.setQuantidade((short) 5);
		ItemDAO itemDAO = new ItemDAO();
		itemDAO.save(item);
	}

}
