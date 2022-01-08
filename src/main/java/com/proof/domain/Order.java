package com.proof.domain;

import com.proof.validations.ValidPilotesNumber;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "ORDERS")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String number;

    @ManyToOne
    @NotNull
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "address_id")
    @NotNull
    private Address deliveryAddress;

    @NotNull
    @ValidPilotesNumber
    private Integer pilotes;

    @NotNull
    private Double orderTotal;

    @NotNull
    private Date creationDate;
}
