package com.proof.mothers.persistence.entities;

import com.proof.persistence.entities.Order;

import java.util.Date;

public class OrderMother {

    public static Order validOrder() {
        Order order = new Order();
        order.setNumber("00001");
        order.setClient(ClientMother.validClient());
        order.setDeliveryAddress(AddressMother.validAddress());
        order.setPilotes(5);
        order.setOrderTotal(25.0);
        order.setCreationDate(new Date());
        return order;
    }
}
