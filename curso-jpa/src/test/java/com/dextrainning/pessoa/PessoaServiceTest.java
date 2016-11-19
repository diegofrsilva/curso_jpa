package com.dextrainning.pessoa;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.dextrainning.jpa.EntityManagerUtil;

public class PessoaServiceTest {

	@After
	public void close() {
		EntityManagerUtil.fechar();
	}

	@Test
	public void salvarPessoaTest() {
		PessoaFisicaService pessoaFisicaService = new PessoaFisicaService();
		PessoaJuridicaService pessoaJuridicaService = new PessoaJuridicaService();

		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua sem saida");
		endereco.setNumero(0001);
		endereco.setEstado("SP");
		
		PessoaFisica pessoa = new PessoaFisica();
		pessoa.setDataNascimento(new Date());
		pessoa.setNome("Diego Farias da Silva");
		pessoa.setCpf("000.000.000-00");
		pessoa.setEndereco(endereco);
		pessoaFisicaService.salvar(pessoa);

		PessoaJuridica pessoaPJ = new PessoaJuridica();
		pessoaPJ.setNome("Empresa qualquer");
		pessoaPJ.setCnpj("000.000.000/01");
		pessoaJuridicaService.salvar(pessoaPJ);

		Assert.assertNotNull(pessoa.getId());
	}

	@Test
	public void buscarPessoaFisicaPorIdTeste() throws ParseException {
		PessoaFisicaService pessoaFisicaService = new PessoaFisicaService();

		PessoaFisica pessoa = new PessoaFisica();
		 pessoa.setDataNascimento(new Date());
		pessoa.setNome("Diego Farias da Silva");
		pessoaFisicaService.salvar(pessoa);

		Pessoa pessoaNaoEncontrada = pessoaFisicaService.buscarPorId(-1L);
		Pessoa pessoaEncontrada = pessoaFisicaService.buscarPorId(pessoa.getId());

		Assert.assertNull(pessoaNaoEncontrada);
		Assert.assertNotNull(pessoaEncontrada);
	}

	@Test
	public void buscarTodosTeste() {
		PessoaFisicaService pessoaFisicaService = new PessoaFisicaService();
		PessoaJuridicaService pessoaJuridicaService = new PessoaJuridicaService();

		PessoaFisica pessoa = new PessoaFisica();
		pessoa.setDataNascimento(new Date());
		pessoa.setNome("Diego Farias da Silva");
		pessoa.setCpf("000.000.000-00");
		pessoaFisicaService.salvar(pessoa);

		PessoaJuridica pessoaPJ = new PessoaJuridica();
		pessoaPJ.setNome("Empresa qualquer");
		pessoaPJ.setCnpj("000.000.000/01");
		pessoaJuridicaService.salvar(pessoaPJ);

		List<PessoaFisica> pessoasFisicas = pessoaFisicaService.buscarTodos();
		List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaService.buscarTodos();
		
		List<Pessoa> pessoas = new ArrayList<>();
		pessoas.addAll(pessoasFisicas);
		pessoas.addAll(pessoasJuridicas);
		
		Assert.assertEquals(2, pessoas.size());
	}
	
	@Test
	public void testarEstado() {
		PessoaFisica pessoa = new PessoaFisica();
		pessoa.setDataNascimento(new Date());
		pessoa.setNome("Diego Farias da Silva");
		pessoa.setCpf("000.000.000-00");
		pessoa.setNome("Manuel"); // ainda nao gerenciado
		
		EntityManager em = EntityManagerUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(pessoa);
		pessoa.setNome("Juquinha"); // depois de gerenciado, vai alterar no banco
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void testarDevoltarEntidade() {
		PessoaFisica pessoa = new PessoaFisica();
		pessoa.setDataNascimento(new Date());
		pessoa.setNome("Diego Farias da Silva");
		pessoa.setCpf("000.000.000-00");
		pessoa.setNome("Manuel"); 
		
		EntityManager em = EntityManagerUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(pessoa);
		em.getTransaction().commit();
		em.close();
		
		em = EntityManagerUtil.criarEntityManager();
		em.getTransaction().begin();
		pessoa = em.merge(pessoa);
		pessoa.setNome("Juquinha"); // depois de gerenciado, vai alterar no banco
		em.getTransaction().commit();
		em.close();
	}
}
