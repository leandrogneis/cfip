package com.boxs.cfip.core.dao;

import java.text.MessageFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.boxs.cfip.core.model.Conta;

@Repository
public class EntidadesNew {
	private static Logger LOG = Logger.getLogger(EntidadesNew.class.getName());
	@PersistenceContext(unitName = "PU_CFIP")
	private EntityManager manager;
	private final String SQL_PADRAO = "SELECT e FROM {0} e WHERE e.excluido = false AND e.usuario = :usuario ";
	
	private String select(Class entidade) {
		MessageFormat sql = new MessageFormat(SQL_PADRAO);
		String select = sql.format(new Object[] {entidade.getName()});
		System.out.println(select);
		return select;
	}
	public List<Conta> listarContas(Integer usuario){
		Query query = manager.createQuery(select(Conta.class));
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}
	
	
	
	/*private String select(Class entidade) {
	return select(entidade.getName());
}
private String select(String entidade) {
	MessageFormat sql = new MessageFormat(SQL_PADRAO);
	String select = sql.format(new Object[] {entidade});
	return select;
}*/
}
