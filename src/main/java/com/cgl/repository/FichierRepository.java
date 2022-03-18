package com.cgl.repository;

import com.cgl.model.Fichier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FichierRepository extends JpaRepository<Fichier, Long> {
    Optional<Fichier> findById(Long id);

    List<Fichier> findByNomContaining(String nom, Pageable pageable);

    List<Fichier> findAllByOrderByDateDesc();
    @Query(value = "SELECT c.date, COUNT(c.date) FROM Fichier AS c GROUP BY c.date")
    List<Object[]> countFichiersByDate();

    List<Fichier> findAllByOrderByTypeFichierDesc();
    @Query(value = "SELECT c.typeFichier, COUNT(c.typeFichier) FROM Fichier AS c GROUP BY c.typeFichier")
    List<Object[]> countFichiersByType();

}
