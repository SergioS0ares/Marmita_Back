package com.j2ns.backend.mocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.j2ns.backend.models.auth.ClientModel;
import com.j2ns.backend.services.auth.ClienteService;

@Component
public class TestMock implements CommandLineRunner{
	
	@Autowired
	ClienteService serv;
	
	@Override
	public void run(String... args) throws Exception {
        ClientModel mockClient = ClientMock.generateMockClient();
        ClientModel savedClient = serv.saveClient(mockClient);
        System.out.println("Cliente salvo: " + savedClient);
    }
}
