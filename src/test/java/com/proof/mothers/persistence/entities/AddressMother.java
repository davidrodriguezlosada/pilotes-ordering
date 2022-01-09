package com.proof.mothers.persistence.entities;

import com.proof.persistence.entities.Address;

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
