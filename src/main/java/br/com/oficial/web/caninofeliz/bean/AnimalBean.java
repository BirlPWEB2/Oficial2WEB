package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.oficial.web.caninofeliz.dao.AnimalDAO;
import br.com.oficial.web.caninofeliz.dao.ClienteDAO;
import br.com.oficial.web.caninofeliz.dao.EspecieDAO;
import br.com.oficial.web.caninofeliz.dao.RacaDAO;
import br.com.oficial.web.caninofeliz.domain.Animal;
import br.com.oficial.web.caninofeliz.domain.Cliente;
import br.com.oficial.web.caninofeliz.domain.Especie;
import br.com.oficial.web.caninofeliz.domain.Raca;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AnimalBean implements Serializable {
	// A classe Controladora deve ter variaveis de todas as classe que
	// contemplam o objeto dominio do qual ela controla.
	// Animal tem uma especie, ou seja a controladora deve ter uma variavel
	// especies para poder trazer todas para mandar a camada de vizualização
	// para listar ao usuario
	private Animal animal;
	private List<Animal> animais;
	private List<Cliente> clientes;
	private List<Raca> racas;
	private List<Especie> especies;

	public List<Raca> getRacas() {
		return racas;
	}

	public void setRacas(List<Raca> racas) {
		this.racas = racas;
	}

	public List<Especie> getEspecies() {
		return especies;
	}

	public void setEspecies(List<Especie> especies) {
		this.especies = especies;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public List<Animal> getAnimais() {
		return animais;
	}

	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	@PostConstruct
	public void listar() {
		try {
			AnimalDAO animalDAO = new AnimalDAO();
			animais = animalDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao listar os animais");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			animal = new Animal();

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarTudo();
			RacaDAO racaDAO = new RacaDAO();
			EspecieDAO especieDAO = new EspecieDAO();
			racas = racaDAO.listarTudo();
			especies = especieDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao criar um animal");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			AnimalDAO animalDAO = new AnimalDAO();
			animalDAO.merge(animal);

			animal = new Animal();

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarTudo();
			RacaDAO racaDAO = new RacaDAO();
			EspecieDAO especieDAO = new EspecieDAO();
			racas = racaDAO.listarTudo();
			especies = especieDAO.listarTudo();

			animais = animalDAO.listarTudo();

			Messages.addGlobalInfo("Animal salvo");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao salvar o animal");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			animal = (Animal) evento.getComponent().getAttributes().get("animalSelecionada");

			AnimalDAO animalDAO = new AnimalDAO();
			animalDAO.excluir(animal);

			animais = animalDAO.listarTudo();

			Messages.addGlobalInfo("Animal removido");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao remover o animal");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			animal = (Animal) evento.getComponent().getAttributes().get("animalSelecionada");

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarTudo();
			RacaDAO racaDAO = new RacaDAO();
			EspecieDAO especieDAO = new EspecieDAO();
			racas = racaDAO.listarTudo();
			especies = especieDAO.listarTudo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao editar o animal");
			erro.printStackTrace();
		}
	}
}
