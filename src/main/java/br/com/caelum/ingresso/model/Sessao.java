package br.com.caelum.ingresso.model;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Sessao {
	@Id
	@GeneratedValue	
	private Integer id;
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private LocalTime horario;
	
	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

	@ManyToOne
	private Sala sala;
	
	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	@ManyToOne
	private Filme filme;
	
	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	/**
	 * 
	 * @deprecated hibernate only
	 */
	public Sessao(){
		
	}
	
	public Sessao(LocalTime horario, Filme filme, Sala sala) {
		this.horario = horario;
		this.filme = filme;
		this.sala = sala;
	}
	
	public LocalTime getHorarioTermino() {
		return this.horario.plusMinutes(filme.getDuracao().toMinutes());
	}
	
	//criar getters e setters
}
