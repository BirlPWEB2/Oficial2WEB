package br.com.oficial.web.caninofeliz.dao;

import org.junit.Ignore;
import org.junit.Test;

import br.com.oficial.web.caninofeliz.domain.Cidade;
import br.com.oficial.web.caninofeliz.domain.Endereco;

public class EnderecoDAOTest {
	@Test
	@Ignore
	public void salvar() {
		Long codigo = 1L;
		Cidade cidade = new Cidade();
		CidadeDAO cidadeDAO = new CidadeDAO();
		cidade = cidadeDAO.buscarComCodigo(codigo);	
		Endereco endereco = new Endereco();
		endereco.setBairro("Schroeder 1");
		endereco.setCep("89275000");
		endereco.setComplemento("CTG");
		endereco.setNumero((short) 770);
		endereco.setRua("Otto HackBarth");
		endereco.setCidade(cidade);
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		enderecoDAO.save(endereco);
	}

}
