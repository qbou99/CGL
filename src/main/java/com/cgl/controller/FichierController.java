package com.cgl.controller;

import com.cgl.exception.ResourceNotFoundException;
import com.cgl.model.Fichier;
import com.cgl.model.TypeFichier;
import com.cgl.repository.FichierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
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
        return fichierRepository.findByTypeFichier(TypeFichier.Texte);
    }

    @GetMapping("/image")
    public Fichier getFichiersImage() {
        return fichierRepository.findByTypeFichier(TypeFichier.Image);
    }

    @GetMapping("/video")
    public Fichier getFichiersVideo() {
        return fichierRepository.findByTypeFichier(TypeFichier.Video);
    }

    @GetMapping("/audio")
    public Fichier getFichiersAudio() {
        return fichierRepository.findByTypeFichier(TypeFichier.Audio);
    }

    @PostMapping(path = "")
    public Fichier archiveFichier(@RequestParam("file") File file, @RequestParam("nom") String nom, @RequestParam("typeFichier") String typeFichier) {
        String chemin = StringUtils.cleanPath(file.getAbsolutePath());
        Fichier fichier = new Fichier(chemin, nom, stringToTypeFichier(typeFichier));
        return fichierRepository.save(fichier);
    }

    private TypeFichier stringToTypeFichier(String typeFichier) {
        switch (typeFichier) {
            case "Image":
                return TypeFichier.Image;
            case "Texte":
                return TypeFichier.Texte;
            case "Audio":
                return TypeFichier.Audio;
            case "Video":
                return TypeFichier.Video;
            case "Binaire":
                return TypeFichier.Binaire;
            default:
                return TypeFichier.Inconnu;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFichier(@PathVariable(value = "id") Long fichierId) {
        Fichier fichier = fichierRepository.findById(fichierId)
                .orElseThrow(() -> new ResourceNotFoundException("Fichier", "id", fichierId));
        fichierRepository.delete(fichier);
        return ResponseEntity.ok().build();
    }

}
