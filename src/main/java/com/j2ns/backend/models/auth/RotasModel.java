package com.j2ns.backend.models.auth;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RotasModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column
	private String nome;
	
	@Column
	private String latitude;
	
	@Column
	private String longitude;
	
	@Column
	private int quantidadeMarmitas;
	
	@Column
	private double distanciaViagem;
	
	@Column
	private double tempoViagem;
	
	@Column
	private String sujestH;
	
	@Column
	private int quantMarmitaEntregador;
	
	
	// metodos
	
	public UUID getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public int getQuantidadeMarmitas() {
		return quantidadeMarmitas;
	}
	
	public double getDistanciaViagem() {
		return distanciaViagem;
	}
	
	public double getTempoViagem() {
		return tempoViagem;
	}
	
	public String getSujestH() {
		return sujestH;
	}
	
	public int getQuantMarmitaEntregador() {
		return quantMarmitaEntregador;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public void setQuantidadeMarmitas(int quantidadeMarmitas) {
		this.quantidadeMarmitas = quantidadeMarmitas;
	}
	
	public void setDistanciaViagem(double distanciaViagem) {
		this.distanciaViagem = distanciaViagem;
	}
	
	public void setTempoViagem(double tempoViagem) {
		this.tempoViagem = tempoViagem;
	}
	
	public void setSujestH(String sujestH) {
		this.sujestH = sujestH;
	}
	
	public void setQuantMarmitaEntregador(int quantMarmitaEntregador) {
		this.quantMarmitaEntregador = quantMarmitaEntregador;
	}
}
