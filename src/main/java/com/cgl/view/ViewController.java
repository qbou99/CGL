package com.cgl.view;

import com.cgl.dto.FichierDto;
import com.cgl.model.Fichier;
import com.cgl.repository.FichierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ViewController {

    @Autowired
    FichierRepository fichierRepository;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping(value = "/docs_list")
    public String docs_list(Model model) {
        model.addAttribute("fichiers", fichierRepository.findAll());
        return "docs_list";
    }

    @GetMapping(value = "/new_doc")
    public String new_doc(Model model) {
        model.addAttribute("fichier", new FichierDto());
        return "new_doc";
    }

    @GetMapping(value = "/stats")
    public String stats(Model model) {
        List<Fichier> fichiers = fichierRepository.findAll();
        Map<Date, Integer> stats = new HashMap<>();

        for (Fichier fichier : fichiers) {
            if (!stats.containsKey(fichier.getDate()))
                stats.put(fichier.getDate(), 1);
            else
                stats.put(fichier.getDate(), stats.get(fichier.getDate()) + 1);

        }
        model.addAttribute("stats", stats);

        return "stats";
    }
}
