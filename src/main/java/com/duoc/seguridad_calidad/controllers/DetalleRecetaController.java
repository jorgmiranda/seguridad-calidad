package com.duoc.seguridad_calidad.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.duoc.seguridad_calidad.dto.Receta;
import com.duoc.seguridad_calidad.security.TokenStore;
import com.duoc.seguridad_calidad.services.RecetaService;

@Controller
public class DetalleRecetaController {

    private final TokenStore tokenStore;
    private final RecetaService recetaService;

    public DetalleRecetaController(TokenStore tokenStore, RecetaService recetaService){
        this.tokenStore = tokenStore;
        this.recetaService = recetaService;
    }
   
    @GetMapping("/detallereceta")
    public String home(@RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name, Model model){
        
        
        List<Receta> recetas = recetaService.obtenerRecetasFull(tokenStore.getToken());

        model.addAttribute("recetas", recetas);
        model.addAttribute("name", name);
        model.addAttribute("token", tokenStore.getToken());
        model.addAttribute("user", tokenStore.getUserModel());

        return "detallereceta";
    }
}

    
