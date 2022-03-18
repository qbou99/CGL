package com.cgl.controller;

import com.cgl.exception.ResourceNotFoundException;
import com.cgl.model.Fichier;
import com.cgl.model.Type;
import com.cgl.repository.FichierRepository;
import com.cgl.repository.TypeRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/document")
public class FichierController {

    private final FichierRepository fichierRepository;
    private final TypeRepository typeRepository;

    public FichierController(FichierRepository fichierRepository, TypeRepository typeRepository) {
        this.fichierRepository = fichierRepository;
        this.typeRepository = typeRepository;
    }

    @GetMapping("")
    public List<Fichier> getAllFichiers() {
        return fichierRepository.findAll();
    }

    @GetMapping("/{id}")
    public Fichier getFichierById(@PathVariable(value = "id") Long fichierId) {
        return fichierRepository.findById(fichierId)
                .orElseThrow(() -> new ResourceNotFoundException("Fichier", "id", fichierId));
    }

    @PostMapping(path = "")
    public RedirectView archiveFichier(@RequestParam("file") File file, @RequestParam("nom") String nom, @RequestParam("typeFichier") String typeFichier) {
        String chemin = StringUtils.cleanPath(file.getAbsolutePath());

        Optional<Type> type = typeRepository.findByNom(typeFichier);
        Type type1;
        if (type.isEmpty()) {
            type1 = typeRepository.save(new Type(typeFichier));
        } else {
            type1 = type.get();
        }
        Fichier fichier = new Fichier(chemin, nom, type1);

        fichierRepository.save(fichier);
        return new RedirectView("new_doc");
    }

    @PostMapping(path = "/{id}")
    public RedirectView deleteFichier(@PathVariable(value = "id") Long fichierId) {
        Fichier fichier = fichierRepository.findById(fichierId)
                .orElseThrow(() -> new ResourceNotFoundException("Fichier", "id", fichierId));
        fichierRepository.delete(fichier);
        return new RedirectView("../docs_list/0");

    }

}