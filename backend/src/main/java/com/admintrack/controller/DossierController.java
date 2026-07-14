package com.admintrack.controller;

import com.admintrack.dto.DossierRequest;
import com.admintrack.dto.DossierResponse;
import com.admintrack.service.DossierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dossiers")
@RequiredArgsConstructor
public class DossierController {

    private final DossierService dossierService;

    @PostMapping
    public ResponseEntity<DossierResponse> creer(@Valid @RequestBody DossierRequest request,
                                                  Authentication authentication) {
        DossierResponse response = dossierService.creerDossier(authentication.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<DossierResponse>> lister(Authentication authentication) {
        return ResponseEntity.ok(dossierService.listerDossiersUtilisateur(authentication.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DossierResponse> detail(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(dossierService.getDossier(authentication.getName(), id));
    }
}
