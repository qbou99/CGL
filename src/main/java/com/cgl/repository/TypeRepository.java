package com.cgl.repository;

import com.cgl.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Optional<Type> findByNom(String nom);

}
