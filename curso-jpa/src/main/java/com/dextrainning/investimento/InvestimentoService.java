package com.dextrainning.investimento;

import com.dextrainning.exception.ValidacaoException;
import com.dextrainning.service.GenericService;

public class InvestimentoService extends GenericService<Investimento> {

	public InvestimentoService() {
		super(Investimento.class);
	}

	@Override
	public void salvar(Investimento investimento) {
		if (investimento.getDescricao() == null || investimento.getDescricao().isEmpty()) {
			throw new ValidacaoException("Descricao nao pode estar vazia");
		}
		if (investimento.getRendimentoMensal() == null || investimento.getRendimentoMensal() <= 0) {
			throw new ValidacaoException("Rendimento mensal invalido");
		}
		if (investimento.getValor() == null || investimento.getValor() <= 0) {
			throw new ValidacaoException("Valor invalido");
		}
		super.salvar(investimento);
	}
}
