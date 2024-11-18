package com.duoc.seguridad_calidad.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.duoc.seguridad_calidad.dto.Receta;
import com.duoc.seguridad_calidad.security.TokenStore;

@Controller
public class DetalleRecetaController {

    private final TokenStore tokenStore;

    public DetalleRecetaController(TokenStore tokenStore){
        this.tokenStore = tokenStore;
    }
   
    @GetMapping("/detallereceta")
    public String home(@RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name, Model model){
        
        
        List<Receta> recetas = obtenerRecetasFull(tokenStore.getToken());

        model.addAttribute("recetas", recetas);
        model.addAttribute("name", name);
        model.addAttribute("token", tokenStore.getToken());
        model.addAttribute("user", tokenStore.getUserModel());

        return "detallereceta";
    }

    private List<Receta> obtenerRecetasFull(String token){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/recetas/full";

        // Crear los encabezados y agregar el token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        // Crear el HttpEntity con los encabezados
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Hacer la solicitud GET con los encabezados
        ResponseEntity<Receta[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Receta[].class);

        // Obtener el resultado de la respuesta
        Receta[] recetas = response.getBody();

        return Arrays.asList(recetas);
    }
}
