package br.com.oficial.web.caninofeliz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.oficial.web.caninofeliz.domain.Item;
import br.com.oficial.web.caninofeliz.domain.ItemServico;
import br.com.oficial.web.caninofeliz.domain.Servico;
import br.com.oficial.web.caninofeliz.util.HibernateUtil;

/**
 * 
 * @author Administrador Extende de genericDAO herdandn todas suas
 *         funcionalidades CRUD, mas ainda tem um metodo personalizado para
 *         poder gravar os item na tabela de entidade fraca criada para fazer
 *         relacionamento N para N
 *
 */
public class ServicoDAO implements Persistivel<Servico> {
	public void salvar(Servico servico, List<ItemServico> itensServico) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			sessao.save(servico);

			for (int posicao = 0; posicao < itensServico.size(); posicao++) {
				ItemServico itemServico = itensServico.get(posicao);
				itemServico.setServico(servico);

				sessao.save(itemServico);

				Item item = itemServico.getItem();
				int quantidade = item.getQuantidade() - itemServico.getQuantidade();
				if (quantidade >= 0) {
					item.setQuantidade(new Short(quantidade + ""));
					sessao.update(item);
				} else {
					throw new RuntimeException("Quantidade insuficiente em estoque");
				}
			}

			transacao.commit();
		} catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public void save(Servico servico) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.save(servico);
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
	public List<Servico> listarTudo() {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = factory.createCriteria(Servico.class);
			List<Servico> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			factory.close();
		}
	}

	public Servico buscarComCodigo(Long codigo) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = factory.createCriteria(Servico.class);
			consulta.add(Restrictions.idEq(codigo));
			Servico resultado = (Servico) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			factory.close();
		}
	}

	public void excluir(Servico servico) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.delete(servico);
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

	public void update(Servico servico) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.update(servico);
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
	public Servico merge(Servico servico) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			Servico retorno = (Servico) factory.merge(servico);
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
