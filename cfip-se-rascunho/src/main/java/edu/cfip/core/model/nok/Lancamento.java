package edu.cfip.core.model.nok;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Lancamento implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private boolean excluido;
	@Column(name="usuario_id",nullable=false,length=9)
	private Integer usuario;
	@Column(nullable=false,length=500)
	private String descricao;
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date data;
	
	@Column(nullable=false,length=6)
	private Integer periodo;
	
	@Column(name="conta_id",nullable=false,length=9)
	private Integer conta;
	@Column(name="destino_id",length=9)
	private Integer destino;
	
	@Column(name="natureza_id",nullable=false,length=9)
	private Integer natureza;
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_mov_id",nullable=false,length=30)
	private TipoMovimento tipoMovimento;
	
	@Column(name="saldo_inicial",nullable=false,length=9,precision=2)
	private Double saldoInicial;
	@Column(nullable=false,length=9,precision=2)
	private Double valor;
	@Column(name="saldo_final",nullable=false,length=9,precision=2)
	private Double saldoFinal;
	
	private boolean previsao;
	private Integer parcelas;
	@Temporal(TemporalType.DATE)
	private Date quitacao;
	
	@Column(name="periodo_quitacao",length=6)
	private Integer periodoQuitacao;
	
	@Column(name="origem_credito_id")
	private Integer origemCredito;
	
	@Column(name="origem_lancto_id",length=9)
	private Integer origemLancamento;
	
	@Column(name="contato_id")
	private Integer contato;
	
	private boolean transferencia;
	
	@Column(name="valor_principal", nullable=false,length=9,precision=2)
	private Double valorPrincipal;
	
	@Column(name="juros_aliq", nullable=false,length=9,precision=2)
	private Double jurosAliquota;
	
	@Column(name="multa_aliq", nullable=false,length=9,precision=2)
	private Double multaAliquota;
	
	@Column(name="juros_valor", nullable=false,length=9,precision=2)
	private Double jurosValor;
	
	@Column(name="multa_valor", nullable=false,length=9,precision=2)
	private Double multaValor;
	
	@Column(name="desconto_aliq", nullable=false,length=9,precision=2)
	private Double descontoAliquota;
	
	@Column(name="desconto_valor", nullable=false,length=9,precision=2)
	private Double descontoValor;
	
	@Column(name="atualizacao_aliq", nullable=false,length=9,precision=2)
	private Double atualizacaoAliquota;
	
	@Column(name="atualizacao_valor",nullable=false,length=9,precision=2)
	private Double atualizacaoValor;
	
	@Column(length=9)
	private Integer fatura;
	
	@Transient
	private String parcelaIntervalo;
	public void setParcelaIntervalo(String parcelaIntervalo) {
		this.parcelaIntervalo = parcelaIntervalo;
	}
	public String getParcelaIntervalo() {
		return parcelaIntervalo;
	}
	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}
	public boolean isExcluido() {
		return excluido;
	}
	
	public Lancamento() {
		valor=0.0d;
		data = new Date();
		valor=0.0d;
		saldoFinal=0.0d;
		saldoFinal=0.0d;
		valorPrincipal=0.0d;
		atualizacaoAliquota=0.0d;
		atualizacaoValor=0.0d;
		jurosAliquota=0.0d;
		jurosValor=0.0d;
		multaAliquota=0.0d;
		multaValor=0.0d;
		descontoAliquota=0.0d;
		descontoValor=0.0d;
		parcelas=1;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Integer getConta() {
		return conta;
	}
	public void setConta(Integer conta) {
		this.conta = conta;
	}
	public Integer getUsuario() {
		return usuario;
	}
	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}
	public Integer getNatureza() {
		return natureza;
	}
	public void setNatureza(Integer natureza) {
		this.natureza = natureza;
	}
	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	public Double getSaldoInicial() {
		return saldoInicial;
	}
	public void setSaldoInicial(Double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public void calcularValorAtual() {
		if(tipoMovimento == TipoMovimento.CREDITO)
			valor = valorPrincipal + multaValor + jurosValor + atualizacaoValor - descontoValor;
		else if(tipoMovimento == TipoMovimento.DEBITO)
			valor = valorPrincipal - multaValor - jurosValor - atualizacaoValor + descontoValor;
	}
	public Double getSaldoFinal() {
		return saldoFinal;
	}
	public void setSaldoFinal(Double saldoFinal) {
		this.saldoFinal = saldoFinal;
	}
	public boolean isPrevisao() {
		return previsao;
	}
	public void setPrevisao(boolean previsao) {
		this.previsao = previsao;
	}
	public Date getQuitacao() {
		return quitacao;
	}
	public void setQuitacao(Date quitacao) {
		this.quitacao = quitacao;
	}
	public Integer getOrigemCredito() {
		return origemCredito;
	}
	public void setOrigemCredito(Integer origemCredito) {
		this.origemCredito = origemCredito;
	}
	public Integer getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	public Integer getPeriodoQuitacao() {
		return periodoQuitacao;
	}
	public void setPeriodoQuitacao(Integer periodoQuitacao) {
		this.periodoQuitacao = periodoQuitacao;
	}
	public boolean isTransferencia() {
		return transferencia;
	}
	public void setTransferencia(boolean transferencia) {
		this.transferencia = transferencia;
	}
	public Integer getDestino() {
		return destino;
	}
	public void setDestino(Integer destino) {
		this.destino = destino;
	}
	public Lancamento copia() {
		Lancamento copia = new Lancamento();
		copia.setConta(conta);
		copia.setData(data);
		copia.setDescricao(descricao);
		copia.setDestino(destino);
		copia.setExcluido(excluido);
		copia.setId(id);
		copia.setNatureza(natureza);
		copia.setOrigemCredito(origemCredito);
		copia.setPeriodo(periodo);
		copia.setPeriodoQuitacao(periodoQuitacao);
		copia.setPrevisao(previsao);
		copia.setSaldoFinal(saldoFinal);
		copia.setQuitacao(quitacao);
		copia.setSaldoInicial(saldoInicial);
		copia.setTipoMovimento(tipoMovimento);
		copia.setTransferencia(transferencia);
		copia.setUsuario(usuario);
		copia.setValor(valor);
		copia.valorPrincipal=valor;
		copia.setParcelas(parcelas);
		copia.setContato(contato);
		copia.setOrigemCredito(origemCredito);
		copia.setAtualizacaoAliquota(atualizacaoAliquota);
		copia.setAtualizacaoValor(atualizacaoValor);
		copia.setDescontoAliquota(descontoAliquota);
		copia.setDescontoValor(descontoValor);
		copia.setJurosAliquota(jurosAliquota);
		copia.setJurosValor(jurosValor);
		copia.setMultaAliquota(multaAliquota);
		copia.setMultaValor(multaValor);
		copia.setFatura(fatura);
		copia.setOrigemLancamento(origemLancamento);
		return copia;
	}
	
	public Double getValorPrincipal() {
		return valorPrincipal;
	}
	public void setValorPrincipal(Double valorPrincipal) {
		this.valorPrincipal = valorPrincipal;
	}
	public Double getJurosAliquota() {
		return jurosAliquota;
	}
	public void setJurosAliquota(Double jurosAliquota) {
		this.jurosAliquota = jurosAliquota;
	}
	public Double getMultaAliquota() {
		return multaAliquota;
	}
	public void setMultaAliquota(Double multaAliquota) {
		this.multaAliquota = multaAliquota;
	}
	public Double getJurosValor() {
		return jurosValor;
	}
	public void setJurosValor(Double jurosValor) {
		this.jurosValor = jurosValor;
	}
	public Double getMultaValor() {
		return multaValor;
	}
	public void setMultaValor(Double multaValor) {
		this.multaValor = multaValor;
	}
	public Double getDescontoAliquota() {
		return descontoAliquota;
	}
	public void setDescontoAliquota(Double descontoAliquota) {
		this.descontoAliquota = descontoAliquota;
	}
	public Double getDescontoValor() {
		return descontoValor;
	}
	public void setDescontoValor(Double descontoValor) {
		this.descontoValor = descontoValor;
	}
	public Double getAtualizacaoAliquota() {
		return atualizacaoAliquota;
	}
	public void setAtualizacaoAliquota(Double atualizacaoAliquota) {
		this.atualizacaoAliquota = atualizacaoAliquota;
	}
	public Double getAtualizacaoValor() {
		return atualizacaoValor;
	}
	public void setAtualizacaoValor(Double atualizacaoValor) {
		this.atualizacaoValor = atualizacaoValor;
	}
	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}
	public Integer getParcelas() {
		return parcelas;
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
		Lancamento other = (Lancamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public Integer getContato() {
		return contato;
	}
	public void setContato(Integer contato) {
		this.contato = contato;
	}
	public void setFatura(Integer fatura) {
		this.fatura = fatura;
	}
	public Integer getFatura() {
		return fatura;
	}
	public Integer getOrigemLancamento() {
		return origemLancamento;
	}
	public void setOrigemLancamento(Integer origemLancamento) {
		this.origemLancamento = origemLancamento;
	}
}
