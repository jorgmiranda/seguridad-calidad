package com.duoc.seguridad_calidad.controllers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.duoc.seguridad_calidad.security.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.duoc.seguridad_calidad.dto.Filtro;
import com.duoc.seguridad_calidad.dto.Receta;
import com.duoc.seguridad_calidad.dto.RecetaParcial;

@Controller
public class HomeController {


    private final TokenStore tokenStore;

    public HomeController(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @GetMapping("/home")
    public String home(@RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name, Model model){
        //Se obtienen las recetas
        List<RecetaParcial> recetas = obtenerRecetas();

        // Ordenar por popularidad (mayor a menor)
        List<RecetaParcial> recetasPorPopularidad = recetas.stream()
            .sorted(Comparator.comparingInt(RecetaParcial::getPopularidad).reversed())
            .limit(3)
            .collect(Collectors.toList());

        // Ordenar por fecha de creación (más reciente primero)
        List<RecetaParcial> recetasPorFecha = recetas.stream()
            .sorted(Comparator.comparing(RecetaParcial::getFechaCreacion).reversed())
            .limit(3)
            .collect(Collectors.toList());

        // Agregar ambas listas al modelo
        model.addAttribute("recetasPorPopularidad", recetasPorPopularidad);
        model.addAttribute("recetasPorFecha", recetasPorFecha);
        model.addAttribute("token", tokenStore.getToken());

        model.addAttribute("name", name);
        return "home";
    }

    @GetMapping("/")
    public String root(@RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name, Model model){
        //Se obtienen las recetas
        List<RecetaParcial> recetas = obtenerRecetas();

        // Ordenar por popularidad (mayor a menor)
        List<RecetaParcial> recetasPorPopularidad = recetas.stream()
            .sorted(Comparator.comparingInt(RecetaParcial::getPopularidad).reversed())
            .limit(3)
            .collect(Collectors.toList());

        // Ordenar por fecha de creación (más reciente primero)
        List<RecetaParcial> recetasPorFecha = recetas.stream()
            .sorted(Comparator.comparing(RecetaParcial::getFechaCreacion).reversed())
            .limit(3)
            .collect(Collectors.toList());

        // Agregar ambas listas al modelo
        model.addAttribute("recetasPorPopularidad", recetasPorPopularidad);
        model.addAttribute("recetasPorFecha", recetasPorFecha);
        
        model.addAttribute("name", name);
        return "home";
    }

    @PostMapping("/filtrar")
    public String filtrar(
                        @RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name,
                        @RequestParam(required = false) String nombre,
                        @RequestParam(required = false) String tipoCocina,
                        @RequestParam(required = false) String ingredientes,
                        @RequestParam(required = false) String paisOrigen,
                        @RequestParam(required = false) String dificultad,
                        Model model){

        if (!isValidInput(nombre) || !isValidInput(tipoCocina) ||
        !isValidInput(ingredientes) || !isValidInput(paisOrigen)) {

            return "home"; //redirigir a una página de error en el futuro
        }

        List<String> validDifficulties = Arrays.asList("Baja", "Media", "Alta");

        if (!validDifficulties.contains(dificultad)) {

            return "home"; //redirigir a una página de error en el futuro
        }


         //Se obtienen las recetas
         List<RecetaParcial> recetas = obtenerRecetas();

         // Ordenar por popularidad (mayor a menor)
         List<RecetaParcial> recetasPorPopularidad = recetas.stream()
             .sorted(Comparator.comparingInt(RecetaParcial::getPopularidad).reversed())
             .limit(3)
             .collect(Collectors.toList());
 
         // Ordenar por fecha de creación (más reciente primero)
         List<RecetaParcial> recetasPorFecha = recetas.stream()
             .sorted(Comparator.comparing(RecetaParcial::getFechaCreacion).reversed())
             .limit(3)
             .collect(Collectors.toList());

        //Filtrar recetas
        List<Receta> recetasfiltradas = filrarRecetas(nombre, paisOrigen, dificultad, tipoCocina, ingredientes);
        

         // Agregar ambas listas al modelo
         model.addAttribute("recetasPorFiltro", recetasfiltradas);
         model.addAttribute("recetasPorPopularidad", recetasPorPopularidad);
         model.addAttribute("recetasPorFecha", recetasPorFecha);
            
        model.addAttribute("name", name);
        return "home";              
    }


    private boolean isValidInput(String input) {
        // Implementa la lógica de validación, por ejemplo, longitud y caracteres permitidos
        return input != null && input.length() < 255 && input.matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]*$");
    }

    private List<RecetaParcial> obtenerRecetas(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/recetas/parcial";

        RecetaParcial []recetas = restTemplate.getForObject(url, RecetaParcial[].class);

        return Arrays.asList(recetas);
    }

    private List<Receta> filrarRecetas(String nombre, String pais, String dificultad, String tipo, String ingrediente){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/recetas/filtrar";
        
        //Valores 
        Filtro filtro = new Filtro(nombre, pais, dificultad, tipo, ingrediente);

        Receta[] recetas = restTemplate.postForObject(url, filtro, Receta[].class);

        return Arrays.asList(recetas);
    }
}
