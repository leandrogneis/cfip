package edu.cfip.app.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.cfip.core.config.PersistenceConfig;
import edu.cfip.core.dao.Repositorio;
import edu.cfip.core.model.Conta;

public class Context {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
		Repositorio dao = context.getBean(Repositorio.class);
		Conta conta = new Conta();
		conta.setNome("SALARIO 2");
		conta.setSigla("SAL");
		conta.setUsuario(1);
		dao.incluir(conta);
	}
}
