package com.admintrack.service;

import com.admintrack.dto.AuthResponse;
import com.admintrack.dto.LoginRequest;
import com.admintrack.dto.RegisterRequest;
import com.admintrack.model.Role;
import com.admintrack.model.Utilisateur;
import com.admintrack.repository.UtilisateurRepository;
import com.admintrack.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final int MAX_TENTATIVES = 5;

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Un compte existe déjà avec cet email.");
        }
        Utilisateur u = new Utilisateur();
        u.setNom(request.getNom());
        u.setEmail(request.getEmail());
        u.setMotDePasse(passwordEncoder.encode(request.getMotDePasse())); // jamais de mot de passe en clair
        u.setRole(Role.USER);
        utilisateurRepository.save(u);

        String token = jwtUtil.generateToken(u.getEmail(), u.getRole().name());
        return new AuthResponse(token, u.getEmail(), u.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        Utilisateur u = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Identifiants invalides"));

        if (u.isCompteVerrouille()) {
            throw new IllegalStateException("Compte verrouillé après plusieurs échecs. Réessayez plus tard.");
        }

        if (!passwordEncoder.matches(request.getMotDePasse(), u.getMotDePasse())) {
            u.setTentativesEchouees(u.getTentativesEchouees() + 1);
            if (u.getTentativesEchouees() >= MAX_TENTATIVES) {
                u.setCompteVerrouille(true); // protection anti brute-force
            }
            utilisateurRepository.save(u);
            throw new IllegalArgumentException("Identifiants invalides");
        }

        u.setTentativesEchouees(0);
        utilisateurRepository.save(u);

        String token = jwtUtil.generateToken(u.getEmail(), u.getRole().name());
        return new AuthResponse(token, u.getEmail(), u.getRole().name());
    }
}
