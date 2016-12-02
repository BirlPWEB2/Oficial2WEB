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

import br.com.oficial.web.caninofeliz.dao.FuncionarioDAO;
import br.com.oficial.web.caninofeliz.dao.TelefoneDAO;
import br.com.oficial.web.caninofeliz.dao.CargoDAO;
import br.com.oficial.web.caninofeliz.dao.EnderecoDAO;
import br.com.oficial.web.caninofeliz.domain.Funcionario;
import br.com.oficial.web.caninofeliz.domain.Telefone;
import br.com.oficial.web.caninofeliz.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import br.com.oficial.web.caninofeliz.domain.Cargo;
import br.com.oficial.web.caninofeliz.domain.Endereco;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable {
	private Funcionario funcionario;
	private List<Funcionario> funcionarios;
	private List<Cargo> cargos;
	private Telefone telefone;
	private List<Telefone> telefones;
	private List<Endereco> enderecos;
	private Endereco endereco;

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	@PostConstruct
	public void listar() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao listar os funcionarios");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			funcionario = new Funcionario();

			telefone = new Telefone();

			endereco = new Endereco();

			CargoDAO cargoDAO = new CargoDAO();
			cargos = cargoDAO.listarTudo();
			EnderecoDAO enderecoDAO = new EnderecoDAO();
			enderecos = enderecoDAO.listarTudo();
			TelefoneDAO telefoneDAO = new TelefoneDAO();
			telefones = telefoneDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao criar um novo funcionario");
			erro.printStackTrace();
		}
	}

	public void salvarTelefone() {
		try {
			TelefoneDAO telefoneDAO = new TelefoneDAO();
			telefoneDAO.merge(telefone);
			telefones = telefoneDAO.listarTudo();

			Messages.addGlobalInfo("Telefone salvo");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao salvar o telefone");
			erro.printStackTrace();
		}
	}

	public void salvarEndereco() {
		try {
			EnderecoDAO enderecoDAO = new EnderecoDAO();
			enderecoDAO.merge(endereco);
			enderecos = enderecoDAO.listarTudo();

			Messages.addGlobalInfo("Endereco salvo");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao salvar o endereco");
			erro.printStackTrace();
		}

	}

	public void salvar() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.merge(funcionario);

			funcionario = new Funcionario();

			CargoDAO cargoDAO = new CargoDAO();
			cargos = cargoDAO.listarTudo();
			EnderecoDAO enderecoDAO = new EnderecoDAO();
			enderecos = enderecoDAO.listarTudo();
			TelefoneDAO telefoneDAO = new TelefoneDAO();
			telefones = telefoneDAO.listarTudo();

			funcionarios = funcionarioDAO.listarTudo();

			Messages.addGlobalInfo("Funcionario salvo");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao salvar um novo funcionario");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionada");

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.excluir(funcionario);

			funcionarios = funcionarioDAO.listarTudo();

			Messages.addGlobalInfo("Funcionario removido");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao remover a funcionario");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionada");

			CargoDAO cargoDAO = new CargoDAO();
			cargos = cargoDAO.listarTudo();
			EnderecoDAO enderecoDAO = new EnderecoDAO();
			enderecos = enderecoDAO.listarTudo();
			TelefoneDAO telefoneDAO = new TelefoneDAO();
			telefones = telefoneDAO.listarTudo();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao editarr um funcionario");
			erro.printStackTrace();
		}
	}
	
	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String funcionarioNome = (String) filtros.get("funcionario.nome");

			String caminho = Faces.getRealPath("/relatorios/funcionarios.jasper");
			

			Map<String, Object> parametros = new HashMap<>();
			
			
			if (funcionarioNome == null) {
				parametros.put("Cliente", "%%");
			} else {
				parametros.put("Cliente", "%" + funcionarioNome + "%");
			}

			Connection conexao =  HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			
			JasperPrint print = JasperFillManager.fillReport(caminho, parametros, conexao);
			
			JasperExportManager.exportReportToPdfFile(print, "c:/RelatorioFuncionario.pdf");	

			JasperPrintManager.printReport(relatorio, true);
		} catch (JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao gerar o relatório");
			erro.printStackTrace();
		}
	}
}
