package com.admintrack.controller;

import com.admintrack.dto.TypeDemarcheRequest;
import com.admintrack.model.TypeDemarche;
import com.admintrack.repository.TypeDemarcheRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Toutes les routes de ce contrôleur sont réservées au rôle ADMIN
 * (double contrôle : SecurityConfig + annotation @PreAuthorize).
 */
@RestController
@RequestMapping("/api/admin/types-demarches")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final TypeDemarcheRepository typeDemarcheRepository;

    @GetMapping
    public ResponseEntity<List<TypeDemarche>> lister() {
        return ResponseEntity.ok(typeDemarcheRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<TypeDemarche> creer(@Valid @RequestBody TypeDemarcheRequest request) {
        TypeDemarche t = new TypeDemarche();
        t.setNom(request.getNom());
        t.setOrganisme(request.getOrganisme());
        t.setDelaiLegalJours(request.getDelaiLegalJours());
        t.setDescription(request.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(typeDemarcheRepository.save(t));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeDemarche> modifier(@PathVariable Long id, @Valid @RequestBody TypeDemarcheRequest request) {
        TypeDemarche t = typeDemarcheRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Type de démarche introuvable"));
        t.setNom(request.getNom());
        t.setOrganisme(request.getOrganisme());
        t.setDelaiLegalJours(request.getDelaiLegalJours());
        t.setDescription(request.getDescription());
        return ResponseEntity.ok(typeDemarcheRepository.save(t));
    }
}
