package com.j2ns.backend.models.auth;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ClientModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String descricaoEndereco;

	@Column
	private int quantPedido;
	
	@Column
	private String telefone;
	
	@Column
	private String latitude;
	
	@Column
	private String longitude;
	
	@Column
	private String sujestH;

	
	// metodos
	
	
	public UUID getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getDescricaoEndereco() {
		return descricaoEndereco;
	}
	
	public int getQuantPedido() {
		return quantPedido;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public String getSujestH() {
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
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public void setLatitude(String lat) {
		this.latitude = lat;
	}
	
	public void setLongitude(String lon) {
		this.longitude = lon;
	}
	
	public void setSujestH(String sujestH) {
		this.sujestH = sujestH;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
}
