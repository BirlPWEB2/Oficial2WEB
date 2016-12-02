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

import br.com.oficial.web.caninofeliz.dao.FabricanteDAO;
import br.com.oficial.web.caninofeliz.dao.ItemDAO;
import br.com.oficial.web.caninofeliz.domain.Fabricante;
import br.com.oficial.web.caninofeliz.domain.Item;
import br.com.oficial.web.caninofeliz.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ItemBean implements Serializable {
	private Item item;
	private List<Item> itens;
	private List<Fabricante> fabricantes;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	@PostConstruct
	public void listar() {
		try {
			ItemDAO itemDAO = new ItemDAO();
			itens = itemDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("erro ao listar os itens");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			item = new Item();

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao criar um novo item");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			item = (Item) evento.getComponent().getAttributes().get("itemSelecionado");

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao editar um item");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			ItemDAO itemDAO = new ItemDAO();
			itemDAO.merge(item);

			item = new Item();

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listarTudo();

			itens = itemDAO.listarTudo();

			Messages.addGlobalInfo("Item salvo");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao salvar o item");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			item = (Item) evento.getComponent().getAttributes().get("itemSelecionado");

			ItemDAO itemDAO = new ItemDAO();
			itemDAO.excluir(item);

			itens = itemDAO.listarTudo();

			Messages.addGlobalInfo("Item removido");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("erro ao remover o item");
			erro.printStackTrace();
		}
	}
	
	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String itemNome = (String) filtros.get("item.descricao");

			String caminho = Faces.getRealPath("/relatorios/itens.jasper");
			

			Map<String, Object> parametros = new HashMap<>();
			
			
			if (itemNome == null) {
				parametros.put("DESCRICAO", "%%");
			} else {
				parametros.put("DESCRICAO", "%" + itemNome + "%");
			}

			Connection conexao =  HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			
			JasperPrint print = JasperFillManager.fillReport(caminho, parametros, conexao);
			
			JasperExportManager.exportReportToPdfFile(print, "c:/RelatorioItem.pdf");	

			JasperPrintManager.printReport(relatorio, true);
		} catch (JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao gerar o relatório");
			erro.printStackTrace();
		}
	}
}