package br.com.oficial.web.caninofeliz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.oficial.web.caninofeliz.domain.Pessoa;
import br.com.oficial.web.caninofeliz.util.HibernateUtil;

/**
 * 
 * @author Administrador Função não implementada (Fazer uma busca por login para
 *         realizar a entrada do usuario no sistema)
 *
 */

public class PessoaDAO implements Persistivel<Pessoa> {

	public Pessoa buscarComLogin(String login) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = factory.createCriteria(Pessoa.class);
			consulta.add(Restrictions.idEq(login));
			Pessoa resultado = (Pessoa) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			factory.close();
		}
	}

	public void save(Pessoa pessoa) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.save(pessoa);
			transaction.commit();
		} catch (RuntimeException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw ex;
		} finally {
			factory.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> listarTudo() {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = factory.createCriteria(Pessoa.class);
			List<Pessoa> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			factory.close();
		}
	}

	public Pessoa buscarComCodigo(Long codigo) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = factory.createCriteria(Pessoa.class);
			consulta.add(Restrictions.idEq(codigo));
			Pessoa resultado = (Pessoa) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			factory.close();
		}
	}

	public void excluir(Pessoa pessoa) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.delete(pessoa);
			transaction.commit();
		} catch (RuntimeException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw ex;
		} finally {
			factory.close();
		}
	}

	public void update(Pessoa pessoa) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.update(pessoa);
			transaction.commit();
		} catch (RuntimeException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw ex;
		} finally {
			factory.close();
		}
	}

	// Metodo merge, ele é um save e update... Caso o objeto não exista ele
	// salva, caso exista ele faz um update.
	public Pessoa merge(Pessoa pessoa) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			Pessoa retorno = (Pessoa) factory.merge(pessoa);
			transaction.commit();
			return retorno;
		} catch (RuntimeException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw ex;
		} finally {
			factory.close();
		}
	}

}
