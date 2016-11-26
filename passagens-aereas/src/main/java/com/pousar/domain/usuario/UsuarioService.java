package com.pousar.domain.usuario;

import java.util.ArrayList;
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
		// TODO: implementar login buscando dados no banco de dados

		if ("admin@pousar.com".equals(email) && "123".equals(senha)) {
			Usuario usuario = new Usuario();
			usuario.setEmail("admin@pousar.com");
			usuario.setNome("Administrador");
			usuario.setSenha("123");

			return usuario;
		}
		return null;
	}

	public Usuario buscarPorEmail(String email) {
		EntityManager em = EntityManagerUtil.criarEntityManager();

		try {
			TypedQuery<Usuario> query = em
					.createQuery("SELECT u FROM Usuario u WHERE UPPER(u.email) = UPPER(:email )", Usuario.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
	}

	public List<Usuario> buscarPor(String email, String nome) {
		// TODO: Buscar usuario por email ou nome
		return new ArrayList<>();
	}
}
