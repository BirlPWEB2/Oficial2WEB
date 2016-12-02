package br.com.oficial.web.caninofeliz.dao;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import br.com.oficial.web.caninofeliz.domain.Animal;
import br.com.oficial.web.caninofeliz.domain.Cliente;
import br.com.oficial.web.caninofeliz.domain.Especie;
import br.com.oficial.web.caninofeliz.domain.Raca;

public class AnimalDAOTest {
	@Test
	@Ignore
	public void salvar(){
		Long codigoC = 1L;
		Long codigoE = 1L;
		Long codigoR = 1L;
		Date nascimento = new Date();
		Cliente cliente = new Cliente();
		Especie especie = new Especie();
		Raca raca = new Raca();
		Animal animal = new Animal();
		ClienteDAO clienteDAO = new ClienteDAO();
		RacaDAO racaDAO = new RacaDAO();
		EspecieDAO especieDAO = new EspecieDAO();
		cliente = clienteDAO.buscarComCodigo(codigoC);
		especie = especieDAO.buscarComCodigo(codigoE);
		raca = racaDAO.buscarComCodigo(codigoR);
		AnimalDAO animalDAO = new AnimalDAO();
		animal.setCliente(cliente);
		animal.setCor("Branco");
		animal.setEspecie(especie);
		animal.setRaca(raca);
		animal.setNome("Biro Biro");
		animal.setIdade(1);
		animal.setNascimento(nascimento);
		animalDAO.save(animal);
		
	}

}
