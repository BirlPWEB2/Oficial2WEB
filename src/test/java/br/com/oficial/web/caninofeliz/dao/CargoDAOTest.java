package br.com.oficial.web.caninofeliz.dao;

import org.junit.Ignore;
import org.junit.Test;

import br.com.oficial.web.caninofeliz.domain.Cargo;

public class CargoDAOTest {
	@Test
	@Ignore
	public void salvar(){
		Cargo cargo = new Cargo();
		CargoDAO cargoDAO = new CargoDAO();
		cargo.setDescricao("Gerente");
		cargoDAO.save(cargo);
	}

}
