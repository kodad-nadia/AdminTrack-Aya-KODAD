package com.admintrack.service;

import com.admintrack.model.Dossier;
import com.admintrack.model.Echeance;
import com.admintrack.model.StatutDossier;
import com.admintrack.model.TypeDemarche;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Calcule automatiquement l'échéance légale d'un dossier a partir
 * du délai légal de référence associé a son type de démarche.
 */
@Service
@RequiredArgsConstructor
public class EcheanceService {

    public Echeance calculerEcheance(Dossier dossier, TypeDemarche typeDemarche) {
        Echeance echeance = new Echeance();
        echeance.setDossier(dossier);
        echeance.setDateLimite(LocalDate.now().plusDays(typeDemarche.getDelaiLegalJours()));
        echeance.setDepassee(false);
        return echeance;
    }

    public StatutDossier calculerStatut(Echeance echeance) {
        if (echeance == null) {
            return StatutDossier.EN_COURS;
        }
        LocalDate aujourdHui = LocalDate.now();
        long joursRestants = java.time.temporal.ChronoUnit.DAYS.between(aujourdHui, echeance.getDateLimite());

        if (aujourdHui.isAfter(echeance.getDateLimite())) {
            return StatutDossier.DEPASSE;
        } else if (joursRestants <= 7) {
            return StatutDossier.ECHEANCE_PROCHE;
        } else {
            return StatutDossier.A_JOUR;
        }
    }
}
