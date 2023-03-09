package ru.job4j.accidents.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "authorities")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Authority {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String authority;
}
