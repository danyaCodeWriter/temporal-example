package ru.danil.trysomething.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "checks")
@Data
@ToString
@IdClass(CompositeKey.class)
public class Check {
    @Column
    @Id
    Long fromId;

    @Column
    @Id
    Long toId;

    @Column(name = "amount")
    Long amount;
}
