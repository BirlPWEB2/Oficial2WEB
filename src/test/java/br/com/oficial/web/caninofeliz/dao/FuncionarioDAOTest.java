package br.com.oficial.web.caninofeliz.dao;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import br.com.oficial.web.caninofeliz.domain.Cargo;
import br.com.oficial.web.caninofeliz.domain.Endereco;
import br.com.oficial.web.caninofeliz.domain.Funcionario;
import br.com.oficial.web.caninofeliz.domain.Telefone;

public class FuncionarioDAOTest {
	@Test
	@Ignore
	public void salvar() {
		Long codigoE = 1L;
		Long codigoT = 1L;
		Long codigoC = 1L;
		Cargo cargo = new Cargo();
		Date dataEntrada = new Date();
		CargoDAO cargoDAO = new CargoDAO();
		cargo = cargoDAO.buscarComCodigo(codigoC);
		Funcionario funcionario = new Funcionario();
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		Endereco endereco = new Endereco();
		Telefone telefone = new Telefone();
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		TelefoneDAO telefoneDAO = new TelefoneDAO();
		endereco = enderecoDAO.buscarComCodigo(codigoE);
		telefone = telefoneDAO.buscarComCodigo(codigoT);
		funcionario.setCpf("22222222222");
		funcionario.setEmail("Janete@hotmail.com");
		funcionario.setEndereco(endereco);
		funcionario.setNome("Janete");
		funcionario.setRg("5555555");
		funcionario.setTelefone(telefone);
		funcionario.setSobrenome("Rogala");
		funcionario.setDataEntrada(dataEntrada);
		funcionario.setSalario(new BigDecimal("850.90"));
		funcionario.setCargo(cargo);
		funcionarioDAO.save(funcionario);
	}

}
