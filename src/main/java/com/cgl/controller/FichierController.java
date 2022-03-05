package com.cgl.controller;

import com.cgl.exception.ResourceNotFoundException;
import com.cgl.model.Fichier;
import com.cgl.repository.FichierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/document")
public class FichierController {

    @Autowired
    FichierRepository fichierRepository;

    @GetMapping("")
    public List<Fichier> getAllFichiers() {
        return fichierRepository.findAll();
    }

    @GetMapping("/{id}")
    public Fichier getFichierById(@PathVariable(value = "id") Long fichierId) {
        return fichierRepository.findById(fichierId)
                .orElseThrow(() -> new ResourceNotFoundException("Fichier", "id", fichierId));
    }

    @GetMapping("/texte")
    public Fichier getFichiersTexte() {
        return fichierRepository.findByTypeFichier("Texte");
    }

    @GetMapping("/image")
    public Fichier getFichiersImage() {
        return fichierRepository.findByTypeFichier("Image");
    }

    @GetMapping("/video")
    public Fichier getFichiersVideo() {
        return fichierRepository.findByTypeFichier("Video");
    }

    @GetMapping("/audio")
    public Fichier getFichiersAudio() {
        return fichierRepository.findByTypeFichier("Audio");
    }

    @PostMapping("")
    public Fichier archiveFichier(@Valid @RequestBody Fichier fichier) {
        return fichierRepository.save(fichier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFichier(@PathVariable(value = "id") Long fichierId) {
        Fichier fichier = fichierRepository.findById(fichierId)
                .orElseThrow(() -> new ResourceNotFoundException("Fichier", "id", fichierId));
        fichierRepository.delete(fichier);
        return ResponseEntity.ok().build();
    }

}
