package edu.cfip.core.model;

import java.io.Serializable;


public class Conta implements Serializable{
	private Integer id;
	private String nome;
	private String sigla;
	private Double saldo;
	private boolean excluido;
	private Integer usuario;
	private Double saldoInicial;
	public Conta(String nome, String sigla, Double saldo) {
		super();
		this.nome = nome;
		this.sigla = sigla;
		this.saldo = saldo;
	}

	public Conta() {
		saldo=0.0d;
		saldoInicial=0.0d;
	}
	
	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}
	public boolean isExcluido() {
		return excluido;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public Integer getUsuario() {
		return usuario;
	}
	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}
	public Double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(Double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	@Override
	public String toString() {
		return "Conta [nome=" + nome + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
