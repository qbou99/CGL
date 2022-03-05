package com.cgl.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping(value="/docs_list")
    public String docs_list() {
        return "docs_list";
    }

    @GetMapping(value="/new_doc")
    public String new_doc() {
        return "new_doc";
    }

    @GetMapping(value="/stats")
    public String stats() {
        return "stats";
    }
}
