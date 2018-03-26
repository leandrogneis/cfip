package edu.cfip.app.spring;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.cfip.core.config.PersistenceConfig;
import edu.cfip.core.dao.Repositorio;
import edu.cfip.core.dao.springjpa.ContaRepositorio;
import edu.cfip.core.dao.springjpa.UsuarioRepositorio;
import edu.cfip.core.model.Conta;
import edu.cfip.core.model.Usuario;

public class Context {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
		Repositorio dao = context.getBean(Repositorio.class);
		ContaRepositorio repositorio = context.getBean(ContaRepositorio.class);
		Conta conta = repositorio.findById(4);
		if (conta != null)
			System.out.println(conta.getNome());

		List<Conta> contas = repositorio.findByUsuarioAndNome(1, "%C%");
		imprimir(contas);

		contas = repositorio.customFindByUsuarioAndNome(1, "%C%");
		imprimir(contas);

		UsuarioRepositorio usuRepo = context.getBean(UsuarioRepositorio.class);
		Usuario usuario = new Usuario();
		/*
		 * usuario.setNome("Gleyson"); usuario.setLogin("gso"); usuario.setSenha("123");
		 * usuario.setEmail("gleyson.s@hotmail.com"); usuRepo.save(usuario);
		 * 
		 * //usuario = usuRepo.login("gso", "123"); usuario =
		 * usuRepo.findFistByLogin("gso");
		 * 
		 * if(usuario!=null) System.out.println(usuario.getNome());
		 * 
		 */
	}

	static void imprimir(List lista) {
		for (Object item : lista) {
			System.out.println(item);
		}
	}
}
