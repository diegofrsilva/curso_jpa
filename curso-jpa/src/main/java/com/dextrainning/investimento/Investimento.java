package com.dextrainning.investimento;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dextrainning.jpa.Entidade;

@Entity
public class Investimento extends Entidade {

	@Column(nullable = false)
	private Double valor;

	@Column(nullable = false)
	private String descricao;

	@Column(nullable = false)
	private Double rendimentoMensal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_hora")
	private Date data;

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getRendimentoMensal() {
		return rendimentoMensal;
	}

	public void setRendimentoMensal(Double rendimentoMensal) {
		this.rendimentoMensal = rendimentoMensal;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getData() {
		return data;
	}
}
