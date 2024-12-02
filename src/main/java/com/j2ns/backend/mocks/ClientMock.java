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
     */

    // Método que gera uma latitude dentro de Goiânia ou Brasil.
    private static double generateLatitude(boolean isGoiania) {
        if (isGoiania) {
            // Limites de Goiânia
            return -16.744 + (random.nextDouble() * (-16.564 - (-16.744)));
        } else {
            // Limites do Brasil
            return -33.75 + (random.nextDouble() * (5.27 - (-33.75)));
        }
    }

    // Método que gera uma longitude dentro de Goiânia ou Brasil.
    private static double generateLongitude(boolean isGoiania) {
        if (isGoiania) {
            // Limites de Goiânia
            return -49.375 + (random.nextDouble() * (-49.098 - (-49.375)));
        } else {
            // Limites do Brasil
            return -73.99 + (random.nextDouble() * (-34.79 - (-73.99)));
        }
    }

    // Método estático que gera um mock de um cliente.
    public static ClientModel generateMockClient() {
        ClientModel client = new ClientModel(); // Cria uma nova instância de ClientModel.

        // Preenche os dados do cliente com valores aleatórios usando o Faker.
        client.setId(UUID.randomUUID()); // Gera um UUID aleatório para o ID do cliente.
        client.setNome(faker.name().fullName()); // Gera um nome completo aleatório.
        client.setDescricaoEndereco(faker.address().fullAddress()); // Gera um endereço aleatório.
        client.setQuantPedido(faker.number().numberBetween(1, 50)); // Gera um número aleatório de pedidos entre 1 e 50.
        client.setTelefone(faker.phoneNumber().phoneNumber()); // Gera um número de telefone aleatório.

        // Define se as coordenadas devem ser de Goiânia.
        boolean isGoiania = true;

        client.setLatitude(String.valueOf(generateLatitude(isGoiania))); // Gera latitude.
        client.setLongitude(String.valueOf(generateLongitude(isGoiania))); // Gera longitude.
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
