package br.com.oficial.web.caninofeliz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.oficial.web.caninofeliz.domain.Item;
import br.com.oficial.web.caninofeliz.domain.ItemVenda;
import br.com.oficial.web.caninofeliz.domain.Venda;
import br.com.oficial.web.caninofeliz.util.HibernateUtil;

/**
 * 
 * @author Administrador Extende de genericDAO herdandn todas suas
 *         funcionalidades CRUD, mas ainda tem um metodo personalizado para
 *         poder gravar os item na tabela de entidade fraca criada para fazer
 *         relacionamento N para N, e verifica se o item tem em quantidade para a venda
 *
 */
public class VendaDAO implements Persistivel<Venda> {
	public void salvar(Venda venda, List<ItemVenda> itensVenda) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			sessao.save(venda);

			for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
				ItemVenda itemVenda = itensVenda.get(posicao);
				itemVenda.setVenda(venda);

				sessao.save(itemVenda);

				Item item = itemVenda.getItem();
				int quantidade = item.getQuantidade() - itemVenda.getQuantidade();
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
	
	public void save(Venda venda) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.save(venda);
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
	public List<Venda> listarTudo() {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = factory.createCriteria(Venda.class);
			List<Venda> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			factory.close();
		}
	}

	public Venda buscarComCodigo(Long codigo) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = factory.createCriteria(Venda.class);
			consulta.add(Restrictions.idEq(codigo));
			Venda resultado = (Venda) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			factory.close();
		}
	}

	public void excluir(Venda venda) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.delete(venda);
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

	public void update(Venda venda) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			factory.update(venda);
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
	public Venda merge(Venda venda) {
		Session factory = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = factory.beginTransaction();
			Venda retorno = (Venda) factory.merge(venda);
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