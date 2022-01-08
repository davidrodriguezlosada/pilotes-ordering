package com.proof.mothers.domain;

import com.proof.api.dtos.ClientDto;

public class ClientDtoMother {

    public static ClientDto validClient() {
        ClientDto clientDto = new ClientDto();
        clientDto.setCode("00001");
        clientDto.setFirstName("John");
        clientDto.setLastName("Doe");
        clientDto.setTelephone("600800500");
        return clientDto;
    }
}
