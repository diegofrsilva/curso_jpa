package com.dextrainning.banco;

import org.junit.Assert;
import org.junit.Test;

public class ContaCorrenteServiceTest {

	
	@Test
	public void buscarPorNumeroTest() {
		NumeroContaCorrente numero = new NumeroContaCorrente();
		numero.setNumero("123");
		numero.setDigitoVerificador("1");
		
		ContaCorrente conta = new ContaCorrente();
		conta.setNumero(numero);
		
		ContaCorrenteService service = new ContaCorrenteService();
		service.salvar(conta);
		
		ContaCorrente contaEncontrada = service.buscarPorNumero("4");
		Assert.assertNull(contaEncontrada);
		
		contaEncontrada = service.buscarPorNumero("123");
		Assert.assertNotNull(contaEncontrada);
	}
}
