package com.jpsolanoc.clientcore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer extends Person {
    //Los id son manejados por JPA el id de persona es el de customer.
    private String password;
    private Boolean state;
}