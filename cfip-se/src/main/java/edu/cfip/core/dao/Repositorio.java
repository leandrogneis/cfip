package edu.cfip.core.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.cfip.core.model.Conta;
import edu.cfip.core.model.Natureza;
import edu.cfip.core.model.TipoMovimento;
import edu.porgamdor.util.desktop.ambiente.TipoOperacao;

@Repository
public class Repositorio {
	private static Logger LOG = Logger.getLogger(Repositorio.class.getName());
	@PersistenceContext(unitName = "PU_CFIP")
	private EntityManager manager;
	
	@Transactional
	public void incluir(Object entidade) {
		manager.persist(entidade);
	}

	@Transactional
	public Object alterar(Object entidade) {
		return manager.merge(entidade);
	}
	
	@Transactional
	public void gravar(TipoOperacao operacao, Object entidade) {
		if (TipoOperacao.INCLUSAO == operacao)
			manager.persist(entidade);
		else
			manager.merge(entidade);
	}
	public <T> T buscar(Class entidade, Integer id) {
		return (T) manager.find(entidade, id);
	}
	
	public List<Conta> listarContas(Integer usuario) {
		Query query = manager.createQuery("SELECT e FROM Conta e WHERE e.excluido = false and e.usuario = :usuario ORDER BY e.nome");
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}
	public List<Conta> listarContas(Integer usuario, String nome) {
		Query query = manager.createQuery("SELECT e FROM Conta e WHERE e.excluido = false and e.usuario = :usuario and e.nome like :nome");
		query.setParameter("usuario", usuario);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}
	
	public List<Natureza> listarNaturezas(Integer usuario, String nome) {
		Query query = manager.createQuery(
				"SELECT e FROM Natureza e WHERE e.excluido = false and e.usuario = :usuario AND e.nome LIKE :nome ORDER BY e.nome");
		query.setParameter("usuario", usuario);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}

	public List<Natureza> listarNaturezas(Integer usuario) {
		Query query = manager.createQuery("SELECT e FROM Natureza e WHERE e.excluido = false and e.usuario = :usuario ORDER BY e.tipoMovimento, e.nome");
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}
	public List<Natureza> listarNaturezas(Integer usuario,TipoMovimento tipo) {
		Query query = manager.createQuery("SELECT e FROM Natureza e WHERE e.excluido = false AND e.usuario = :usuario AND e.tipoMovimento=:tipoMovto ORDER BY e.nome");
		query.setParameter("usuario", usuario);
		query.setParameter("tipoMovto", tipo);
		return query.getResultList();
	}
}
