package org.kkbp.proyectointernacionalizacion.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("nombre", "Mundo");
        return "index";  // Busca index.html en la carpeta templates
    }
}
