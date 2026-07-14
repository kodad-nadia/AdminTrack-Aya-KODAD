package com.admintrack.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeDemarcheRequest {

    @NotBlank
    private String nom;

    private String organisme;

    @Min(1)
    private int delaiLegalJours;

    private String description;
}
