package com.dextrainning.pessoa;

import javax.persistence.EntityManager;

import com.dextrainning.jpa.EntityManagerUtil;
import com.dextrainning.service.GenericService;

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
				salvarEndereco(entidade.getEndereco(), em);
				em.persist(entidade);
			} else {
				entidade.setEndereco(salvarEndereco(entidade.getEndereco(), em));
				em.merge(entidade);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
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
}