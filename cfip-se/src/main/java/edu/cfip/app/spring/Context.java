package edu.cfip.app.spring;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.cfip.core.config.PersistenceConfig;
import edu.cfip.core.dao.Repositorio;
import edu.cfip.core.dao.RepositorioLancamento;
import edu.cfip.core.model.Lancamento;
import edu.cfip.core.model.filter.Filtro;

public class Context {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
		RepositorioLancamento dao = context.getBean(RepositorioLancamento.class);
		
		Filtro filtro = new Filtro();
		filtro.setUsuario(1);
		filtro.setPrevisao(true);
		
		List<Lancamento> lista = dao.listarLancamentos(filtro);
		imprimir(lista);
	}

	static void imprimir(List lista) {
		for (Object item : lista) {
			System.out.println(item);
		}
	}
}
