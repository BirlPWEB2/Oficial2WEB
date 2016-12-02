package br.com.oficial.web.caninofeliz.dao;

import org.junit.Ignore;
import org.junit.Test;

import br.com.oficial.web.caninofeliz.domain.Telefone;

public class TelefoneDAOTest {
	@Test
	@Ignore
	public void salvar() {
		Telefone telefone = new Telefone();
		TelefoneDAO telefoneDAO = new TelefoneDAO();
		telefone.setDdd("47");
		telefone.setTelefone("92010785");
		telefoneDAO.save(telefone);
	}

}
