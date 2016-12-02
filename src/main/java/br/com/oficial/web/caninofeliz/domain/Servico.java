package br.com.oficial.web.caninofeliz.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 
 * @author Administrador A classe servico, vai possuir uma descricao do tipo do
 *         servico, um preço, e os item que vai ser utilizado para realização do
 *         mesmo
 *
 */
@SuppressWarnings("serial")
@Entity
public class Servico implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@Column(length = 200, nullable = false)
	private String descricao;

	@Column(nullable = false)
	private BigDecimal preco;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "servico")
	private List<ItemServico> itensServico;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "servico")
	private List<ServicoOrdem> servicosOrdem;

	public List<ServicoOrdem> getServicosOrdem() {
		return servicosOrdem;
	}

	public void setServicosOrdem(List<ServicoOrdem> servicosOrdem) {
		this.servicosOrdem = servicosOrdem;
	}

	public List<ItemServico> getItensServico() {
		return itensServico;
	}

	public void setItensServico(List<ItemServico> itensServico) {
		this.itensServico = itensServico;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

}
