package br.com.oficial.web.caninofeliz.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.oficial.web.caninofeliz.domain.Estado;

public class EstadoDAOTest {
	@Test
	@Ignore
	public void salvar() {
		Estado estado = new Estado();
		estado.setNome("Santa Catarina");
		estado.setSigla("SC");

		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.save(estado);
	}

	@Test
	@Ignore
	public void listarTudo() {
		EstadoDAO estadoDAO = new EstadoDAO();
		List<Estado> resultado = estadoDAO.listarTudo();

		System.out.println("Total de Registros Encontrados: " + resultado.size());

		for (Estado estado : resultado) {
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
	}
	
	@Test
	@Ignore
	public void buscarPorCodigo(){
		Long codigo = 3L;
		
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscarComCodigo(codigo);
		
		if(estado == null){
			System.out.println("Nenhum registro encontrado");
		}else{
			System.out.println("Registro encontrado:");
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
	}
	
	@Test
	@Ignore
	public void excluir(){
		Long codigo = 1L;
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscarComCodigo(codigo);
		
		if(estado == null){
			System.out.println("Nenhum registro encontrado");
		}else{
			estadoDAO.excluir(estado);
			System.out.println("Registro removido:");
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
	}
	
	@Test
	@Ignore
	public void update(){
		Long codigo = 10L;
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscarComCodigo(codigo);
		
		if(estado == null){
			System.out.println("Nenhum registro encontrado");
		}else{
			System.out.println("Registro editado - Antes:");
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
			
			estado.setNome("Santa Catarina");
			estado.setSigla("SC");
			estadoDAO.update(estado);
			
			System.out.println("Registro editado - Depois:");
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
	}

}
