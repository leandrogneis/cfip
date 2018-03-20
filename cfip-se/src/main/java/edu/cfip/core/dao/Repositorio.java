package edu.cfip.core.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.cfip.core.model.Conta;
import edu.porgamdor.util.desktop.TipoOperacao;

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
	
	public List<Conta> listarContas() {
		Query query = manager.createQuery("SELECT e FROM Conta e ORDER BY e.nome");
		return query.getResultList();
	}
}
