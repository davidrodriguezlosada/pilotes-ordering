package com.proof.persistence.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "EVENTS")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 4000)
    private String data;

    @NotNull
    private Date creationDate;
}
