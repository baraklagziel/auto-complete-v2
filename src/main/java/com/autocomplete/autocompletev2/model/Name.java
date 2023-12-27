package com.autocomplete.autocompletev2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@Table(name = "NAMES")
public class Name {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String firstName;

    public Name(final String firstName) {
        this.firstName = firstName;
    }
}
