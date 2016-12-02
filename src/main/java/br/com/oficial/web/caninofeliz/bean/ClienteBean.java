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

import br.com.oficial.web.caninofeliz.dao.EnderecoDAO;
import br.com.oficial.web.caninofeliz.dao.TelefoneDAO;
import br.com.oficial.web.caninofeliz.dao.ClienteDAO;
import br.com.oficial.web.caninofeliz.domain.Endereco;
import br.com.oficial.web.caninofeliz.domain.Telefone;
import br.com.oficial.web.caninofeliz.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import br.com.oficial.web.caninofeliz.domain.Cliente;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {
	private Cliente cliente;
	private List<Cliente> clientes;
	private Telefone telefone;
	private List<Telefone> telefones;
	private List<Endereco> enderecos;
	private Endereco endereco;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@PostConstruct
	public void listar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao listar os clientes");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			cliente = new Cliente();

			telefone = new Telefone();

			endereco = new Endereco();

			EnderecoDAO enderecoDAO = new EnderecoDAO();
			enderecos = enderecoDAO.listarTudo();
			TelefoneDAO telefoneDAO = new TelefoneDAO();
			telefones = telefoneDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao criar um novo cliente");
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
			Messages.addFlashGlobalError("Erro ao salvar o telefone");
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
			Messages.addFlashGlobalError("Erro ao salvar o endereco");
			erro.printStackTrace();
		}

	}

	public void salvar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.merge(cliente);

			cliente = new Cliente();

			EnderecoDAO enderecoDAO = new EnderecoDAO();
			enderecos = enderecoDAO.listarTudo();
			TelefoneDAO telefoneDAO = new TelefoneDAO();
			telefones = telefoneDAO.listarTudo();

			clientes = clienteDAO.listarTudo();

			Messages.addGlobalInfo("Funcionario salvo");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao salvar um funcionario");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionada");

			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.excluir(cliente);

			clientes = clienteDAO.listarTudo();

			Messages.addGlobalInfo("Cliente removido");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao remover o cliente");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionada");

			EnderecoDAO enderecoDAO = new EnderecoDAO();
			enderecos = enderecoDAO.listarTudo();
			TelefoneDAO telefoneDAO = new TelefoneDAO();
			telefones = telefoneDAO.listarTudo();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao editar o cliente");
			erro.printStackTrace();
		}
	}

	// Metodo que vai imprimir o relatorio - ele recebe um datatable que vai
	// obter os resultados do form da camada de vizualização
	// Apos isso ele pega o filtro atraves do nome dele, que foi adicionado
	// atraves de um parametro com o banco de dados utilizando o jasperreport
	// Ele captura também o caminho aonde fica o relatorio, e apos ele adiciona
	// tudo os parametros em um relatorio e chama a opção de impressao
	// Que no mesmo pode selecionar para salvar no computador
	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String funcionarioNome = (String) filtros.get("cliente.nome");

			String caminho = Faces.getRealPath("/relatorios/clientes.jasper");

			Map<String, Object> parametros = new HashMap<>();

			if (funcionarioNome == null) {
				parametros.put("Funcionario", "%%");
			} else {
				parametros.put("Funcionario", "%" + funcionarioNome + "%");
			}

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			
			JasperPrint print = JasperFillManager.fillReport(caminho, parametros, conexao);
			
			JasperExportManager.exportReportToPdfFile(print, "c:/RelatorioCliente.pdf");	

			JasperPrintManager.printReport(relatorio, true);
		} catch (JRException erro) {
			Messages.addGlobalError("Erro ao criar o relatorio");
			erro.printStackTrace();
		}
	}

}
