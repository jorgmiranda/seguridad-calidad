package com.duoc.seguridad_calidad.controllers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.duoc.seguridad_calidad.security.TokenStore;
import com.duoc.seguridad_calidad.services.RecetaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.duoc.seguridad_calidad.dto.Receta;
import com.duoc.seguridad_calidad.dto.RecetaParcial;

@Controller
public class HomeController {

    private final TokenStore tokenStore;
    private final RecetaService recetaService;

    public HomeController(TokenStore tokenStore, RecetaService recetaService) {
        this.tokenStore = tokenStore;
        this.recetaService = recetaService;
    }

    @GetMapping("/home")
    public String home(
            @RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name,
            Model model) {
        // Se obtienen las recetas
        List<RecetaParcial> recetas = recetaService.obtenerRecetas();

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

        if (tokenStore.getToken() != null) {
            model.addAttribute("token", tokenStore.getToken());
            //Se obtiene el objeto usuario

            model.addAttribute("user", tokenStore.getUserModel());

        }

        model.addAttribute("name", name);
        return "home";
    }

    @GetMapping("/")
    public String root(
            @RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name,
            Model model) {
        // Se obtienen las recetas
        List<RecetaParcial> recetas = recetaService.obtenerRecetas();

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
        if (tokenStore.getToken() != null) {
            model.addAttribute("token", tokenStore.getToken());
            model.addAttribute("user", tokenStore.getUserModel());

        }
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
            Model model) {
        System.out.println("-------------------------------------------------");
        if (!isValidInput(nombre) || !isValidInput(tipoCocina) ||
                !isValidInput(ingredientes) || !isValidInput(paisOrigen)) {
            System.out.println("inpund no valido");
            return "redirect:/home"; // redirigir a una página de error en el futuro
        }

        List<String> validDifficulties = Arrays.asList("Baja", "Media", "Alta");

        if (dificultad != null && !validDifficulties.contains(dificultad)) {

            
            return "redirect:/home"; // redirigir a una página de error en el futuro
        }

        System.out.println(nombre);
        // Se obtienen las recetas
        List<RecetaParcial> recetas = recetaService.obtenerRecetas();

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

        // Filtrar recetas
        List<Receta> recetasfiltradas = recetaService.filrarRecetas(nombre, paisOrigen, dificultad, tipoCocina, ingredientes);

        // Agregar ambas listas al modelo
        model.addAttribute("recetasPorFiltro", recetasfiltradas);
        model.addAttribute("recetasPorPopularidad", recetasPorPopularidad);
        model.addAttribute("recetasPorFecha", recetasPorFecha);

        if (tokenStore.getToken() != null) {
            model.addAttribute("token", tokenStore.getToken());
            model.addAttribute("user", tokenStore.getUserModel());

        }

        model.addAttribute("name", name);
        return "home";
    }

    private boolean isValidInput(String input) {
        // Implementa la lógica de validación, por ejemplo, longitud y caracteres
        // permitidos
        return input != null && input.length() < 255 && input.matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]*$");
    }

}
