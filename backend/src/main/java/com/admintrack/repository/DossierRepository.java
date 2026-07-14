package com.admintrack.repository;

import com.admintrack.model.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DossierRepository extends JpaRepository<Dossier, Long> {

    // Requete parametree : jamais de concatenation de chaine (protection anti-injection SQL)
    @Query("SELECT d FROM Dossier d WHERE d.utilisateur.id = :userId ORDER BY d.dateCreation DESC")
    List<Dossier> findByUtilisateurId(@Param("userId") Long userId);

    @Query("SELECT d FROM Dossier d WHERE d.id = :id AND d.utilisateur.id = :userId")
    java.util.Optional<Dossier> findByIdAndUtilisateurId(@Param("id") Long id, @Param("userId") Long userId);
}
