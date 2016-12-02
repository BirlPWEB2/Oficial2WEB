package br.com.oficial.web.caninofeliz.dao;

import org.junit.Ignore;
import org.junit.Test;

import br.com.oficial.web.caninofeliz.domain.Agenda;
import br.com.oficial.web.caninofeliz.domain.Funcionario;

public class AgendaDAOTest {
	@Test
	@Ignore
	public void salvar(){
	Long codigo = 1L;
	Agenda agenda = new Agenda();
	Funcionario funcionario = new Funcionario();
	FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
	funcionario = funcionarioDAO.buscarComCodigo(codigo);
	agenda.setFuncionario(funcionario);
	AgendaDAO agendaDAO = new AgendaDAO();
	agendaDAO.save(agenda);
	}
}
