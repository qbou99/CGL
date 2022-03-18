package com.cgl.controller;

import com.cgl.dto.FichierDto;
import com.cgl.repository.FichierRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping(value = "/docs_list")
    public String docs_list(Model model) {
        model.addAttribute("fichiers", fichierController.fichiers);
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
