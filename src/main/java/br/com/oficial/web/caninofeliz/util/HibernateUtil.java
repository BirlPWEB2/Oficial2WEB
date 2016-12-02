package br.com.oficial.web.caninofeliz.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.service.ServiceRegistry;

/**
 * 
 * @author Leandro 
 * Classe utilizada para criar uma conexão com o banco de dados
 * utilizando o Hibernate
 * 
 */

public class HibernateUtil {

	// variavel que vai receber uma sessão
	private static SessionFactory sessionFactory = createSessionFactory();

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	//Metodo para capturar a conexao caso precisar.
	public static Connection getConexao(){
		Session sessao = sessionFactory.openSession();
		
		Connection conexao = sessao.doReturningWork(new ReturningWork<Connection>() {
			@Override
			public Connection execute(Connection conn) throws SQLException {
				return conn;
			}
		});
		
		return conexao;
	}

	// Metodo que vai abrir uma sessão com o banco de dados
	private static SessionFactory createSessionFactory() {
		try {

			// Pega a configuração do hibernate.cfg.xml
			Configuration configuration = new Configuration().configure();

			// Registra o serviço pegando as configuração
			ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
					.build();

			// Abre uma sessão com a configuração e pega o registro como
			// parametro
			SessionFactory factory = configuration.buildSessionFactory(registry);

			return factory;
		} catch (Throwable ex) {
			System.err.println("Houve erro na abertura da sessao com banco de dados." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}