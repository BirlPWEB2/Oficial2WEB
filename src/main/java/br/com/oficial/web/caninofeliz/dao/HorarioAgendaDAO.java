package br.com.oficial.web.caninofeliz.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.oficial.web.caninofeliz.domain.HorarioAgenda;
import br.com.oficial.web.caninofeliz.util.HibernateUtil;

/**
 * 
 * @author Administrador Esta classe além de estender todas as funcionalidades
 *         da GenericDAO Ela possui uma busca personalizada para encontrar
 *         apenas os horarios livres ou seja, sem agendamento... Utilizando
 *         Hibernate e iterator para verifica atraves de uma lista se os
 *         horarios ja possuem um animal cadastrado.
 *
 */
public class HorarioAgendaDAO implements Persistivel<HorarioAgenda> {
	@SuppressWarnings("unchecked")
	public List<HorarioAgenda> listarTudoHorarioLivres() {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = factory.createCriteria(HorarioAgenda.class);
			List<HorarioAgenda> resultado = consulta.list();
			List<HorarioAgenda> resultadoLivres = verificaHorarioLivre(resultado);
			return resultadoLivres;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			factory.close();
		}
	}

	private List<HorarioAgenda> verificaHorarioLivre(List<HorarioAgenda> horarios) {
		for (Iterator<HorarioAgenda> i = horarios.iterator(); i.hasNext();) {
			HorarioAgenda horario = i.next();
			if (horario.getAnimal() != null) {
				i.remove();
			}
		}
		return horarios;
	}

	public void save(HorarioAgenda horarioAgenda) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.save(horarioAgenda);
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
	public List<HorarioAgenda> listarTudo() {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = factory.createCriteria(HorarioAgenda.class);
			List<HorarioAgenda> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			factory.close();
		}
	}

	public HorarioAgenda buscarComCodigo(Long codigo) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = factory.createCriteria(HorarioAgenda.class);
			consulta.add(Restrictions.idEq(codigo));
			HorarioAgenda resultado = (HorarioAgenda) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			factory.close();
		}
	}

	public void excluir(HorarioAgenda horarioAgenda) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.delete(horarioAgenda);
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

	public void update(HorarioAgenda horarioAgenda) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.update(horarioAgenda);
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
	public HorarioAgenda merge(HorarioAgenda horarioAgenda) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			HorarioAgenda retorno = (HorarioAgenda) factory.merge(horarioAgenda);
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
