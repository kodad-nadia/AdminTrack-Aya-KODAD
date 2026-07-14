package com.admintrack.service;

import com.admintrack.model.Dossier;
import com.admintrack.model.Echeance;
import com.admintrack.repository.EcheanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Tâche planifiée quotidienne : détecte les échéances proches ou dépassées
 * et déclenche les notifications correspondantes (BF05).
 */
@Service
@RequiredArgsConstructor
public class EcheanceSchedulerService {

    private final EcheanceRepository echeanceRepository;
    private final NotificationService notificationService;
    private final EcheanceService echeanceService;

    // Tous les jours a 7h du matin
    @Scheduled(cron = "0 0 7 * * *")
    public void verifierEcheances() {
        List<Echeance> echeances = echeanceRepository.findAll();

        for (Echeance echeance : echeances) {
            Dossier dossier = echeance.getDossier();
            String email = dossier.getUtilisateur().getEmail();
            long joursRestants = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), echeance.getDateLimite());

            if (echeance.estDepassee() && !echeance.isDepassee()) {
                echeance.setDepassee(true);
                echeanceRepository.save(echeance);
                notificationService.notifierEcheanceDepassee(echeance, email);
            } else if (!echeance.isDepassee() && (joursRestants == 7 || joursRestants == 1)) {
                notificationService.notifierEcheanceProche(echeance, email);
            }

            dossier.setStatut(echeanceService.calculerStatut(echeance));
        }
    }
}
