package com.cgl.controller;

import com.cgl.dto.FichierDto;
import com.cgl.model.Fichier;
import com.cgl.repository.FichierRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ViewController {

    private final FichierRepository fichierRepository;
    private final FichierController fichierController;

    public ViewController(FichierRepository fichierRepository, FichierController fichierController) {
        this.fichierRepository = fichierRepository;
        this.fichierController = fichierController;
    }


    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping(value = "/docs_list/{page}")
    public String docs_list(@PathVariable(value = "page") String page, Model model, @RequestParam("nom") Optional<String> nom) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), 10);
        if(nom.isEmpty())
            nom = Optional.of("");
        List<Fichier> fichiers = fichierRepository.findByNomContaining(nom.get(), pageable);
        model.addAttribute("fichiers", fichiers);
        if (fichiers.size() == 10)
            model.addAttribute("pageSuiv", Integer.parseInt(page) + 1);
        else
            model.addAttribute("pageSuiv", -1);
        model.addAttribute("pagePrec", Integer.parseInt(page) - 1);
        model.addAttribute("nom", nom.get());
        return "docs_list";
    }

    @GetMapping(value = "/new_doc")
    public String new_doc(Model model) {
        model.addAttribute("fichier", new FichierDto());
        return "new_doc";
    }

    @GetMapping(value = "/DateStats")
    public String dateStats(Model model) {
        List<Object[]> dateStats = fichierRepository.countFichiersByDate();
        model.addAttribute("dateStats", dateStats);
        return "stats";
    }

    @GetMapping(value = "/TypeStats")
    public String typeStats(Model model) {
        List<Object[]> typeStats = fichierRepository.countFichiersByType();
        model.addAttribute("typeStats", typeStats);
        return "stats";
    }
}
