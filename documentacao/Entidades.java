package com.boxs.cfip.core.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.boxs.cfip.core.model.Conta;
import com.boxs.cfip.core.model.Contato;
import com.boxs.cfip.core.model.DespesaRapida;
import com.boxs.cfip.core.model.Fatura;
import com.boxs.cfip.core.model.Lancamento;
import com.boxs.cfip.core.model.Natureza;
import com.boxs.cfip.core.model.Saldo;
import com.boxs.cfip.core.model.TipoMovimento;
import com.boxs.cfip.core.model.Usuario;
import com.boxs.cfip.core.model.filter.Filtro;
import com.boxs.cfip.core.model.vo.LancamentoVo;
import com.boxs.cfip.core.util.TipoOperacao;
import com.boxs.cfip.core.util.Total;

import client.ss.infraestrutura.util.DataHora;
import client.ss.infraestrutura.util.Formatador;
//https://docs.oracle.com/javase/7/docs/api/java/text/MessageFormat.html
@Repository
public class Entidades {
	private static Logger LOG = Logger.getLogger(Entidades.class.getName());
	@PersistenceContext(unitName = "PU_CFIP")
	private EntityManager manager;

	@Transactional
	public void gravar(TipoOperacao operacao, Object entidade) {
		if (TipoOperacao.INCLUSAO == operacao)
			manager.persist(entidade);
		else
			manager.merge(entidade);
	}

	public List<Integer> periodos() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 6);
		List<Integer> lista = new ArrayList<Integer>();
		for (int x = 0; x < 11; x++) {
			Integer periodo = DataHora.pegaPeriodo(c.getTime());
			lista.add(periodo);
			c.add(Calendar.MONTH, -1);
		}
		return lista;
	}
	public boolean contaUnica(String login, String email) {
		Query query = manager.createQuery("SELECT u FROM Usuario u WHERE u.login=:login OR u.email = :email");
		query.setParameter("login", login);
		query.setParameter("email", email);
		
		boolean contaUnica=false;
		try {
			query.getSingleResult();
		} catch (NoResultException nre) {
			contaUnica=true;
		}

		return contaUnica;
	}
	public Usuario login(String login, String senha) {
		Usuario usuario = null;
		Query query = manager.createQuery("SELECT u from Usuario u WHERE u.login=:login and u.senha = :senha");
		query.setParameter("login", login);
		query.setParameter("senha", senha);
		try {
			usuario = (Usuario) query.getSingleResult();
		} catch (Exception ex) {
			System.out.println("Usuario não econtrado");
		}

		return usuario;
	}
	public Usuario usuario(String login) {
		Usuario usuario = null;
		Query query = manager.createQuery("SELECT u from Usuario u WHERE u.login=:login");
		query.setParameter("login", login);
		try {
			usuario = (Usuario) query.getSingleResult();
		} catch (Exception ex) {
			System.out.println("Usuario não econtrado");
		}

		return usuario;
	}

	@Transactional
	public void incluir(Object entidade) {
		manager.persist(entidade);
	}

	@Transactional
	public Object alterar(Object entidade) {
		return manager.merge(entidade);
	}
	@Transactional
	public void excluir(Class entidade, Integer id) {
		Query query = manager.createQuery("update " + entidade.getSimpleName() + "  e set e.excluido = true where e.id = :id");
		query.setParameter("id", id);
		query.executeUpdate();
	}
	@Transactional
	public void removerFaturaLancamento(Integer id) {
		Query query = manager.createQuery("update Lancamento e set e.fatura = null where e.id = :id");
		query.setParameter("id", id);
		query.executeUpdate();
	}
	public List<DespesaRapida> listarDespesasRapidas(Integer usuario) {
		Query query = manager.createQuery("SELECT e FROM DespesaRapida e WHERE e.excluido = false AND e.usuario = :usuario ORDER BY e.ordem");
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}
	public List<Conta> listarContas(Integer usuario, String nome) {
		Query query = manager.createQuery(
				"SELECT e FROM Conta e WHERE e.excluido = false and e.usuario = :usuario and e.nome like :nome");
		query.setParameter("usuario", usuario);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}
	public List<Fatura> listarFaturas(Integer usuario) {
		Query query = manager.createQuery(
				"SELECT e FROM Fatura e WHERE e.excluido = false AND e.usuario = :usuario ORDER BY e.vencimento desc");
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}
	public List<Fatura> listarFaturas(Integer usuario, String nome) {
		Query query = manager.createQuery(
				"SELECT e FROM Fatura e WHERE e.excluido = false AND e.usuario = :usuario AND ( e.sigla like :nome OR e.detalhe like :nome) ORDER BY e.vencimento desc");
		query.setParameter("usuario", usuario);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}
	public List<Contato> listarContatos(Integer usuario, String nome) {
		Query query = manager.createQuery(
				"SELECT e FROM Contato e WHERE e.excluido = false and e.usuario = :usuario and e.nome like :nome");
		query.setParameter("usuario", usuario);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}
	public List<Contato> listarContatos(Integer usuario) {
		Query query = manager.createQuery("SELECT e FROM Contato e WHERE e.excluido = false and e.usuario = :usuario ORDER BY e.nome");
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}
	public List<Conta> listarContas(Integer usuario) {
		Query query = manager.createQuery("SELECT e FROM Conta e WHERE e.excluido = false and e.usuario = :usuario ORDER BY e.nome");
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}
	public List<Conta> listarContas(Integer usuario,Integer id) {
		StringBuilder sql = new StringBuilder("SELECT e FROM Conta e WHERE e.excluido = false AND saldo >0 AND  e.usuario = :usuario");
		if(id!=null)
			sql.append(" AND e.id = :id");
		sql.append(" ORDER BY e.nome");
		Query query = manager.createQuery(sql.toString());
		query.setParameter("usuario", usuario);
		if(id!=null)
			query.setParameter("id", id);
		
		return query.getResultList();
	}
	public List<Saldo> listarSaldos(Integer conta) {
		Query query = manager.createQuery("SELECT e FROM Saldo e WHERE e.conta.id = :conta ORDER BY e.data DESC");
		query.setParameter("conta", conta);
		return query.getResultList();
	}

	public List<Natureza> listarNaturezas(Integer usuario, String nome) {
		Query query = manager.createQuery(
				"SELECT e FROM Natureza e WHERE e.excluido = false and e.usuario = :usuario AND e.nome LIKE :nome ORDER BY e.nome");
		query.setParameter("usuario", usuario);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}

	public List<Natureza> listarNaturezas(Integer usuario) {
		Query query = manager.createQuery("SELECT e FROM Natureza e WHERE e.excluido = false and e.usuario = :usuario ORDER BY e.tipoMovimento, e.nome");
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}
	public List<Natureza> listarNaturezas(Integer usuario,TipoMovimento tipo) {
		Query query = manager.createQuery("SELECT e FROM Natureza e WHERE e.excluido = false AND e.usuario = :usuario AND e.tipoMovimento=:tipoMovto ORDER BY e.nome");
		query.setParameter("usuario", usuario);
		query.setParameter("tipoMovto", tipo);
		return query.getResultList();
	}
	public <T> T buscar(Class entidade, Integer id) {
		return (T) manager.find(entidade, id);
	}

	@Transactional
	public Usuario incluirUsuario(Usuario usuario) {
		manager.persist(usuario);
		Conta conta = new Conta();
		conta.setNome("CARTEIRA");
		conta.setSigla("CTR");
		conta.setUsuario(usuario.getId());
		manager.persist(conta);

		conta = new Conta();
		conta.setNome("CONTA CORRENTE");
		conta.setSigla("CCR");
		conta.setUsuario(usuario.getId());
		manager.persist(conta);

		conta = new Conta();
		conta.setNome("CONTA POUPANCA");
		conta.setSigla("CPA");
		conta.setUsuario(usuario.getId());
		manager.persist(conta);

		Natureza natureza = new Natureza();
		natureza.setDescricao("SALARIO");
		natureza.setNome("SALARIO");
		natureza.setUsuario(usuario.getId());
		natureza.setTipoMovimento(TipoMovimento.CREDITO);
		manager.persist(natureza);

		natureza = new Natureza();
		natureza.setDescricao("MESADA-PENSAO");
		natureza.setNome("MESADA-PENSAO");
		natureza.setUsuario(usuario.getId());
		natureza.setTipoMovimento(TipoMovimento.CREDITO);
		manager.persist(natureza);

		natureza = new Natureza();
		natureza.setDescricao("DESPEAS");
		natureza.setNome("DESPESAS");
		natureza.setUsuario(usuario.getId());
		natureza.setTipoMovimento(TipoMovimento.DEBITO);
		manager.persist(natureza);

		natureza = new Natureza();
		natureza.setDescricao("TRANSFERENCIA");
		natureza.setNome("TRANSFERENCIA");
		natureza.setUsuario(usuario.getId());
		natureza.setTipoMovimento(TipoMovimento.TRANSFERENCIA);
		manager.persist(natureza);

		natureza = new Natureza();
		natureza.setDescricao("ESTORNO DA TRANSFERENCIA");
		natureza.setNome("ESTORNO");
		natureza.setUsuario(usuario.getId());
		natureza.setTipoMovimento(TipoMovimento.TRANSFERENCIA);
		manager.persist(natureza);
		
		return usuario;
	}

	@Transactional
	public void amortizarLancamento(Integer id, Date quitacao, Double amortizado) {
		Lancamento lancamento = buscar(Lancamento.class, id);
		Double valor = lancamento.getValor();
		if (lancamento.getTipoMovimento() == TipoMovimento.DEBITO) {
			amortizado = amortizado * -1;
		}
		Double diferenca = valor - amortizado;

		lancamento.setPrevisao(false);

		Conta conta = manager.find(Conta.class, lancamento.getConta());
		lancamento.setSaldoInicial(conta.getSaldo());
		lancamento.setValor(amortizado);
		conta.setSaldo(conta.getSaldo() + amortizado);
		lancamento.setSaldoFinal(conta.getSaldo());
		if (diferenca != 0.0d) {
			// trazer nova instancia de Lancamento
			Lancamento novo = new Lancamento();
			novo.setOrigemLancamento(lancamento.getId());
			novo.setPrevisao(true);
			novo.setData(new Date());
			novo.setQuitacao(quitacao);
			novo.setDescricao("AMORT LANCTO: " + lancamento.getId() + "-" + lancamento.getDescricao());
			novo.setConta(lancamento.getConta());
			novo.setNatureza(lancamento.getNatureza());
			novo.setPeriodo(DataHora.pegaPeriodo(quitacao));
			novo.setSaldoInicial(lancamento.getSaldoInicial());
			novo.setSaldoFinal(lancamento.getSaldoFinal());
			novo.setValor(diferenca);
			novo.setTipoMovimento(lancamento.getTipoMovimento());
			novo.setUsuario(lancamento.getUsuario());
			manager.persist(novo);
		}
		manager.merge(conta);
		manager.merge(lancamento);
	}

	@Transactional
	public void prorrogarLancamento(Integer id, Date quitacao) {
		// compensar debito, depois fazer validacao
		Lancamento lancamento = buscar(Lancamento.class, id);
		lancamento.setDescricao("PRORROGAR LANCTO.:" + lancamento.getDescricao() + " - DE: "
				+ Formatador.formatarData(lancamento.getData()));
		lancamento.setQuitacao(quitacao);
		manager.merge(lancamento);
	}
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void fecharFatura(Integer fatura) {
		Query query = manager.createQuery("SELECT e.id FROM Lancamento e where e.fatura = :fatura");
		query.setParameter("fatura", fatura);
		List<Integer> ids = query.getResultList();;
		compensarLancamento(new Date(), fatura, ids.toArray(new Integer[] {}));
	}
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void compensarLancamento(Integer id, Date quitacao ) {
		compensarLancamento(quitacao, null, id);
	}
	@Transactional(propagation=Propagation.NESTED)
	public void compensarLancamento(Date quitacao,Integer fatura, Integer ... ids) {
		for(Integer id:ids) {
			Lancamento lancamento = buscar(Lancamento.class, id);
			lancamento.setDescricao(lancamento.getDescricao() + " - DE: " + Formatador.formatarData(lancamento.getData()));
			lancamento.setQuitacao(quitacao);
			lancamento.setData(quitacao);
			lancamento.setPrevisao(false);
			Conta conta = manager.find(Conta.class, lancamento.getConta());
			lancamento.setSaldoInicial(conta.getSaldo());
			conta.setSaldo(conta.getSaldo() + lancamento.getValor());
			lancamento.setSaldoFinal(conta.getSaldo());
			// depois tentar salvar em dois metodos um única transacao
			if (lancamento.isTransferencia()) {
				Lancamento origem = buscar(Lancamento.class, lancamento.getOrigemCredito());
				origem.setQuitacao(quitacao);
				origem.setData(quitacao);
				origem.setPrevisao(false);
				Conta contaOrigem = manager.find(Conta.class, origem.getConta());
				origem.setSaldoInicial(contaOrigem.getSaldo());
				contaOrigem.setSaldo(contaOrigem.getSaldo() + origem.getValor());
				origem.setSaldoFinal(contaOrigem.getSaldo());
				manager.merge(contaOrigem);
				manager.persist(origem);
			}

			manager.merge(conta);
			manager.merge(lancamento);
			LOG.info("Compensando lançamento:: " + lancamento.getId() + " " + lancamento.getDescricao());
		}
		if(fatura!=null) {
			Fatura fat = buscar(Fatura.class, fatura);
			fat.setFechada(true);
			manager.merge(fat);
			LOG.info("Fechando fatura:: " + fat.getSigla() + " " + fat.getId());
		}
	}

	@Transactional
	public void incluirLancamento(Lancamento lancamento) {
		Date quitacao = lancamento.getQuitacao();
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

	}
	
	/*private static String SQL_LANCAMENTO_PREVISAO_FATURA = "SELECT NEW com.boxs.cfip.core.model.vo.LancamentoVo(l.id, l.data,c.nome,n.nome,l.descricao,"
			+ "l.saldoInicial,l.valor,l.saldoFinal,l.tipoMovimento,l.previsao,l.quitacao, l.periodo, l.periodoQuitacao,l.transferencia,l.origemCredito,ct.nome) "
			+ " FROM Lancamento l, Conta c, Natureza n, Contato ct WHERE l.conta=c.id AND l.natureza=n.id "
			+ " AND l.contato=ct.id AND l.excluido=:excluido AND l.previsao = :previsao AND l.usuario=:usuario AND ";*/
	
	private static String SQL_LANCAMENTO_PREVISAO_FATURA = "SELECT NEW com.boxs.cfip.core.model.vo.LancamentoVo(l.id, l.data,c.nome,n.nome,l.descricao,"
			+ "l.saldoInicial,l.valor,l.saldoFinal,l.tipoMovimento,l.previsao,l.quitacao, l.periodo, l.periodoQuitacao,l.transferencia,l.origemCredito,l.descricao) "
			+ " FROM Lancamento l, Conta c, Natureza n WHERE l.conta=c.id AND l.natureza=n.id "
			+ " AND l.excluido=:excluido AND l.previsao = :previsao AND l.usuario=:usuario AND ";
	
	public List<LancamentoVo> listarFaturaLancamentos(Integer usuario, Integer fatura){
		StringBuilder sql = new StringBuilder(SQL_LANCAMENTO_PREVISAO_FATURA + " l.fatura = :fatura");
		TypedQuery<LancamentoVo> query = manager.createQuery(sql.toString(), LancamentoVo.class);
		query.setParameter("excluido", false);
		query.setParameter("previsao", true);
		query.setParameter("usuario", usuario);
		query.setParameter("fatura", fatura);
		List<LancamentoVo> lista = query.getResultList();
		return lista;
	}
	
	private List<LancamentoVo> listarLancamentos(boolean excluido, boolean previsao, Integer usuario, Date inicio,
			Date fim, Integer conta, Integer natureza) {
		StringBuilder sql = new StringBuilder(SQL_LANCAMENTO_PREVISAO_FATURA);
		if(previsao)
			sql.append(" l.quitacao BETWEEN :inicio AND :fim ");
		else
			sql.append("l.data BETWEEN :inicio AND :fim ");
		if (natureza != null) {
			sql.append(" AND n.id=:natureza ");
		}
		if (conta != null) {
			sql.append(" AND c.id=:conta ");
		}
		if(previsao)
			sql = sql.append(" ORDER BY l.quitacao");
		else
			sql = sql.append(" ORDER BY l.data");
		
		TypedQuery<LancamentoVo> query = manager.createQuery(sql.toString(), LancamentoVo.class);
		query.setParameter("excluido", excluido);
		query.setParameter("previsao", previsao);
		query.setParameter("usuario", usuario);
		query.setParameter("inicio", inicio);
		query.setParameter("fim", fim);
		if (natureza != null)
			query.setParameter("natureza", natureza);

		if (conta != null)
			query.setParameter("conta", conta);

		List<LancamentoVo> lista = query.getResultList();
		// ISSO É PARA TRAZER SÓ AS TRANSFERENCIAS DE CREDITO NA PREVISAO
		List<LancamentoVo> minLista = new ArrayList<LancamentoVo>();
		for (LancamentoVo l : lista) {
			if (l.getTipo() == TipoMovimento.CREDITO
					|| (l.getTipo() == TipoMovimento.DEBITO && !(l.isTranferencia()))) {
				minLista.add(l);
			}
		}
		if (previsao)
			return minLista;
		else
			return lista;
	}

	// TELA DE LANÇAMENTOS
	public List<LancamentoVo> listarLancamentos(Integer usuario, Date inicio, Date fim, Integer conta,
			Integer natureza) {
		return listarLancamentos(false, false, usuario, inicio, fim, conta, natureza);
	}

	public List<LancamentoVo> listarLancamentos(Filtro filtro) {
		return listarLancamentos(filtro.isExcluido(), filtro.isPrevisao(), filtro.getUsuario(), filtro.getInicio(),
				filtro.getFim(), filtro.getConta(), filtro.getNatureza());
	}
	// TELA DE PREVISOES

	public List<LancamentoVo> listarPrevisoes(Integer usuario, Date inicio, Date fim, Integer conta, Integer natureza) {
		return listarLancamentos(false, true, usuario, inicio, fim, conta, natureza);
	}
	
	// TELA DE EXTRATOS
	public List<LancamentoVo> listarContaLancamentos(Integer usuario, Date inicio, Date fim, Integer conta) {
		return listarLancamentos(false, false, usuario, inicio, fim, conta, null);
	}
	public Total totais(List<LancamentoVo> lista) {
		return totais(lista, false);
	}
	public Total totais(List<LancamentoVo> lista, boolean comTransferencia) {
		Total total = new Total();
		for(LancamentoVo l: lista) {
			if(comTransferencia || !l.isTranferencia())
				total.aplicar(l.getTipo() == TipoMovimento.CREDITO,l.getValor());
		}
		return total;
	}
	public Double contaTotais(List<Conta> lista) {
		Double total = 0.0d;;
		for(Conta c: lista) {
			total = total+c.getSaldo();
		}
		return total;
	}

}
