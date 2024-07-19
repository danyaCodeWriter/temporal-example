package ru.danil.trysomething.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "address")
@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String address;

}
