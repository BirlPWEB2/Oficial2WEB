package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.oficial.web.caninofeliz.dao.ClienteDAO;
import br.com.oficial.web.caninofeliz.dao.FuncionarioDAO;
import br.com.oficial.web.caninofeliz.dao.ItemDAO;
import br.com.oficial.web.caninofeliz.dao.VendaDAO;
import br.com.oficial.web.caninofeliz.domain.Cliente;
import br.com.oficial.web.caninofeliz.domain.Funcionario;
import br.com.oficial.web.caninofeliz.domain.Item;
import br.com.oficial.web.caninofeliz.domain.ItemVenda;
import br.com.oficial.web.caninofeliz.domain.Venda;

/**
 * 
 * @author Administrador Está classe é responsavel por controlar o objeto Venda,
 *         tendo nela metodos que vão adicionar em uma lista os item que vão ser
 *         vendidos ou removido, tendo também a funcionalidade que calcula e
 *         verifica se o item ainda tem em estoque
 *
 */
@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaBean implements Serializable {
	private Venda venda;
	private List<Item> itens;
	private List<ItemVenda> itensVenda;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;
	private List<Venda> vendas;

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	@PostConstruct
	public void novo() {
		try {
			venda = new Venda();
			venda.setPrecoTotal(new BigDecimal("0.00"));

			ItemDAO itemDAO = new ItemDAO();
			itens = itemDAO.listarTudo();

			itensVenda = new ArrayList<>();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("erro ao criar uma nova venda");
			erro.printStackTrace();
		}
	}

	//Este metodo é usado para adicionar um item à venda
	public void adicionar(ActionEvent evento) {
		Item item = (Item) evento.getComponent().getAttributes().get("itemSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getItem().equals(item)) {
				achou = posicao;
			}
		}

		if (achou < 0) {
			ItemVenda itemVenda = new ItemVenda();
			itemVenda.setPrecoParcial(item.getPreco());
			itemVenda.setItem(item);
			itemVenda.setQuantidade(new Short("1"));

			itensVenda.add(itemVenda);
		} else {
			ItemVenda itemVenda = itensVenda.get(achou);
			itemVenda.setQuantidade(new Short(itemVenda.getQuantidade() + 1 + ""));
			itemVenda.setPrecoParcial(item.getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
		}

		calcular();
	}

	//Este metodo é utilizado caso queira remover um item da venda antes de finalizada
	public void remover(ActionEvent evento) {
		ItemVenda itemVenda = (ItemVenda) evento.getComponent().getAttributes().get("itemSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getItem().equals(itemVenda.getItem())) {
				achou = posicao;
			}
		}

		if (achou > -1) {
			itensVenda.remove(achou);
		}

		calcular();
	}

	//Este metodo calcula toda vez que é adicionado ou removido o valor total da venda
	public void calcular() {
		venda.setPrecoTotal(new BigDecimal("0.00"));

		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			ItemVenda itemVenda = itensVenda.get(posicao);
			venda.setPrecoTotal(venda.getPrecoTotal().add(itemVenda.getPrecoParcial()));
		}
	}

	//Este metodo é usado para finalizar a venda
	public void finalizar() {
		try {
			venda.setHorario(new Date());
			venda.setCliente(null);
			venda.setFuncionario(null);

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarTudo();

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("erro ao  finalizar a venda");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (venda.getPrecoTotal().signum() == 0) {
				Messages.addGlobalError("Um item tem que estar na venda");
				return;
			}

			VendaDAO vendaDAO = new VendaDAO();
			vendaDAO.salvar(venda, itensVenda);

			venda = new Venda();
			venda.setPrecoTotal(new BigDecimal("0.00"));

			ItemDAO itemDAO = new ItemDAO();
			itens = itemDAO.listarTudo();

			itensVenda = new ArrayList<>();

			Messages.addGlobalInfo("Venda realizada");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("erro ao salvar a venda");
			erro.printStackTrace();
		}
	}

}