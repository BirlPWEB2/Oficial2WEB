package br.com.oficial.web.caninofeliz.dao;

import org.junit.Ignore;
import org.junit.Test;

import br.com.oficial.web.caninofeliz.domain.Especie;

public class EspecieDAOTest {
	@Test
	@Ignore
	public void salvar(){
		Especie especie = new Especie();
		EspecieDAO especieDAO = new EspecieDAO();
		especie.setDescricao("Cachorro");
		especieDAO.save(especie);		
	}

}
