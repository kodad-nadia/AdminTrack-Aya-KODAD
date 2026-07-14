package com.admintrack.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "echeance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Echeance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_limite", nullable = false)
    private LocalDate dateLimite;

    @Column(nullable = false)
    private boolean depassee = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_id", nullable = false, unique = true)
    private Dossier dossier;

    public boolean estDepassee() {
        return LocalDate.now().isAfter(dateLimite);
    }
}
