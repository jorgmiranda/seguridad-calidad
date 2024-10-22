package com.duoc.seguridad_calidad.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DetalleRecetaController {
   
    @GetMapping("/detallereceta")
    public String home(@RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name, Model model){
        model.addAttribute("name", name);
        return "detallereceta";
    }
}
