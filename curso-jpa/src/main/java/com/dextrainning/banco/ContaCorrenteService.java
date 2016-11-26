package com.dextrainning.banco;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.dextrainning.infra.jpa.EntityManagerUtil;
import com.dextrainning.infra.service.GenericService;

public class ContaCorrenteService extends GenericService<ContaCorrente>{

	public ContaCorrenteService() {
		super(ContaCorrente.class);
	}
	
	public ContaCorrente buscarPorNumero(String numero) {
		EntityManager em = EntityManagerUtil.criarEntityManager();
		try {
			StringBuilder str = new StringBuilder();
			str.append("SELECT conta FROM ContaCorrente conta ");
			str.append("WHERE conta.numero.numero = :numero");

			TypedQuery<ContaCorrente> query = em.createQuery(str.toString(), ContaCorrente.class);
			query.setParameter("numero", numero);
			
			return query.getSingleResult();
		} catch(NoResultException e) {
			return null;
		} finally {
			em.close();
		}
	}
}
