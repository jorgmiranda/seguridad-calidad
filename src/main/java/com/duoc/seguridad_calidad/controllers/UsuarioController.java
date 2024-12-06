package com.duoc.seguridad_calidad.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.duoc.seguridad_calidad.security.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UsuarioController {
    private final TokenStore tokenStore;

    public UsuarioController(TokenStore tokenStore){
        this.tokenStore = tokenStore;
    }

    @GetMapping("/listadousuario")
    public String navegarMantenedorUsuarios(@RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name,
            Model model) {
            
        
        
        model.addAttribute("name", name);
        
        return "usuarios/listadousuarios";
    }

    @GetMapping("/crearusuario")
    public String naveagarCrearUsuario(@RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name,
            Model model) {
            
        
        
        model.addAttribute("name", name);
        
        return "usuarios/crearusuario";
    }
    
}
