package com.j2ns.backend.mocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.j2ns.backend.models.auth.ClientModel;
import com.j2ns.backend.services.auth.ClienteService;

@Component // Indica que essa classe é um componente do Spring que será gerenciado pelo Spring Container.
public class TestMock implements CommandLineRunner { // A classe implementa CommandLineRunner, o que significa que o método "run" será executado na inicialização da aplicação.
	
	@Autowired
	ClienteService serv; // Injeção de dependência do serviço "ClienteService", que será usado para salvar o cliente.

	// O método "run" é executado assim que a aplicação Spring Boot é iniciada.
	@Override
	public void run(String... args) throws Exception {
        // Gera um objeto de cliente simulado (mock) usando o método "generateMockClient".
        ClientModel mockClient = ClientMock.generateMockClient();
        
        // Salva o cliente mock no banco de dados chamando o método "saveClient" do ClienteService.
        ClientModel savedClient = serv.saveClient(mockClient);
        
        // Exibe no console as informações do cliente salvo.
        System.out.println("Cliente salvo: " + savedClient);
    }
}
