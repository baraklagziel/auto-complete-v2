package com.autocomplete.autocompletev2.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "NAMES")
public class Name {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String firstName;
}
