package com.proof.mothers.api.dtos;

import com.proof.api.dtos.AddressDto;

public class AddressDtoMother {

    public static AddressDto validAddress() {
        AddressDto addressDto = new AddressDto();
        addressDto.setStreet("Baker Street");
        addressDto.setCity("London");
        addressDto.setCountry("United Kingdom");
        addressDto.setPostcode("NW1 6XE");
        return addressDto;
    }
}
