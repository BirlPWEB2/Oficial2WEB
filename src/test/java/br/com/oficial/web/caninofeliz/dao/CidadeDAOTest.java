package br.com.oficial.web.caninofeliz.dao;

import org.junit.Ignore;
import org.junit.Test;

import br.com.oficial.web.caninofeliz.domain.Cidade;
import br.com.oficial.web.caninofeliz.domain.Estado;

public class CidadeDAOTest {
	@Test
	@Ignore
	public void salvar() {
		Long codigo = 2L;
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscarComCodigo(codigo);
		Cidade cidade = new Cidade();
		cidade.setNome("Schroeder");
		cidade.setEstado(estado);

		CidadeDAO cidadeDAO = new CidadeDAO();
		cidadeDAO.save(cidade);
	}

}
