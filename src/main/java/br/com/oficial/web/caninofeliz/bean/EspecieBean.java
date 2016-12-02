package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.oficial.web.caninofeliz.dao.EspecieDAO;
import br.com.oficial.web.caninofeliz.domain.Especie;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EspecieBean implements Serializable {

	private Especie especie;
	private List<Especie> especies;

	public Especie getEspecie() {
		return especie;
	}

	public void setEspecie(Especie especie) {
		this.especie = especie;
	}

	public List<Especie> getEspecies() {
		return especies;
	}

	public void setEspecies(List<Especie> especies) {
		this.especies = especies;
	}

	public void novo() {
		especie = new Especie();
	}

	@PostConstruct
	public void listar() {
		try {
			EspecieDAO especieDAO = new EspecieDAO();
			especies = especieDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("erro ao listar as especies");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			EspecieDAO especieDAO = new EspecieDAO();
			especieDAO.merge(especie);

			especie = new Especie();
			especies = especieDAO.listarTudo();

			Messages.addGlobalInfo("Especie salvo");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("erro ao salvar a especie");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			especie = (Especie) evento.getComponent().getAttributes().get("especieSelecionado");

			EspecieDAO especieDAO = new EspecieDAO();
			especieDAO.excluir(especie);

			especies = especieDAO.listarTudo();

			Messages.addGlobalInfo("Especie removido");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao remover a especie");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		especie = (Especie) evento.getComponent().getAttributes().get("especieSelecionado");
	}
}
