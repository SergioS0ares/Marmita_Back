package com.j2ns.backend.mocks;

import com.github.javafaker.Faker;
import com.j2ns.backend.models.auth.*;
import java.util.UUID;

public class ClientMock {

    private static final Faker faker = new Faker();
    
    /* Essa daqui é uma classe feita inteiramente para criar um mock (objeto de teste)
     * da classe cliente para fins de teste da requisiçao de clientes.
     * */

    public static ClientModel generateMockClient() {
        ClientModel client = new ClientModel();
        client.setId(UUID.randomUUID());
        client.setNome(faker.name().fullName());
        client.setDescricaoEndereco(faker.address().fullAddress());
        client.setQuantPedido(faker.number().numberBetween(1, 50));
        client.setTelefone(faker.phoneNumber().phoneNumber());
        client.setLatitude(faker.address().latitude());
        client.setLongitude(faker.address().longitude());
        client.setSujestH(faker.lorem().sentence());
        return client;
    }
}
