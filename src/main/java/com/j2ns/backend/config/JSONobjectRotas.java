package com.j2ns.backend.config;

import java.util.List;

import com.j2ns.backend.models.auth.RotasModel;

public class JSONobjectRotas {

	private List<RotasModel> list;
	
	private int quantMarmitasEntregador;
	
	public JSONobjectRotas(List<RotasModel> list, int quantMarmita) {
		this.list = list;
		this.quantMarmitasEntregador = quantMarmita;
	}
}
