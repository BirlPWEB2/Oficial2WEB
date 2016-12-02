package br.com.oficial.web.caninofeliz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.oficial.web.caninofeliz.domain.OrdemServico;
import br.com.oficial.web.caninofeliz.util.HibernateUtil;

public class OrdemServicoDAO implements Persistivel<OrdemServico> {

	public void save(OrdemServico ordemServico) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.save(ordemServico);
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
	public List<OrdemServico> listarTudo() {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = factory.createCriteria(OrdemServico.class);
			List<OrdemServico> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			factory.close();
		}
	}

	public OrdemServico buscarComCodigo(Long codigo) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = factory.createCriteria(OrdemServico.class);
			consulta.add(Restrictions.idEq(codigo));
			OrdemServico resultado = (OrdemServico) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			factory.close();
		}
	}

	public void excluir(OrdemServico ordemServico) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.delete(ordemServico);
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

	public void update(OrdemServico ordemServico) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.update(ordemServico);
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
	public OrdemServico merge(OrdemServico ordemServico) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			OrdemServico retorno = (OrdemServico) factory.merge(ordemServico);
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
