package com.cgl.controller;

import com.cgl.exception.ResourceNotFoundException;
import com.cgl.model.Fichier;
import com.cgl.model.TypeFichier;
import com.cgl.repository.FichierRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/document")
public class FichierController {

    private final FichierRepository fichierRepository;

    List<Fichier> fichiers;
    int page = 0;
    String nomCherche = "";

    public FichierController(FichierRepository fichierRepository) {
        this.fichierRepository = fichierRepository;
        Pageable pageable = PageRequest.of(page, 10);
        this.fichiers = fichierRepository.findByNomContaining(nomCherche, pageable);
    }

    @GetMapping("")
    public List<Fichier> getAllFichiers() {
        return fichierRepository.findAll();
    }

    @GetMapping("/nom")
    public RedirectView getFichierContainingNom(@RequestParam(value = "nom") String nomFichier) {
        this.page = 0;
        this.nomCherche = nomFichier;
        Pageable pageable = PageRequest.of(page, 10);
        fichiers = fichierRepository.findByNomContaining(nomFichier, pageable);
        return new RedirectView("../../docs_list");
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

    @PostMapping(path = "/p")
    public RedirectView addPage() {
        if (this.page < (fichierRepository.findAll().size() / 10)) {
            this.page = this.page + 1;
            Pageable pageable = PageRequest.of(page, 10);
            this.fichiers = fichierRepository.findByNomContaining(nomCherche, pageable);
        }
        return new RedirectView("../docs_list");
    }

    @PostMapping(path = "/m")
    public RedirectView deletePage() {
        if (this.page > 0) {
            this.page = this.page - 1;
            Pageable pageable = PageRequest.of(page, 10);
            this.fichiers = fichierRepository.findByNomContaining(nomCherche, pageable);
        }
        return new RedirectView("../docs_list");
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
