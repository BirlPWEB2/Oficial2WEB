package br.com.oficial.web.caninofeliz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.oficial.web.caninofeliz.domain.Animal;
import br.com.oficial.web.caninofeliz.util.HibernateUtil;

public class AnimalDAO implements Persistivel<Animal> {

	public void save(Animal animal) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.save(animal);
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
	public List<Animal> listarTudo() {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = factory.createCriteria(Animal.class);
			List<Animal> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			factory.close();
		}
	}

	public Animal buscarComCodigo(Long codigo) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = factory.createCriteria(Animal.class);
			consulta.add(Restrictions.idEq(codigo));
			Animal resultado = (Animal) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			factory.close();
		}
	}

	public void excluir(Animal animal) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.delete(animal);
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

	public void update(Animal animal) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.update(animal);
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
	public Animal merge(Animal animal) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			Animal retorno = (Animal) factory.merge(animal);
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