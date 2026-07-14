package com.admintrack.repository;

import com.admintrack.model.Echeance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EcheanceRepository extends JpaRepository<Echeance, Long> {

    @Query("SELECT e FROM Echeance e WHERE e.depassee = false AND e.dateLimite <= CURRENT_DATE")
    List<Echeance> findEcheancesVenantDeDepasser();
}
