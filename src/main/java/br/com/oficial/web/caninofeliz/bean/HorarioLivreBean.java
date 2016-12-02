package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.oficial.web.caninofeliz.dao.AgendaDAO;
import br.com.oficial.web.caninofeliz.dao.AnimalDAO;
import br.com.oficial.web.caninofeliz.dao.HorarioAgendaDAO;
import br.com.oficial.web.caninofeliz.domain.Agenda;
import br.com.oficial.web.caninofeliz.domain.Animal;
import br.com.oficial.web.caninofeliz.domain.HorarioAgenda;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class HorarioLivreBean implements Serializable {
	private HorarioAgenda horarioLivre;
	private List<HorarioAgenda> horariosLivres;
	private List<Agenda> agendas;
	private List<Animal> animais;

	public HorarioAgenda getHorarioLivre() {
		return horarioLivre;
	}

	public void setHorarioLivre(HorarioAgenda horarioLivre) {
		this.horarioLivre = horarioLivre;
	}

	public List<HorarioAgenda> getHorariosLivres() {
		return horariosLivres;
	}

	public void setHorariosLivres(List<HorarioAgenda> horariosLivres) {
		this.horariosLivres = horariosLivres;
	}

	public List<Agenda> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}

	public List<Animal> getAnimais() {
		return animais;
	}

	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}

	@PostConstruct
	public void listar() {
		try {
			HorarioAgendaDAO horarioAgendaDAO = new HorarioAgendaDAO();
			horariosLivres = horarioAgendaDAO.listarTudoHorarioLivres();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao listar os horarios");
			erro.printStackTrace();
		}
	}


	public void salvar() {
		try {
			HorarioAgendaDAO horarioAgendaDAO = new HorarioAgendaDAO();
			horarioAgendaDAO.merge(horarioLivre);

			horarioLivre = new HorarioAgenda();

			AnimalDAO animalDAO = new AnimalDAO();
			AgendaDAO agendaDAO = new AgendaDAO();
			animais = animalDAO.listarTudo();
			agendas = agendaDAO.listarTudo();

			horariosLivres = horarioAgendaDAO.listarTudoHorarioLivres();

			Messages.addGlobalInfo("horario salvo");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao salvar um novo horario");
			erro.printStackTrace();
		}
	}


	public void editar(ActionEvent evento) {
		try {
			horarioLivre = (HorarioAgenda) evento.getComponent().getAttributes().get("horarioLivreSelecionada");

			AnimalDAO animalDAO = new AnimalDAO();
			AgendaDAO agendaDAO = new AgendaDAO();
			animais = animalDAO.listarTudo();
			agendas = agendaDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao editar um horario");
			erro.printStackTrace();
		}
	}

}
