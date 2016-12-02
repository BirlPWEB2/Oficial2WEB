package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;

import br.com.oficial.web.caninofeliz.dao.AgendaDAO;
import br.com.oficial.web.caninofeliz.dao.AnimalDAO;
import br.com.oficial.web.caninofeliz.dao.HorarioAgendaDAO;
import br.com.oficial.web.caninofeliz.domain.Agenda;
import br.com.oficial.web.caninofeliz.domain.Animal;
import br.com.oficial.web.caninofeliz.domain.HorarioAgenda;
import br.com.oficial.web.caninofeliz.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class HorarioAgendaBean implements Serializable {
	private HorarioAgenda horario;
	private List<HorarioAgenda> horarios;
	private List<Agenda> agendas;
	private List<Animal> animais;

	public HorarioAgenda getHorario() {
		return horario;
	}

	public void setHorario(HorarioAgenda horario) {
		this.horario = horario;
	}

	public List<HorarioAgenda> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorarioAgenda> horarios) {
		this.horarios = horarios;
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
			horarios = horarioAgendaDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao listar os horarios");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			horario = new HorarioAgenda();

			AnimalDAO animalDAO = new AnimalDAO();
			AgendaDAO agendaDAO = new AgendaDAO();
			animais = animalDAO.listarTudo();
			agendas = agendaDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao criar um horario");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			HorarioAgendaDAO horarioAgendaDAO = new HorarioAgendaDAO();
			horarioAgendaDAO.merge(horario);

			horario = new HorarioAgenda();

			AnimalDAO animalDAO = new AnimalDAO();
			AgendaDAO agendaDAO = new AgendaDAO();
			animais = animalDAO.listarTudo();
			agendas = agendaDAO.listarTudo();

			horarios = horarioAgendaDAO.listarTudo();

			Messages.addGlobalInfo("horario salvo ");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao salvar um horario");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			horario = (HorarioAgenda) evento.getComponent().getAttributes().get("horarioSelecionada");

			HorarioAgendaDAO horarioAgendaDAO = new HorarioAgendaDAO();
			horarioAgendaDAO.excluir(horario);

			horarios = horarioAgendaDAO.listarTudo();

			Messages.addGlobalInfo("horario removido");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao remover");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			horario = (HorarioAgenda) evento.getComponent().getAttributes().get("horarioSelecionada");

			AnimalDAO animalDAO = new AnimalDAO();
			AgendaDAO agendaDAO = new AgendaDAO();
			animais = animalDAO.listarTudo();
			agendas = agendaDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao editar um horario");
			erro.printStackTrace();
		}
	}

	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String funcionarioNome = (String) filtros.get("agenda.funcionario.nome");

			String caminho = Faces.getRealPath("/relatorios/agendamentos.jasper");

			Map<String, Object> parametros = new HashMap<>();

			if (funcionarioNome == null) {
				parametros.put("Funcionario", "%%");
			} else {
				parametros.put("Funcionario", "%" + funcionarioNome + "%");
			}

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			
			JasperPrint print = JasperFillManager.fillReport(caminho, parametros, conexao);
			
			JasperExportManager.exportReportToPdfFile(print, "c:/RelatorioHorarios.pdf");	

			JasperPrintManager.printReport(relatorio, true);
		} catch (JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao gerar o relatório");
			erro.printStackTrace();
		}
	}

}
