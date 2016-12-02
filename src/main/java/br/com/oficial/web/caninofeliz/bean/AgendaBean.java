package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.oficial.web.caninofeliz.dao.AgendaDAO;
import br.com.oficial.web.caninofeliz.dao.FuncionarioDAO;
import br.com.oficial.web.caninofeliz.domain.Agenda;
import br.com.oficial.web.caninofeliz.domain.Funcionario;

/**
 * 
 * @author Administrador ManagedBea são as classes controladores do sistema,
 *         utilizando o modelo MVC. Nessa classe, ela é utilizada para controlar
 *         os objetos Agenda, ganhando os parametros da camada de vizualização e
 *         acessando as camada DAO e controlando os dominio para realizar toda a
 *         logica do sistema.
 *
 */

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AgendaBean implements Serializable {
	private Agenda agenda;
	private List<Agenda> agendas;
	private List<Funcionario> funcionarios;

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public List<Agenda> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	// Anotação PostConstruct é uma anotação que diz que apos o objeto ser
	// instanciado pelo seu construtor padrão, ele vai ser encaminhado
	// diretamente para esse metodo
	@PostConstruct
	public void listar() {
		try {
			AgendaDAO agendaDAO = new AgendaDAO();
			agendas = agendaDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao listar as agendas");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			agenda = new Agenda();

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao criar uma agenda");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			AgendaDAO agendaDAO = new AgendaDAO();
			agendaDAO.merge(agenda);

			agenda = new Agenda();

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarTudo();

			agendas = agendaDAO.listarTudo();

			Messages.addGlobalInfo("Agenda Salvada");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao salvar a agenda");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			agenda = (Agenda) evento.getComponent().getAttributes().get("agendaSelecionada");

			AgendaDAO agendaDAO = new AgendaDAO();
			agendaDAO.excluir(agenda);

			agendas = agendaDAO.listarTudo();

			Messages.addGlobalInfo("Removido");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao remover a agenda");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			agenda = (Agenda) evento.getComponent().getAttributes().get("agendaSelecionada");

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao editar a agenda");
			erro.printStackTrace();
		}
	}

}
