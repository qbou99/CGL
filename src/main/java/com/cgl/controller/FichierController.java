package com.cgl.controller;

import com.cgl.exception.ResourceNotFoundException;
import com.cgl.model.Fichier;
import com.cgl.model.TypeFichier;
import com.cgl.repository.FichierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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

    @GetMapping("/nom/{nom}")
    public List<Fichier> getFichierContainingNom(@PathVariable(value = "nom") String nomFichier) {
        return fichierRepository.findByNomContaining(nomFichier);
    }

    @GetMapping("/{id}")
    public Fichier getFichierById(@PathVariable(value = "id") Long fichierId) {
        return fichierRepository.findById(fichierId)
                .orElseThrow(() -> new ResourceNotFoundException("Fichier", "id", fichierId));
    }

    @PostMapping(path = "")
    public RedirectView archiveFichier(@RequestParam("file") File file, @RequestParam("nom") String nom, @RequestParam("typeFichier") String typeFichier) {
        String chemin = StringUtils.cleanPath(file.getAbsolutePath());
        Fichier fichier = new Fichier(chemin, nom, stringToTypeFichier(typeFichier));
        fichierRepository.save(fichier);
        return new RedirectView("new_doc");
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

    @PostMapping(path = "/{id}")
    public RedirectView deleteFichier(@PathVariable(value = "id") Long fichierId) {
        Fichier fichier = fichierRepository.findById(fichierId)
                .orElseThrow(() -> new ResourceNotFoundException("Fichier", "id", fichierId));
        fichierRepository.delete(fichier);
        return new RedirectView("../docs_list");

    }

}
