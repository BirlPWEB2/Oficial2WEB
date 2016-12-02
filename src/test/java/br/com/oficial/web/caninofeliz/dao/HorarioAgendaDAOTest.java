package br.com.oficial.web.caninofeliz.dao;


import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import br.com.oficial.web.caninofeliz.domain.Agenda;
import br.com.oficial.web.caninofeliz.domain.Animal;
import br.com.oficial.web.caninofeliz.domain.HorarioAgenda;

public class HorarioAgendaDAOTest {
	@Test
	@Ignore
	public void salvar(){
		Agenda agenda = new Agenda();
		AgendaDAO agendaDAO = new AgendaDAO();
		agenda = agendaDAO.buscarComCodigo(1L);
		Date data = new Date();
		HorarioAgenda horarioAgenda = new HorarioAgenda();
		horarioAgenda.setAgenda(agenda);
		horarioAgenda.setData(data);
		horarioAgenda.setHorario("17:00");
		HorarioAgendaDAO horarioAgendaDAO = new HorarioAgendaDAO();
		horarioAgendaDAO.save(horarioAgenda);		
	}
	
	@Test
	public void merge(){
		Animal animal = new Animal();
		AnimalDAO animalDAO = new AnimalDAO();
		animal = animalDAO.buscarComCodigo(1L);
		HorarioAgendaDAO horarioAgendaDAO = new HorarioAgendaDAO();
		HorarioAgenda horarioAgenda = new HorarioAgenda();
		horarioAgenda = horarioAgendaDAO.buscarComCodigo(1L);
		horarioAgenda.setObs("Doente Mental");
		horarioAgenda.setAnimal(animal);
		horarioAgendaDAO.merge(horarioAgenda);
	}

}
