package com.j2ns.backend.mocks;

import com.github.javafaker.Faker; // Biblioteca para gerar dados falsos (mock) para testes
import com.j2ns.backend.models.auth.*; // Importa a classe ClientModel para criar instâncias mockadas
import java.util.UUID; // Importa o UUID para gerar identificadores únicos
import java.util.Random; // Importa a classe Random para gerar números aleatórios

public class ClientMock {

    private static final Faker faker = new Faker(); // Instancia o Faker para gerar dados aleatórios.
    private static final Random random = new Random(); // Instancia o Random para gerar números aleatórios.

    /* Essa classe foi criada para gerar objetos de teste (mocks) da classe ClientModel.
     * É útil para testar a requisição de clientes sem depender de dados reais. 
     * Ela preenche os campos da classe ClientModel com dados aleatórios para fins de teste.
     * */

    // Método estático que gera um mock de um cliente.
    public static ClientModel generateMockClient() {
        ClientModel client = new ClientModel(); // Cria uma nova instância de ClientModel.

        // Preenche os dados do cliente com valores aleatórios usando o Faker.
        client.setId(UUID.randomUUID()); // Gera um UUID aleatório para o ID do cliente.
        client.setNome(faker.name().fullName()); // Gera um nome completo aleatório.
        client.setDescricaoEndereco(faker.address().fullAddress()); // Gera um endereço aleatório.
        client.setQuantPedido(faker.number().numberBetween(1, 50)); // Gera um número aleatório de pedidos entre 1 e 50.
        client.setTelefone(faker.phoneNumber().phoneNumber()); // Gera um número de telefone aleatório.
        client.setLatitude(faker.address().latitude()); // Gera uma latitude aleatória para a localização.
        client.setLongitude(faker.address().longitude()); // Gera uma longitude aleatória para a localização.
        client.setSujestH(generateRandomTimeRange()); // Gera um horário aleatório no formato "HH:MM - HH:MM".

        return client; // Retorna o objeto ClientModel preenchido com dados aleatórios.
    }

    // Método que gera um intervalo de horário aleatório entre as 10:00 e as 14:00.
    private static String generateRandomTimeRange() {
        int startHour = random.nextInt(5) + 10; // Gera uma hora de início entre 10 e 14.
        int endHour = startHour + 1; // A hora final é uma hora depois da hora de início.

        // Formata as horas no formato "HH:MM".
        String startTime = String.format("%02d:00", startHour);
        String endTime = String.format("%02d:00", endHour);

        return startTime + " - " + endTime; // Retorna o intervalo de horário no formato "HH:MM - HH:MM".
    }
}
