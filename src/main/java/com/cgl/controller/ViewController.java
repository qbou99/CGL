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
    public String docs_list(@PathVariable(value = "page") String page, Model model) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), 10);
        List<Fichier> fichiers = fichierRepository.findByNomContaining("", pageable);
        model.addAttribute("fichiers", fichiers);
        model.addAttribute("page", page);
        return "docs_list";
    }

    @GetMapping(value = "/docs_list/{page}/{nom}")
    public String docs_list_nom(@PathVariable(value = "nom") String nom, @PathVariable(value = "page") String page, Model model) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), 10);
        List<Fichier> fichiers = fichierRepository.findByNomContaining(nom, pageable);
        model.addAttribute("fichiers", fichiers);
        model.addAttribute("page", page);
        model.addAttribute("nom", nom);
        return "docs_list";
    }

    @GetMapping(value = "/docs_list/pageSuivante/{page}/{nom}")
    public String docs_list_nom_suivant(@PathVariable(value = "nom") String nom, @PathVariable(value = "page") String page, Model model) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), 10);
        List<Fichier> fichiers = fichierRepository.findByNomContaining(nom, pageable);
        model.addAttribute("fichiers", fichiers);
        model.addAttribute("page", page + 1);
        model.addAttribute("nom", nom);
        return "docs_list";
    }

    @GetMapping(value = "/docs_list/pageSuivante/{page}/")
    public String docs_list_suivant(@PathVariable(value = "page") String page, Model model) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), 10);
        List<Fichier> fichiers = fichierRepository.findByNomContaining("", pageable);
        model.addAttribute("fichiers", fichiers);
        model.addAttribute("page", page + 1);
        return "docs_list";
    }

    @GetMapping(value = "/new_doc")
    public String new_doc(Model model) {
        model.addAttribute("fichier", new FichierDto());
        return "new_doc";
    }

    @GetMapping(value = "/stats")
    public String stats(Model model) {
        List<Object[]> stats = fichierRepository.countFichiersByDate();
        model.addAttribute("stats", stats);
        return "stats";
    }
}
