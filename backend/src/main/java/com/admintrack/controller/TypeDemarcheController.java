package com.admintrack.controller;

import com.admintrack.model.TypeDemarche;
import com.admintrack.repository.TypeDemarcheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/types-demarches")
@RequiredArgsConstructor
public class TypeDemarcheController {

    private final TypeDemarcheRepository typeDemarcheRepository;

    @GetMapping
    public ResponseEntity<List<TypeDemarche>> lister() {
        return ResponseEntity.ok(typeDemarcheRepository.findAll());
    }
}
