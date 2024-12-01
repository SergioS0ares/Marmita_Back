package com.j2ns.backend.mocks;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TesteEntity {

	@Id
	private String msg;
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
