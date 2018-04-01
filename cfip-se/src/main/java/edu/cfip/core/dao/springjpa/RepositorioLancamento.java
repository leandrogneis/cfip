package edu.cfip.core.dao.springjpa;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import edu.cfip.core.dao.Repositorio;
import edu.cfip.core.model.Conta;
import edu.cfip.core.model.Lancamento;
import edu.cfip.core.model.TipoMovimento;
import edu.porgamdor.util.desktop.ss.util.DataHora;

@Repository
public class RepositorioLancamento {
	private static Logger LOG = Logger.getLogger(Repositorio.class.getName());
	@PersistenceContext(unitName = "PU_CFIP")
	private EntityManager manager;
	
	@Transactional
	public void incluirLancamento(Lancamento entidade) {
		Date quitacao = entidade.getQuitacao();
		int parcelaInicial=entidade.getParcelaInicial();
		int parcelaFinal=entidade.getParcelaFinal();
		int parcelas = (parcelaFinal + 1) - parcelaInicial;
		for (int parcela = parcelaInicial; parcela <= parcelas; parcela++) {
			entidade.setParcelas(parcelas);
			entidade.setParcela(parcela);
			
			Lancamento lancamento = entidade.copia();
			
			lancamento.setQuitacao(quitacao);
			if (parcelas > 1)
				lancamento.setDescricao(lancamento.getDescricao() + " PARC.: " + parcela + "/" + parcelas);
			
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
			
			if (TipoMovimento.TRANSFERENCIA == tipoMovimento) {
				Lancamento transferencia = lancamento.copia();
				transferencia.setTipoMovimento(TipoMovimento.CREDITO);
				transferencia.setOrigemLancamento(lancamento.getId());
				transferencia.setTransferencia(true);
				
				lancamento.setTransferencia(true);
				
				Conta destino = lancamento.getDestino();			
				transferencia.setSaldoInicial(destino.getSaldo());
				transferencia.setSaldoFinal(destino.getSaldo());
				
				if (!lancamento.isPrevisao()) {
					destino.setSaldo(destino.getSaldo() + valor);
					transferencia.setSaldoFinal(destino.getSaldo());
				}
				transferencia.setDescricao("TRANSF.DE: " + conta.getNome() + " - " + lancamento.getDescricao());
				
				transferencia.setValor(valor);
				transferencia.setConta(destino);
				
				if (lancamento.getQuitacao() != null)
					transferencia.setPeriodoQuitacao(DataHora.pegaPeriodo(lancamento.getQuitacao()));
				
				manager.persist(transferencia);
				manager.merge(destino);
			}
			
			manager.merge(conta);
			lancamento = manager.merge(lancamento);
			if (quitacao != null)
				quitacao = DataHora.adiciona(Calendar.MONTH, 1, quitacao);
		}
		
		/*Date quitacao = lancamento.getQuitacao();
		Integer parcelas=1;
		Integer inicio=1;
		if(lancamento.getParcelaIntervalo()!=null) {
			String[] iniFim = lancamento.getParcelaIntervalo().split("/");
			inicio = Integer.valueOf(iniFim[0].trim());
			parcelas = Integer.valueOf(iniFim[1].trim());
		}
		//se tiver fatura - só gera uma parcela
		int contador = lancamento.getFatura()==null || lancamento.getFatura()==0 ?parcelas :1;
		for (int parcela = inicio; parcela <= contador; parcela++) {
			lancamento.setParcelas(parcela);
			Lancamento novo = lancamento.copia();
			novo.setQuitacao(quitacao);
			if (contador > 1)
				novo.setDescricao(novo.getDescricao() + " PARC.: " + parcela + "/" + parcelas);
			Double valor = novo.getValor();
			Conta conta = manager.find(Conta.class, novo.getConta());
			novo.setSaldoInicial(conta.getSaldo());
			novo.setSaldoFinal(conta.getSaldo());
			novo.setPeriodo(DataHora.pegaPeriodo(novo.getData()));
			if (novo.getQuitacao() != null)
				novo.setPeriodoQuitacao(DataHora.pegaPeriodo(novo.getQuitacao()));

			TipoMovimento tipoMovimento = novo.getTipoMovimento();

			if (TipoMovimento.DEBITO == tipoMovimento || TipoMovimento.TRANSFERENCIA == tipoMovimento) {
				novo.setValor(valor * -1);
				novo.setValorPrincipal(novo.getValor());
			}
			if (!novo.isPrevisao()) {
				conta.setSaldo(conta.getSaldo() + novo.getValor());
				novo.setSaldoFinal(conta.getSaldo());
			}
			
			if (TipoMovimento.TRANSFERENCIA == tipoMovimento) {
				Lancamento transferencia = novo.copia();
				transferencia.setOrigemCredito(novo.getId());
				transferencia.setTransferencia(true);
				novo.setTransferencia(true);
				Conta destino = manager.find(Conta.class, novo.getDestino());
				novo.setDestino(destino.getId());
				novo.setTipoMovimento(TipoMovimento.DEBITO);
				transferencia.setSaldoInicial(destino.getSaldo());
				transferencia.setSaldoFinal(destino.getSaldo());
				if (!novo.isPrevisao()) {
					destino.setSaldo(destino.getSaldo() + valor);
					transferencia.setSaldoFinal(destino.getSaldo());
				}
				transferencia.setDescricao("TRANSF.DE: " + conta.getNome() + " - DESC: " + novo.getDescricao());
				
				transferencia.setValor(valor);
				transferencia.setConta(destino.getId());
				transferencia.setTipoMovimento(TipoMovimento.CREDITO);

				if (novo.getQuitacao() != null)
					transferencia.setPeriodoQuitacao(DataHora.pegaPeriodo(novo.getQuitacao()));
				manager.persist(transferencia);
				manager.persist(destino);
			}
			manager.merge(conta);
			novo = manager.merge(novo);
			if (quitacao != null)
				quitacao = DataHora.adiciona(Calendar.MONTH, 1, quitacao);

		}	
*/
	}
}
