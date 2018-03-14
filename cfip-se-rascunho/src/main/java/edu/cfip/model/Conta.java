package edu.cfip.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Conta implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false,length=50)
	private String nome;
	@Column(nullable=false,length=20)
	private String sigla;
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="saldo_inicial")
	private Saldo saldoInicial;
	@Column(nullable=false,length=9,precision=2)
	private Double saldo;
	private boolean excluido;
	@Column(name="usuario_id", nullable=false, length=9)
	private Integer usuario;

	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	public Conta() {
		dataInicio = new Date();
		saldo=0.0d;
		this.saldoInicial = new Saldo();
		this.saldoInicial.setConta(this);
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
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Integer getUsuario() {
		return usuario;
	}
	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public Saldo getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(Saldo saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	public void setValorSaldoInicial(Double saldoInicial) {
		this.saldoInicial.setValor(saldoInicial);
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
