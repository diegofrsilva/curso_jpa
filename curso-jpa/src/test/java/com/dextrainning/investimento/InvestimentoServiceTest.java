package com.dextrainning.investimento;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.dextrainning.exception.ValidacaoException;
import com.dextrainning.jpa.EntityManagerUtil;

public class InvestimentoServiceTest {

	@After
	public void close() {
		EntityManagerUtil.fechar();
	}

	@Test
	public void salvarComSucessoTest() {
		Investimento investimento = new Investimento();
		investimento.setDescricao("Investimento qualquer");
		investimento.setRendimentoMensal(0.1);
		investimento.setValor(500.00);

		InvestimentoService investimentoService = new InvestimentoService();
		investimentoService.salvar(investimento);
		Investimento investimentoSalvo = investimentoService.buscarPorId(investimento.getId());
		Assert.assertNotNull(investimentoSalvo);
	}

	@Test(expected = ValidacaoException.class)
	public void salvarSemCampoObrigatorioTest() {
		Investimento investimento = new Investimento();
		investimento.setRendimentoMensal(0.1);
		investimento.setValor(500.00);

		InvestimentoService investimentoService = new InvestimentoService();
		investimentoService.salvar(investimento);
	}
}
