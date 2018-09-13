package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Lugar;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;
import br.com.caelum.ingresso.model.descontos.DescontoParaBancos;
import br.com.caelum.ingresso.model.descontos.DescontoParaEstudantes;
import br.com.caelum.ingresso.model.descontos.SemDesconto;

public class DescontoTest {

	private Filme filme;
	private Sala sala;
	private Sessao sessao;
	private Lugar lugar;
	
	@Before
	public void preparaDesconto(){
		this.lugar = new Lugar("A", 1);
		this.sala = new Sala("Eldorado - IMax", new BigDecimal("20.5"));
		this.filme = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("12"));
		this.sessao = new Sessao(LocalTime.parse("10:00:00"), filme, sala);
	}
	
	@Test
	public void naoDeveConcederDescontoParaIngressoNormal() {
		Ingresso ingresso = new Ingresso(this.sessao, TipoDeIngresso.INTEIRO, lugar);
		
		BigDecimal precoEsperado = this.sala.getPreco().add(filme.getPreco());
		
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
	
	@Test
	public void deveConcederDescontoDe30PorcentoParaIngressoDeClientesDeBancos() {
		Ingresso ingresso = new Ingresso(this.sessao, TipoDeIngresso.BANCO, lugar);
		
		BigDecimal precoAux = this.sala.getPreco().add(this.filme.getPreco());
		BigDecimal precoEsperado = precoAux.subtract(trintaPorCentoSobre(precoAux)).setScale(2, RoundingMode.UNNECESSARY);
		
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
	
	@Test
	public void deveConcederDescontoDe50PorcentoParaIngressoDeEstudante() {
		Ingresso ingresso = new Ingresso(this.sessao, TipoDeIngresso.ESTUDANTE, lugar);
		
		BigDecimal precoEsperado = this.sala.getPreco().add(this.filme.getPreco()).divide(new BigDecimal("2.0"));
		
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
	
	private BigDecimal trintaPorCentoSobre(BigDecimal precoOriginal) {
		return precoOriginal.multiply(new BigDecimal("0.3"));
	}

}
