package com.j2ns.backend.models.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RotasModel {

	@Id
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
	
	
	// metodos
	
	
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
	
}
