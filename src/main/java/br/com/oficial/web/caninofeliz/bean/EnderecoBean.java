package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.oficial.web.caninofeliz.dao.CidadeDAO;
import br.com.oficial.web.caninofeliz.dao.EnderecoDAO;
import br.com.oficial.web.caninofeliz.domain.Cidade;
import br.com.oficial.web.caninofeliz.domain.Endereco;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EnderecoBean implements Serializable {
	private Endereco endereco;
	private List<Endereco> enderecos;
	private List<Cidade> cidades;

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	@PostConstruct
	public void listar() {
		try {
			EnderecoDAO enderecoDAO = new EnderecoDAO();
			enderecos = enderecoDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao listar os enderecos");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			endereco = new Endereco();

			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listarTudo();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao criar um novo endereco");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			EnderecoDAO enderecoDAO = new EnderecoDAO();
			enderecoDAO.merge(endereco);

			endereco = new Endereco();
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listarTudo();

			enderecos = enderecoDAO.listarTudo();

			Messages.addGlobalInfo("Endereco salvo");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao salvar um endereco");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			endereco = (Endereco) evento.getComponent().getAttributes().get("enderecoSelecionada");

			EnderecoDAO enderecoDAO = new EnderecoDAO();
			enderecoDAO.excluir(endereco);

			enderecos = enderecoDAO.listarTudo();

			Messages.addGlobalInfo("Endereco removido");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao remover o endereco");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			endereco = (Endereco) evento.getComponent().getAttributes().get("enderecoSelecionada");

			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listarTudo();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao editar um endereco");
			erro.printStackTrace();
		}
	}
}