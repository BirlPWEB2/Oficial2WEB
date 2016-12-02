package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;

import br.com.oficial.web.caninofeliz.dao.VendaDAO;
import br.com.oficial.web.caninofeliz.domain.Venda;
import br.com.oficial.web.caninofeliz.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

/**
 * 
 * @author Administrador Está classe é responsavel por controlar a tela de
 *         impressão dos relatorios de vendas
 *
 */

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaHistoricoBean implements Serializable {

	private List<Venda> vendas;

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	@PostConstruct
	public void listar() {
		VendaDAO vendaDAO = new VendaDAO();
		vendas = vendaDAO.listarTudo();
	}

	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String funcionarioNome = (String) filtros.get("venda.funcionario.nome");

			String caminho = Faces.getRealPath("/relatorios/vendas.jasper");

			Map<String, Object> parametros = new HashMap<>();

			if (funcionarioNome == null) {
				parametros.put("Funcionario", "%%");
			} else {
				parametros.put("Funcionario", "%" + funcionarioNome + "%");
			}

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			
			JasperPrint print = JasperFillManager.fillReport(caminho, parametros, conexao);
			
			JasperExportManager.exportReportToPdfFile(print, "c:/RelatorioVenda.pdf");	

			JasperPrintManager.printReport(relatorio, true);
		} catch (JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao gerar o relatório");
			erro.printStackTrace();
		}
	}

}
