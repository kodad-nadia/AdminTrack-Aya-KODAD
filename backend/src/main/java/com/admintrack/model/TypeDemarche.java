package com.admintrack.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "type_demarche")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeDemarche {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(length = 150)
    private String organisme;

    @Column(name = "delai_legal_jours", nullable = false)
    private int delaiLegalJours;

    @Column(length = 500)
    private String description;
}
