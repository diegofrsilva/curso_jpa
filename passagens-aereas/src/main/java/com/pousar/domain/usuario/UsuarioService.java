package com.pousar.domain.usuario;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.pousar.app.util.Strings;
import com.pousar.domain.ValidacaoException;
import com.pousar.jpa.BaseService;
import com.pousar.jpa.EntityManagerUtil;

public class UsuarioService extends BaseService<Usuario> {

	public UsuarioService() {
		super(Usuario.class);
	}

	@Override
	public Usuario salvar(Usuario usuario) {
		if (Strings.isEmpty(usuario.getNome())) {
			throw new ValidacaoException("Nome eh obrigatorio");
		}
		if (Strings.isEmpty(usuario.getEmail())) {
			throw new ValidacaoException("Email eh obrigatorio");
		}
		if (Strings.isEmpty(usuario.getSenha())) {
			throw new ValidacaoException("Senha eh obrigatorio");
		}
		Usuario usuarioEncontrado = buscarPorEmail(usuario.getEmail());
		if (usuarioEncontrado != null) {
			if (!usuarioEncontrado.getId().equals(usuario.getId())) {
				throw new ValidacaoException("Email jah cadastrado");
			}
		}
		return super.salvar(usuario);
	}

	public Usuario buscarParaLogin(String email, String senha) {
		EntityManager em = EntityManagerUtil.criarEntityManager();

		try {
			StringBuilder jpql = new StringBuilder();
			jpql.append("SELECT u FROM Usuario u ");
			jpql.append("WHERE UPPER(u.email) = UPPER(:email ) ");
			jpql.append("AND u.senha = :senha");

			TypedQuery<Usuario> query = em.createQuery(jpql.toString(), Usuario.class);
			query.setParameter("email", email);
			query.setParameter("senha", senha);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
	}

	public Usuario buscarPorEmail(String email) {
		EntityManager em = EntityManagerUtil.criarEntityManager();

		try {
			TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE UPPER(u.email) = UPPER(:email )",
					Usuario.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
	}

	public List<Usuario> buscarPor(String email, String nome) {
		EntityManager em = EntityManagerUtil.criarEntityManager();
		try {
			StringBuilder jpql = new StringBuilder();
			jpql.append("SELECT u FROM Usuario u WHERE 1=1 ");

			if (Strings.isNotEmpty(email)) {
				jpql.append("AND UPPER(u.email) LIKE UPPER(:email) ");
			}
			if (Strings.isNotEmpty(nome)) {
				jpql.append("AND UPPER(u.nome) LIKE UPPER(:nome) ");
			}
			TypedQuery<Usuario> query = em.createQuery(jpql.toString(), Usuario.class);

			if (Strings.isNotEmpty(email)) {
				query.setParameter("email", "%" + email + "%");
			}
			if (Strings.isNotEmpty(nome)) {
				query.setParameter("nome", "%" + nome + "%");
			}
			return query.getResultList();
		} finally {
			em.close();
		}
	}
}
