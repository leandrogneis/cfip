package edu.porgamdor.util.desktop.ambiente;

public class Sessao {
	private Perfil perfil;
	private String ambiente;
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public String getAmbiente() {
		return ambiente;
	}
}
