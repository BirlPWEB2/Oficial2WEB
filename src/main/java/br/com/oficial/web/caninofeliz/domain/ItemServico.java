package br.com.oficial.web.caninofeliz.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 
 * @author Administrador Está classe funciona apenas como uma entidade de
 *         ligação de relacionamentos N para N no esquema relacional sendo
 *         assim, ela vai estar ligada com servico e os item, possuindo os item,
 *         quantidade que é utilizado para o servico;
 *
 */
@SuppressWarnings("serial")
@Entity
public class ItemServico implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@Column(nullable = false)
	private Short quantidade;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Item item;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Servico servico;

	public Short getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

}
