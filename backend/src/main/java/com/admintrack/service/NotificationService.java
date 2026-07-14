package com.admintrack.service;

import com.admintrack.model.Echeance;
import com.admintrack.model.Notification;
import com.admintrack.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Envoi des notifications (email) de rappel d'échéance.
 * En cas d'échec d'envoi, l'erreur est journalisée sans exposer de donnée sensible.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender mailSender;
    private final NotificationRepository notificationRepository;

    public void notifierEcheanceProche(Echeance echeance, String emailDestinataire) {
        String message = "Votre dossier \"" + echeance.getDossier().getTitre()
                + "\" arrive à échéance le " + echeance.getDateLimite() + ".";
        envoyer(echeance, emailDestinataire, "Échéance à venir - AdminTrack", message);
    }

    public void notifierEcheanceDepassee(Echeance echeance, String emailDestinataire) {
        String message = "Le délai légal de votre dossier \"" + echeance.getDossier().getTitre()
                + "\" est dépassé depuis le " + echeance.getDateLimite()
                + ". Vous pouvez générer un courrier de relance depuis votre espace AdminTrack.";
        envoyer(echeance, emailDestinataire, "Échéance dépassée - AdminTrack", message);
    }

    private void envoyer(Echeance echeance, String destinataire, String sujet, String corps) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(destinataire);
            mail.setSubject(sujet);
            mail.setText(corps);
            mailSender.send(mail);
        } catch (Exception e) {
            // Ne jamais journaliser le contenu du mail ni de donnée personnelle sensible
            log.warn("Échec d'envoi de notification pour l'échéance id={}", echeance.getId());
        }

        Notification n = new Notification();
        n.setEcheance(echeance);
        n.setCanal("EMAIL");
        n.setMessage(corps);
        notificationRepository.save(n);
    }
}
