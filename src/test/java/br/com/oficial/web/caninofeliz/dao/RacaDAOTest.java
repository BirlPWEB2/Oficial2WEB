package br.com.oficial.web.caninofeliz.dao;

import org.junit.Ignore;
import org.junit.Test;

import br.com.oficial.web.caninofeliz.domain.Raca;

public class RacaDAOTest {
	@Test
	@Ignore
	public void salvar(){
		Raca raca = new Raca();
		RacaDAO racaDAO = new RacaDAO();
		raca.setDescricao("Chow Chow");
		racaDAO.save(raca);
		
	}

}
