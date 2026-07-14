package com.admintrack.service;

import com.admintrack.dto.DossierRequest;
import com.admintrack.dto.DossierResponse;
import com.admintrack.model.*;
import com.admintrack.repository.DossierRepository;
import com.admintrack.repository.TypeDemarcheRepository;
import com.admintrack.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DossierService {

    private final DossierRepository dossierRepository;
    private final TypeDemarcheRepository typeDemarcheRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final EcheanceService echeanceService;

    public DossierResponse creerDossier(String emailUtilisateur, DossierRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(emailUtilisateur)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        TypeDemarche type = typeDemarcheRepository.findById(request.getTypeDemarcheId())
                .orElseThrow(() -> new IllegalArgumentException("Type de démarche introuvable"));

        Dossier dossier = new Dossier();
        dossier.setTitre(request.getTitre());
        dossier.setUtilisateur(utilisateur);
        dossier.setTypeDemarche(type);
        dossier.setStatut(StatutDossier.EN_COURS);

        Echeance echeance = echeanceService.calculerEcheance(dossier, type);
        dossier.setEcheance(echeance);
        dossier.setStatut(echeanceService.calculerStatut(echeance));

        Dossier saved = dossierRepository.save(dossier);
        return new DossierResponse(saved);
    }

    // Toujours filtrer par utilisateur proprietaire : controle d'acces horizontal (RBAC + isolation des donnees)
    public List<DossierResponse> listerDossiersUtilisateur(String emailUtilisateur) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(emailUtilisateur)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        return dossierRepository.findByUtilisateurId(utilisateur.getId())
                .stream()
                .map(DossierResponse::new)
                .collect(Collectors.toList());
    }

    public DossierResponse getDossier(String emailUtilisateur, Long dossierId) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(emailUtilisateur)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        Dossier dossier = dossierRepository.findByIdAndUtilisateurId(dossierId, utilisateur.getId())
                .orElseThrow(() -> new IllegalArgumentException("Dossier introuvable ou accès refusé"));

        return new DossierResponse(dossier);
    }
}
