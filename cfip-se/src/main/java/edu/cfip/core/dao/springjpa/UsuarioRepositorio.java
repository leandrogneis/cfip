package edu.cfip.core.dao.springjpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.cfip.core.model.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
	//4
	public Usuario findFistByLogin(String login);
	//...
	@Query(value="SELECT e FROM Usuario e WHERE e.login = :login and e.senha like :senha")
	public Usuario login(@Param("login") String login, @Param("senha") String senha);
}
