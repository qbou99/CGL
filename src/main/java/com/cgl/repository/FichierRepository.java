package com.cgl.repository;

import com.cgl.model.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FichierRepository extends JpaRepository<Fichier, Long> {
    Optional<Fichier> findById(Long id);

    Fichier findByTypeFichier(String typeFichier);
}
