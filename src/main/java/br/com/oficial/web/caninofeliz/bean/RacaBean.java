package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.oficial.web.caninofeliz.dao.RacaDAO;
import br.com.oficial.web.caninofeliz.domain.Raca;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RacaBean implements Serializable {

	private Raca raca;
	private List<Raca> racas;

	public Raca getRaca() {
		return raca;
	}

	public void setRaca(Raca raca) {
		this.raca = raca;
	}

	public List<Raca> getRacas() {
		return racas;
	}

	public void setRacas(List<Raca> racas) {
		this.racas = racas;
	}

	public void novo() {
		raca = new Raca();
	}

	@PostConstruct
	public void listar() {
		try {
			RacaDAO racaDAO = new RacaDAO();
			racas = racaDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("erro ao listar as raças");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			RacaDAO racaDAO = new RacaDAO();
			racaDAO.merge(raca);

			raca = new Raca();
			racas = racaDAO.listarTudo();

			Messages.addGlobalInfo("Raça salvo");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("erro ao salvar a raça");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			raca = (Raca) evento.getComponent().getAttributes().get("racaSelecionado");

			RacaDAO racaDAO = new RacaDAO();
			racaDAO.excluir(raca);

			racas = racaDAO.listarTudo();

			Messages.addGlobalInfo("Raça removido");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao remover a raça");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		raca = (Raca) evento.getComponent().getAttributes().get("racaSelecionado");
	}
}
