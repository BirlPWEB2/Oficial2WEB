package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.oficial.web.caninofeliz.dao.CargoDAO;
import br.com.oficial.web.caninofeliz.domain.Cargo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CargoBean implements Serializable {

	private Cargo cargo;
	private List<Cargo> cargos;

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public void novo() {
		cargo = new Cargo();
	}

	@PostConstruct
	public void listar() {
		try {
			CargoDAO cargoDAO = new CargoDAO();
			cargos = cargoDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar os cargos");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			CargoDAO cargoDAO = new CargoDAO();
			cargoDAO.merge(cargo);

			cargo = new Cargo();
			cargos = cargoDAO.listarTudo();

			Messages.addGlobalInfo("Cargo salvo");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao salvar");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			cargo = (Cargo) evento.getComponent().getAttributes().get("cargoSelecionado");

			CargoDAO cargoDAO = new CargoDAO();
			cargoDAO.excluir(cargo);

			cargos = cargoDAO.listarTudo();

			Messages.addGlobalInfo("Cargo removido");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao remover");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		cargo = (Cargo) evento.getComponent().getAttributes().get("cargoSelecionado");
	}
}
