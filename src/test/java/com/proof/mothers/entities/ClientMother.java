package com.proof.mothers.entities;

import com.proof.domain.Client;

public class ClientMother {

    public static Client validClient() {
        Client client = new Client();
        client.setCode("00001");
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setTelephone("600800500");
        return client;
    }
}
