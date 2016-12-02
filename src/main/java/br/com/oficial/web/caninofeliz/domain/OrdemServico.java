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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * 
 * @author Administrador Funcionalidade não implementada por falta de tempo.
 *
 */
@SuppressWarnings("serial")
@Entity
public class OrdemServico implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@Column(nullable = false)
	private BigDecimal valorTotal;

	@OneToOne
	@JoinColumn(nullable = false)
	private HorarioAgenda horarioAgenda;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "ordemServico")
	private List<ServicoOrdem> servicosOrdem;

	public List<ServicoOrdem> getServicosOrdem() {
		return servicosOrdem;
	}

	public void setServicosOrdem(List<ServicoOrdem> servicosOrdem) {
		this.servicosOrdem = servicosOrdem;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public HorarioAgenda getHorarioAgenda() {
		return horarioAgenda;
	}

	public void setHorarioAgenda(HorarioAgenda horarioAgenda) {
		this.horarioAgenda = horarioAgenda;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

}
