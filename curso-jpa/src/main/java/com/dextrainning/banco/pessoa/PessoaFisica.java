package com.dextrainning.banco.pessoa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dextrainning.banco.Agencia;
import com.dextrainning.banco.ServicoBancario;
import com.dextrainning.banco.endereco.Endereco;
import com.dextrainning.banco.investimento.Investimento;
import com.dextrainning.banco.telefone.Telefone;

@Entity
@Table(name = "pessoa_fisica")
// @DiscriminatorValue("PF")
public class PessoaFisica extends Pessoa {

	private String cpf;

	@ManyToOne
	private Agencia agencia;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "pessoa_id")
	private List<Telefone> telefones;

	@OneToMany(mappedBy = "pessoa")
	private List<Investimento> investimentos;

	@JoinColumn(name = "id_endereco")
	@OneToOne(cascade = CascadeType.ALL)
	private Endereco endereco;

	@ManyToMany
	@JoinTable(name = "pessoa_servico_bancario")
	private List<ServicoBancario> servicos;

	@Column(name = "data_nascimento")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	public PessoaFisica() {
		this.telefones = new ArrayList<>();
		this.servicos = new ArrayList<>();
		this.investimentos = new ArrayList<>();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public List<ServicoBancario> getServicos() {
		return servicos;
	}

	public void setServicos(List<ServicoBancario> servicos) {
		this.servicos = servicos;
	}

	public List<Investimento> getInvestimentos() {
		return investimentos;
	}

	public void setInvestimentos(List<Investimento> investimentos) {
		this.investimentos = investimentos;
	}
}
