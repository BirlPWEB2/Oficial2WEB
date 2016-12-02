package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.oficial.web.caninofeliz.dao.CidadeDAO;
import br.com.oficial.web.caninofeliz.dao.EstadoDAO;
import br.com.oficial.web.caninofeliz.domain.Cidade;
import br.com.oficial.web.caninofeliz.domain.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CidadeBean implements Serializable {
	private Cidade cidade;
	private List<Cidade> cidades;
	private List<Estado> estados;

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	@PostConstruct
	public void listar() {
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao lsitar as cidades");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			cidade = new Cidade();

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao criar uma cidade");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.merge(cidade);

			cidade = new Cidade();

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listarTudo();

			cidades = cidadeDAO.listarTudo();

			Messages.addGlobalInfo("Cidade salva");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao salvar a cidade");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");

			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.excluir(cidade);

			cidades = cidadeDAO.listarTudo();

			Messages.addGlobalInfo("Cidade removida");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao remover");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento){
		try {
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao editar a cidade");
			erro.printStackTrace();
		}	
	}
}