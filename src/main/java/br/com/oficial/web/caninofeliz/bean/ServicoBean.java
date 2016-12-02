package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.oficial.web.caninofeliz.dao.ItemDAO;
import br.com.oficial.web.caninofeliz.dao.ServicoDAO;
import br.com.oficial.web.caninofeliz.domain.Item;
import br.com.oficial.web.caninofeliz.domain.ItemServico;
import br.com.oficial.web.caninofeliz.domain.Servico;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ServicoBean implements Serializable {

	private Servico servico;
	private List<Servico> servicos;
	private List<Item> itens;
	private List<ItemServico> itensServico;

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public List<ItemServico> getItensServico() {
		return itensServico;
	}

	public void setItensServico(List<ItemServico> itensServico) {
		this.itensServico = itensServico;
	}

	@PostConstruct
	public void novo() {
		try {
			servico = new Servico();

			ItemDAO itemDAO = new ItemDAO();
			itens = itemDAO.listarTudo();

			itensServico = new ArrayList<>();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela de servicoss");
			erro.printStackTrace();
		}
	}

	public void listar() {
		ItemDAO itemDAO = new ItemDAO();
		itens = itemDAO.listarTudo();
		ServicoDAO servicoDAO = new ServicoDAO();
		servicos = servicoDAO.listarTudo();
	}

	public void adicionar(ActionEvent evento) {
		Item item = (Item) evento.getComponent().getAttributes().get("itemSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensServico.size(); posicao++) {
			if (itensServico.get(posicao).getItem().equals(item)) {
				achou = posicao;
			}
		}

		if (achou < 0) {
			ItemServico itemServico = new ItemServico();
			itemServico.setItem(item);
			itemServico.setQuantidade(new Short("1"));

			itensServico.add(itemServico);
		} else {
			ItemServico itemServico = itensServico.get(achou);
			itemServico.setQuantidade(new Short(itemServico.getQuantidade() + 1 + ""));
		}

	}

	public void remover(ActionEvent evento) {
		ItemServico itemServico = (ItemServico) evento.getComponent().getAttributes().get("itemSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensServico.size(); posicao++) {
			if (itensServico.get(posicao).getItem().equals(itemServico.getItem())) {
				achou = posicao;
			}
		}

		if (achou > -1) {
			itensServico.remove(achou);
		}

	}

	public void salvar() {
		try {
			ServicoDAO servicoDAO = new ServicoDAO();
			servicoDAO.salvar(servico, itensServico);

			servico = new Servico();

			ItemDAO itemDAO = new ItemDAO();
			itens = itemDAO.listarTudo();

			itensServico = new ArrayList<>();

			Messages.addGlobalInfo("Servico Cadastrado");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o servico");
			erro.printStackTrace();
		}
	}

}
