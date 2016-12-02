package br.com.oficial.web.caninofeliz.util;

import org.hibernate.Session;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * @author Administrador
 * Classe Utilizada para Teste da Conexão com o hibernate
 *
 */
public class HibernateUtilTest {
	
	@Test //Metodo que vai testar a conexão com o banco de dados
	@Ignore
	public void conectar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		sessao.close();
		HibernateUtil.getSessionFactory().close();
	}

}
