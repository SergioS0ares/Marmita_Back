package com.fachter.backend.viewModels.auth;

import java.util.UUID;

import com.fachter.backend.viewModels.other.auth.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class ClientModel {
	
	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID Id;

	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String descricaoEndereco;

	@Column(nullable = false)
	private int quantPedido;
	
	private Phone telefone;
	
	private Latitude latitude;
	
	private Longitude longitude;
	
	private JanelaHorarios sujestH;
	

	// metodos
	
	public String getNome() {
		return nome;
	}
	
	public String getDescricaoEndereco() {
		return descricaoEndereco;
	}
	
	public int getQuantPedido() {
		return quantPedido;
	}
	
	public Phone getTelefone() {
		return telefone;
	}
	
	public Latitude getLatitude() {
		return latitude;
	}
	
	public Longitude getLongitude() {
		return longitude;
	}
	
	public JanelaHorarios getSujestH() {
		return sujestH;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setDescricaoEndereco(String descricaoEndereco) {
		this.descricaoEndereco = descricaoEndereco;
	}
	
	public void setQuantPedido(int x) {
		this.quantPedido = x;
	}
	
	public void setTelefone(Phone telefone) {
		this.telefone = telefone;
	}
	
	public void setLatitude(Latitude lat) {
		this.latitude = lat;
	}
	
	public void setLongitude(Longitude lon) {
		this.longitude = lon;
	}
	
	public void setSujestH(JanelaHorarios sujestH) {
		this.sujestH = sujestH;
	}
}
