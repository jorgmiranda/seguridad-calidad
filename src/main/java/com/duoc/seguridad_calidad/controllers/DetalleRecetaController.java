package com.duoc.seguridad_calidad.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.duoc.seguridad_calidad.dto.Receta;

@Controller
public class DetalleRecetaController {
   
    @GetMapping("/detallereceta")
    public String home(@RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name, Model model){
        
        List<Receta> recetas = obtenerRecetasFull();

        model.addAttribute("recetas", recetas);
        model.addAttribute("name", name);

        return "detallereceta";
    }

    private List<Receta> obtenerRecetasFull(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/recetas/full";

        Receta []recetas = restTemplate.getForObject(url, Receta[].class);

        return Arrays.asList(recetas);
    }
}
