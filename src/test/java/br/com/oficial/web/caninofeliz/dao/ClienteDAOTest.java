package br.com.oficial.web.caninofeliz.dao;

import org.junit.Ignore;
import org.junit.Test;

import br.com.oficial.web.caninofeliz.domain.Cliente;
import br.com.oficial.web.caninofeliz.domain.Endereco;
import br.com.oficial.web.caninofeliz.domain.Telefone;

public class ClienteDAOTest {
	@Test
	@Ignore
	public void salvar() {
		Long codigoE = 1L;
		Long codigoT = 1L;
		Cliente cliente = new Cliente();
		ClienteDAO clienteDAO = new ClienteDAO();
		Endereco endereco = new Endereco();
		Telefone telefone = new Telefone();
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		TelefoneDAO telefoneDAO = new TelefoneDAO();
		endereco = enderecoDAO.buscarComCodigo(codigoE);
		telefone = telefoneDAO.buscarComCodigo(codigoT);
		cliente.setCpf("11111111111");
		cliente.setEmail("leandro@hotmail.com");
		cliente.setEndereco(endereco);
		cliente.setNome("Leandro");
		cliente.setRg("4519910");
		cliente.setTelefone(telefone);
		cliente.setSobrenome("Rogala");
		clienteDAO.save(cliente);
	}

}
