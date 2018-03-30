package edu.porgamdor.util.desktop.ambiente;

public interface Perfil {
	void setId(Integer id);
	Integer getId();
	
	void setEmail(String email);
	String getEmail();
	
	void setCpf(String cpf);
	String getCpf();
	
	
	void setNome(String nome);
	String getNome();
	
	void setLogin(String login);
	String getLogin();
	
	void setSenha(String senha);
	String getSenha();
	
	void setTelefone(String telefone);
	String getTelefone();
}
