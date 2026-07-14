package com.admintrack.dto;

import com.admintrack.model.Dossier;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class DossierResponse {
    private final Long id;
    private final String titre;
    private final String statut;
    private final LocalDateTime dateCreation;
    private final String typeDemarche;
    private final LocalDate echeanceDateLimite;
    private final boolean echeanceDepassee;
    private final int nombreDocuments;

    public DossierResponse(Dossier d) {
        this.id = d.getId();
        this.titre = d.getTitre();
        this.statut = d.getStatut().name();
        this.dateCreation = d.getDateCreation();
        this.typeDemarche = d.getTypeDemarche().getNom();
        this.echeanceDateLimite = d.getEcheance() != null ? d.getEcheance().getDateLimite() : null;
        this.echeanceDepassee = d.getEcheance() != null && d.getEcheance().isDepassee();
        this.nombreDocuments = d.getDocuments() != null ? d.getDocuments().size() : 0;
    }
}
