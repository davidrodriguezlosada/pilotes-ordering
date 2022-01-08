package com.proof.mothers.entities;

import com.proof.domain.Address;

public class AddressMother {

    public static Address validAddress() {
        Address address = new Address();
        address.setStreet("Baker Street");
        address.setCity("London");
        address.setCountry("United Kingdom");
        address.setPostcode("NW1 6XE");
        return address;
    }
}
