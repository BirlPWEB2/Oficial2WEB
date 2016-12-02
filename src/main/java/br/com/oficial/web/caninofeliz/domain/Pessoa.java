package br.com.oficial.web.caninofeliz.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

/**
 * 
 * @author Administrador Classe Pessoa, vai ser classe pai de clientes e
 *         funcionarios.
 *
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class Pessoa implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@Column(length = 80, nullable = false)
	private String nome;

	@Column(length = 80, nullable = false)
	private String sobrenome;

	@OneToOne
	@JoinColumn(nullable = false)
	private Telefone telefone;

	@OneToOne
	@JoinColumn(nullable = false)
	private Endereco endereco;

	@Column(length = 80, nullable = false)
	private String email;

	@Column(length = 12, nullable = false)
	private String rg;

	@Column(length = 14, nullable = false, unique = true)
	private String cpf;

	@Column(length = 10, nullable = false, unique = true)
	private String login;

	@Column(length = 10, nullable = false)
	private String senha;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Pessoa(String login, String senha) {
		super();
		this.login = login;
		this.senha = senha;
	}

	public Pessoa() {
		// TODO Auto-generated constructor stub
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

}
