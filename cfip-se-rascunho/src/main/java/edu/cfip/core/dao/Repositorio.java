package edu.cfip.core.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.cfip.util.desktop.TipoOperacao;
@Repository
public class Repositorio {
	private static Logger LOG = Logger.getLogger(Repositorio.class.getName());
	@PersistenceContext(unitName = "PU_CFIP")
	private EntityManager manager;
	
	@Transactional
	public void gravar(TipoOperacao operacao, Object entidade) {
		if (TipoOperacao.INCLUSAO == operacao)
			manager.persist(entidade);
		else
			manager.merge(entidade);
	}
	@Transactional
	public void incluir(Object entidade) {
		manager.persist(entidade);
	}

	@Transactional
	public Object alterar(Object entidade) {
		return manager.merge(entidade);
	}
}
