package br.com.oficial.web.caninofeliz.dao;

import org.junit.Test;

import br.com.oficial.web.caninofeliz.domain.Fabricante;

public class FabricanteDAOTest {
	@Test
	public void salvar(){
		Fabricante fabricante = new Fabricante();
		fabricante.setDescricao("Generico");
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		fabricanteDAO.save(fabricante);	
	}

}
