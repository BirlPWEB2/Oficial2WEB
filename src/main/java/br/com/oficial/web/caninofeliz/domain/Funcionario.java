package br.com.oficial.web.caninofeliz.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Funcionario extends Pessoa {

	@ManyToOne
	@JoinColumn(nullable = false)
	private Cargo cargo;
	
	@Column(nullable = false, precision = 6, scale = 2)
	private BigDecimal salario;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataEntrada;

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

}
