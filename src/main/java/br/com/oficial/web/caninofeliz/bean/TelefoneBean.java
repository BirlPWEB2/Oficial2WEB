package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.oficial.web.caninofeliz.dao.TelefoneDAO;
import br.com.oficial.web.caninofeliz.domain.Telefone;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TelefoneBean implements Serializable {
	private Telefone telefone;
	private List<Telefone> telefones;

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	@PostConstruct
	public void listar() {
		try {
			TelefoneDAO telefoneDAO = new TelefoneDAO();
			telefones = telefoneDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao listar os telefones");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			telefone = new Telefone();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao gerar um novo telefone");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			TelefoneDAO telefoneDAO = new TelefoneDAO();
			telefoneDAO.merge(telefone);

			telefone = new Telefone();

			telefones = telefoneDAO.listarTudo();

			Messages.addGlobalInfo("Telefone salvo");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao salvar um novo telefone");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			telefone = (Telefone) evento.getComponent().getAttributes().get("telefoneSelecionado");

			TelefoneDAO telefoneDAO = new TelefoneDAO();
			telefoneDAO.excluir(telefone);

			telefones = telefoneDAO.listarTudo();

			Messages.addGlobalInfo("Telefone removido");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao remover a telefone");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			telefone = (Telefone) evento.getComponent().getAttributes().get("telefoneSelecionado");

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao editar um telefone");
			erro.printStackTrace();
		}
	}
}
