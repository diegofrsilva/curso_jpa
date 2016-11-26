package com.dextrainning.banco.pessoa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.dextrainning.banco.Agencia;
import com.dextrainning.banco.endereco.Endereco;
import com.dextrainning.banco.telefone.Telefone;
import com.dextrainning.infra.jpa.EntityManagerUtil;
import com.dextrainning.infra.service.GenericService;

public class PessoaFisicaService extends GenericService<PessoaFisica> {

	public PessoaFisicaService() {
		super(PessoaFisica.class);
	}

	@Override
	public void salvar(PessoaFisica entidade) {
		EntityManager em = EntityManagerUtil.criarEntityManager();
		try {
			em.getTransaction().begin();
			if (entidade.getId() == null) {
				// salvarTelefones(entidade.getTelefones(), em); Esta sendo
				// salvo com cascade
				salvarEndereco(entidade.getEndereco(), em);
				em.persist(entidade);
			} else {
				entidade.setEndereco(salvarEndereco(entidade.getEndereco(), em));
				em.merge(entidade);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	private void salvarTelefones(List<Telefone> telefones, EntityManager em) {
		for (Telefone telefone : telefones) {
			em.persist(telefone);
		}
	}

	private Endereco salvarEndereco(Endereco endereco, EntityManager em) {
		if (endereco != null) {
			if (endereco.getId() == null) {
				em.persist(endereco);
			} else {
				return em.merge(endereco);
			}
		}
		return endereco;
	}

	public List<PessoaFisica> buscarPorAgencia(Agencia agencia) {
		EntityManager em = EntityManagerUtil.criarEntityManager();
		try {
			String jpql = "SELECT pessoa FROM PessoaFisica pessoa";
			jpql += " WHERE pessoa.agencia = :agencia";

			TypedQuery<PessoaFisica> query = em.createQuery(jpql, PessoaFisica.class);
			query.setParameter("agencia", agencia);
			return query.getResultList();
		} finally {
			em.close();
		}
	}
	
	public List<PessoaFisica> buscarPorAgenciaComNativeQuery(Agencia agencia) {
		EntityManager em = EntityManagerUtil.criarEntityManager();
		try {
			String jpql = "SELECT pessoa.* FROM pessoa_fisica pessoa WHERE pessoa.agencia_id = :agenciaId";
			TypedQuery<PessoaFisica> query = em.createQuery(jpql, PessoaFisica.class);
			query.setParameter("agenciaId", agencia.getId());
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	public List<Object[]> buscarNomeEDataNascimento() {
		EntityManager em = EntityManagerUtil.criarEntityManager();
		try {
			Query query = em.createQuery("SELECT pessoa.nome, pessoa.dataNascimento FROM PessoaFisica pessoa");
			return query.getResultList();
		} finally {
			em.close();
		}
	}
}