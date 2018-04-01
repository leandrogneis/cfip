package edu.cfip.core.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import edu.cfip.core.model.Conta;
import edu.cfip.core.model.Lancamento;
import edu.cfip.core.model.TipoMovimento;
import edu.cfip.core.model.filter.Filtro;
import edu.porgamdor.util.desktop.ambiente.Total;
import edu.porgamdor.util.desktop.ss.util.DataHora;

@Repository
public class RepositorioLancamento {
	private static Logger LOG = Logger.getLogger(Repositorio.class.getName());
	@PersistenceContext(unitName = "PU_CFIP")
	private EntityManager manager;
	
	private final String SQL_LANCAMENTO_PREVISAO="SELECT l FROM Lancamento l WHERE l.excluido=:excluido AND l.previsao = :previsao AND l.usuario=:usuario AND";
	private List<Lancamento> listarLancamentos(boolean excluido, boolean previsao, Integer usuario, Date inicio,
			Date fim, Integer conta, Integer natureza) {
		StringBuilder sql = new StringBuilder(SQL_LANCAMENTO_PREVISAO);
		if(previsao)
			sql.append(" l.quitacao BETWEEN :inicio AND :fim ");
		else
			sql.append(" l.data BETWEEN :inicio AND :fim ");
		if (natureza != null) {
			sql.append(" AND l.natureza.id=:natureza ");
		}
		if (conta != null) {
			sql.append(" AND l.conta.id=:conta ");
		}
		if(previsao)
			sql = sql.append(" ORDER BY l.quitacao");
		else
			sql = sql.append(" ORDER BY l.data");
		
		TypedQuery<Lancamento> query = manager.createQuery(sql.toString(), Lancamento.class);
		query.setParameter("excluido", excluido);
		query.setParameter("previsao", previsao);
		query.setParameter("usuario", usuario);
		query.setParameter("inicio", inicio);
		query.setParameter("fim", fim);
		if (natureza != null)
			query.setParameter("natureza", natureza);

		if (conta != null)
			query.setParameter("conta", conta);

		List<Lancamento> lista = query.getResultList();
		// ISSO É PARA TRAZER SÓ AS TRANSFERENCIAS DE CREDITO NA PREVISAO
		List<Lancamento> minLista = new ArrayList<Lancamento>();
		for (Lancamento l : lista) {
			if (l.getTipoMovimento() == TipoMovimento.CREDITO
					|| (l.getTipoMovimento() == TipoMovimento.DEBITO && !(l.isTransferencia()))) {
				minLista.add(l);
			}
		}
		if (previsao)
			return minLista;
		else
			return lista;
	}
	
	// TELA DE LANÇAMENTOS
	public List<Lancamento> listarLancamentos(Integer usuario, Date inicio, Date fim, Integer conta,
			Integer natureza) {
		return listarLancamentos(false, false, usuario, inicio, fim, conta, natureza);
	}

	public List<Lancamento> listarLancamentos(Filtro filtro) {
		return listarLancamentos(filtro.isExcluido(), filtro.isPrevisao(), filtro.getUsuario(), filtro.getInicio(),
				filtro.getFim(), filtro.getConta(), filtro.getNatureza());
	}
	// TELA DE PREVISOES

	public List<Lancamento> listarPrevisoes(Integer usuario, Date inicio, Date fim, Integer conta, Integer natureza) {
		return listarLancamentos(false, true, usuario, inicio, fim, conta, natureza);
	}

	// TELA DE EXTRATOS
	public List<Lancamento> listarContaLancamentos(Integer usuario, Date inicio, Date fim, Integer conta) {
		return listarLancamentos(false, false, usuario, inicio, fim, conta, null);
	}

	public Total totais(List<Lancamento> lista) {
		return totais(lista, false);
	}

	public Total totais(List<Lancamento> lista, boolean comTransferencia) {
		Total total = new Total();
		for (Lancamento l : lista) {
			if (comTransferencia || !l.isTransferencia())
				total.aplicar(l.getTipoMovimento() == TipoMovimento.CREDITO, l.getValor());
		}
		return total;
	}

	public Double contaTotais(List<Conta> lista) {
		Double total = 0.0d;
		;
		for (Conta c : lista) {
			total = total + c.getSaldo();
		}
		return total;
	}

	@Transactional
	public void incluirLancamento(Lancamento entidade) {
		Date quitacao = entidade.getQuitacao();
		int parcelaInicial = entidade.getParcelaInicial();
		int parcelaFinal = entidade.getParcelaFinal();
		int parcelas = (parcelaFinal + 1) - parcelaInicial;
		for (int parcela = parcelaInicial; parcela <= parcelaFinal; parcela++) {
			entidade.setParcelas(parcelas);
			entidade.setParcela(parcela);

			Lancamento lancamento = entidade.copia();

			lancamento.setQuitacao(quitacao);
			if (parcelas > 1)
				lancamento.setDescricao(lancamento.getDescricao() + " PARC.: " + parcela + "/" + parcelaFinal);

			Conta conta = lancamento.getConta();

			lancamento.setSaldoInicial(conta.getSaldo());
			lancamento.setSaldoFinal(conta.getSaldo());
			lancamento.setPeriodo(DataHora.pegaPeriodo(lancamento.getData()));

			if (lancamento.getQuitacao() != null)
				lancamento.setPeriodoQuitacao(DataHora.pegaPeriodo(lancamento.getQuitacao()));

			Double valor = lancamento.getValor();
			TipoMovimento tipoMovimento = lancamento.getTipoMovimento();

			if (TipoMovimento.DEBITO == tipoMovimento || TipoMovimento.TRANSFERENCIA == tipoMovimento) {
				lancamento.setValor(valor * -1);
				lancamento.setValorPrincipal(lancamento.getValor());
			}

			if (!lancamento.isPrevisao()) {
				conta.setSaldo(conta.getSaldo() + lancamento.getValor());
				lancamento.setSaldoFinal(conta.getSaldo());
			}
			Lancamento transferencia = lancamento.copia();
			if (TipoMovimento.TRANSFERENCIA == tipoMovimento) {
				transferencia.setValor(valor);
				transferencia.setValorPrincipal(valor);
				transferencia.setTransferencia(true);
				transferencia.setTipoMovimento(TipoMovimento.CREDITO);

				lancamento.setTransferencia(true);
				lancamento.setTipoMovimento(TipoMovimento.DEBITO);

				Conta destino = lancamento.getDestino();
				transferencia.setSaldoInicial(destino.getSaldo());
				transferencia.setSaldoFinal(destino.getSaldo());
				transferencia.setDestino(null);

				if (!lancamento.isPrevisao()) {
					destino.setSaldo(destino.getSaldo() + valor);
					transferencia.setSaldoFinal(destino.getSaldo());
				}
				transferencia.setDescricao("TRANSF.DE: " + conta.getNome() + " - " + lancamento.getDescricao());

				transferencia.setConta(destino);

				if (lancamento.getQuitacao() != null)
					transferencia.setPeriodoQuitacao(DataHora.pegaPeriodo(lancamento.getQuitacao()));

				// FIXME:Validar persistencia
				transferencia.setOrigemLancamento(lancamento.getId());

				transferencia = manager.merge(transferencia);
				manager.merge(destino);
			}

			manager.merge(conta);
			lancamento.setDestinoLancamento(transferencia.getId());
			lancamento = manager.merge(lancamento);
			if (quitacao != null)
				quitacao = DataHora.adiciona(Calendar.MONTH, 1, quitacao);
		}
	}
	/*
	 * SELECT L.ID,l.TIPO_MOV_ID, l.SALDO_INICIAL, l.VALOR_PRINCIPAL, l.VALOR,
	 * L.SALDO_FINAL, l.ORIGEM_LANCTO_ID, l.TRANSFERENCIA, l.DESTINO_ID FROM
	 * LANCAMENTO l;
	 * 
	 * 
	 */
}
