package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.oficial.web.caninofeliz.dao.EstadoDAO;
import br.com.oficial.web.caninofeliz.domain.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EstadoBean implements Serializable{
	private Estado estado;
	private List<Estado> estados;


	public Estado getEstado() {
		return estado;
	}
	
	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public void novo() {
		estado = new Estado();
	}
	
	@PostConstruct
	public void listar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("erro ao listar os estados");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.merge(estado);

			estado = new Estado();
			estados = estadoDAO.listarTudo();

			Messages.addGlobalInfo("Estado salvo");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("erro ao salvar o estado");
			erro.printStackTrace();
		}
	}
	
	public void excluir(ActionEvent evento) {
		try {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");

			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.excluir(estado);
			
			estados = estadoDAO.listarTudo();

			Messages.addGlobalInfo("Estado removido");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao remover o estado");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento){
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
	}
}
