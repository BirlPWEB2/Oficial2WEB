package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.oficial.web.caninofeliz.dao.FabricanteDAO;
import br.com.oficial.web.caninofeliz.domain.Fabricante;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FabricanteBean implements Serializable{
	
	private Fabricante fabricante;
	private List<Fabricante> fabricantes;


	public Fabricante getFabricante() {
		return fabricante;
	}
	
	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public void novo() {
		fabricante = new Fabricante();
	}

	@PostConstruct
	public void listar() {
		try {
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("erro ao listar os fabricantes");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricanteDAO.merge(fabricante);

			fabricante = new Fabricante();
			fabricantes = fabricanteDAO.listarTudo();

			Messages.addGlobalInfo("Fabricante salvo");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("erro ao salvar o fabricante");
			erro.printStackTrace();
		}
	}
	
	public void excluir(ActionEvent evento) {
		try {
			fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricanteDAO.excluir(fabricante);
			
			fabricantes = fabricanteDAO.listarTudo();

			Messages.addGlobalInfo("Fabricante removido");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao remover o fabricante");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento){
		fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");
	}
}
