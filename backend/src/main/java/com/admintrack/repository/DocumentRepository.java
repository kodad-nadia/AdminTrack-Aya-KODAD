package com.admintrack.repository;

import com.admintrack.model.DocumentJustificatif;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentJustificatif, Long> {
    List<DocumentJustificatif> findByDossierId(Long dossierId);
}
