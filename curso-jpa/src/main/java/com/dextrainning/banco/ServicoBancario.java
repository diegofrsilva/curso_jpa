package com.dextrainning.banco;

import javax.persistence.Entity;

import com.dextrainning.jpa.Entidade;

@Entity
public class ServicoBancario extends Entidade {

	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
