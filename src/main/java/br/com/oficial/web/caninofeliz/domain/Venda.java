package br.com.oficial.web.caninofeliz.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Administrador A classe venda, é uma classe que vai ser construida a
 *         venda em sí, contendo o horario que ela foi realizada preco total da
 *         venda, o cliente que comprou e o funcionario que a vendeu.
 *
 */
@SuppressWarnings("serial")
@Entity
public class Venda implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date horario;

	@Column(nullable = false, precision = 7, scale = 2)
	private BigDecimal precoTotal;

	@ManyToOne
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "venda")
	private List<ItemVenda> itensVenda;

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public BigDecimal getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(BigDecimal precoTotal) {
		this.precoTotal = precoTotal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

}