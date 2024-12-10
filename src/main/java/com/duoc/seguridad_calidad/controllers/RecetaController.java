package com.duoc.seguridad_calidad.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import com.duoc.seguridad_calidad.dto.ComentarioReceta;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.duoc.seguridad_calidad.dto.CrearReceta;
import com.duoc.seguridad_calidad.dto.Ingrediente;
import com.duoc.seguridad_calidad.dto.Receta;
import com.duoc.seguridad_calidad.security.TokenStore;
import com.duoc.seguridad_calidad.services.RecetaService;

@Controller
public class RecetaController {
    private final TokenStore tokenStore;
    private final RecetaService recetaService;

    public RecetaController(TokenStore tokenStore, RecetaService recetaService){
        this.tokenStore = tokenStore;
        this.recetaService = recetaService;
    }

    @GetMapping("/crearreceta")
    public String home(
            @RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name,
            Model model) {

        List<Ingrediente> ingredientes = recetaService.obtenIngredientes(tokenStore.getToken());

        model.addAttribute("ingredientesback", ingredientes);
        model.addAttribute("name", name);

        model.addAttribute("token", tokenStore.getToken());
        model.addAttribute("user", tokenStore.getUserModel());

        return "crearreceta";
    }

    @PostMapping("/receta/comentar/{id}")
    public String comentar(
            @RequestParam("comentario") String comentario,
            @RequestParam("calificacion") int calificacion,
            @PathVariable Long id, Model model
            ) {

        ComentarioReceta comment = new ComentarioReceta();

        comment.setComentario(comentario);
        comment.setIdReceta(id);
        comment.setIdUsuario(tokenStore.getUserModel().getId());
        comment.setCalificacion(calificacion);

        List<ComentarioReceta> comentarios = recetaService.obtenerComentarios(id);

        model.addAttribute("comentarios", comentarios);
        Receta receta = recetaService.obtenerRecetasByID(id, tokenStore.getToken());



        model.addAttribute("receta", receta);
        model.addAttribute("token", tokenStore.getToken());
        model.addAttribute("user", tokenStore.getUserModel());

        recetaService.comentarRecetas(comment, tokenStore.getToken());

        return "recetas/verreceta";
    }

    @PostMapping("/publicar")
    public String publicarReceta(
            @RequestParam("nombre") String nombre,
            @RequestParam("tipoCocina") String tipoCocina,
            @RequestParam("paisOrigen") String paisOrigen,
            @RequestParam("dificultad") String dificultadElaboracion,
            @RequestParam("instruccionesPreparacion") String instruccionesPreparacion,
            @RequestParam("tiempoCoccion") Integer tiempoCoccion,
            @RequestParam("imagen") MultipartFile imagen,
            @RequestParam("ingredientes") List<String> ingredientes,
            Model model) {

        CrearReceta receta = new CrearReceta();
        receta.setNombre(nombre);
        receta.setTipoDeCocina(tipoCocina);
        receta.setPaisDeOrigen(paisOrigen);
        receta.setDificultadElaboracion(dificultadElaboracion);
        receta.setInstruccionesPreparacion(instruccionesPreparacion);
        receta.setTiempoCoccion(tiempoCoccion);
        receta.setIngredientes(ingredientes);
        receta.setPopularidad(1);
        receta.setUsuarioId(tokenStore.getUserModel().getId());

        // Verificar que la imagen no esté vacía
        if (!imagen.isEmpty()) {
            try {
                // Obtener el directorio en el que se guardará la imagen
                String uploadDir = "src/main/resources/static/images/recetas/";

                // Crear el directorio si no existe
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath); // Crear directorios si no existen
                }

                // Obtener el nombre del archivo original y la ruta completa
                String imagePath = uploadDir + imagen.getOriginalFilename();

                // Copiar el archivo al directorio de destino
                Files.copy(imagen.getInputStream(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);

                // Establecer la ruta de la imagen en el atributo de la receta
                receta.setUrlImagen("/images/recetas/" + imagen.getOriginalFilename()); // Solo la ruta relativa a los
                                                                                        // recursos

            } catch (IOException e) {
                model.addAttribute("error", "Error al subir la imagen: " + e.getMessage());
                return "crearreceta";
            }
        } else {
            model.addAttribute("error", "Imagen vacía");
            return "crearreceta";
        }

        // Se imprimen los valores de receta para ver que se envian sin problemas
        System.out.println(receta.getNombre());
        System.out.println(receta.getIngredientes());
        System.out.println(receta.getInstruccionesPreparacion());
        System.out.println(receta.getTipoDeCocina());
        System.out.println(receta.getPaisDeOrigen());
        System.out.println(receta.getTiempoCoccion());
        System.out.println(receta.getDificultadElaboracion());

        ResponseEntity<?> response = recetaService.publicarReceta(receta, tokenStore.getToken());

        // Verificar si la solicitud fue exitosa
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Receta publicada con éxito");
        } else {
            model.addAttribute("error", "Hubo un problema al publicar la receta");
        }

        List<Ingrediente> ingredientesselect = recetaService.obtenIngredientes(tokenStore.getToken());
        model.addAttribute("ingredientesback", ingredientesselect);
        
        model.addAttribute("token", tokenStore.getToken());
        model.addAttribute("user", tokenStore.getUserModel());

        return "crearreceta";
    }

    @GetMapping("/vermisrecetas/{id}")
    public String vermisrecetas(
            @RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name,
            @PathVariable Long id, Model model) {

        List<Receta> recetas = recetaService.obtenerRecetasUsuario(id, tokenStore.getToken());

        model.addAttribute("recetasCreadas", recetas);
        model.addAttribute("name", name);

        model.addAttribute("token", tokenStore.getToken());
        model.addAttribute("user", tokenStore.getUserModel());

        return "recetas/vermisrecetas";
    }

    @GetMapping("/editarmireceta/{id}")
    public String editarmireceta(
            @RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name,
            @PathVariable Long id, Model model) {

        Receta receta = recetaService.obtenerRecetasByID(id, tokenStore.getToken());
        List<Ingrediente> ingredientes = recetaService.obtenIngredientes(tokenStore.getToken());

        List<String> ingredientesSeleccionados = receta.getRecetaIngredientes().stream()
                .map(Ingrediente::getNombreIngrediente) // Extrae los nombres
                .collect(Collectors.toList());

        model.addAttribute("ingredientesback", ingredientes);
        model.addAttribute("ingredientesSeleccionados", ingredientesSeleccionados);
        model.addAttribute("receta", receta);
        model.addAttribute("name", name);

        model.addAttribute("token", tokenStore.getToken());
        model.addAttribute("user", tokenStore.getUserModel());

        return "recetas/editarmireceta";
    }

    @GetMapping("/verreceta/{id}")
    public String verReceta(
            @PathVariable Long id,
            Model model) {

        List<ComentarioReceta> comentarios = recetaService.obtenerComentarios(id);

        model.addAttribute("comentarios", comentarios);
                
        Receta receta = recetaService.obtenerRecetasByID(id, tokenStore.getToken());

        model.addAttribute("receta", receta);
        model.addAttribute("token", tokenStore.getToken());
        model.addAttribute("user", tokenStore.getUserModel());

        return "recetas/verreceta";

    }

    @PostMapping("/editar/{id}")
    public String editarReceta(@PathVariable Long id, Receta receta,
            @RequestParam("imagen") MultipartFile imagen,
            @RequestParam("ingredientes") List<String> ingredientes,
            Model model) {

        CrearReceta recetaEdit = new CrearReceta();
        recetaEdit.setNombre(receta.getNombre());
        recetaEdit.setTipoDeCocina(receta.getTipoDeCocina());
        recetaEdit.setPaisDeOrigen(receta.getPaisDeOrigen());
        recetaEdit.setDificultadElaboracion(receta.getDificultadElaboracion());
        recetaEdit.setInstruccionesPreparacion(receta.getInstruccionesPreparacion());
        recetaEdit.setTiempoCoccion(receta.getTiempoCoccion());
        recetaEdit.setIngredientes(ingredientes);
        recetaEdit.setUrlVideo(receta.getUrlVideo());

        // Verificar que la imagen no esté vacía
        if (!imagen.isEmpty()) {
            try {
                // Obtener el directorio en el que se guardará la imagen
                String uploadDir = "src/main/resources/static/images/recetas/";

                // Crear el directorio si no existe
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath); // Crear directorios si no existen
                }

                // Obtener el nombre del archivo original y la ruta completa
                String imagePath = uploadDir + imagen.getOriginalFilename();

                // Copiar el archivo al directorio de destino
                Files.copy(imagen.getInputStream(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);

                // Establecer la ruta de la imagen en el atributo de la receta
                receta.setUrlImagen("/images/recetas/" + imagen.getOriginalFilename()); // Solo la ruta relativa a los
                                                                                        // recursos

            } catch (IOException e) {
                model.addAttribute("error", "Error al subir la imagen: " + e.getMessage());
                return "recetas/vermisrecetas";
            }
        }

        // Se imprimen los valores de receta para ver que se envian sin problemas
        System.out.println(recetaEdit.getNombre());
        System.out.println(recetaEdit.getIngredientes());
        System.out.println(recetaEdit.getInstruccionesPreparacion());
        System.out.println(recetaEdit.getTipoDeCocina());
        System.out.println(recetaEdit.getPaisDeOrigen());
        System.out.println(recetaEdit.getTiempoCoccion());
        System.out.println(recetaEdit.getDificultadElaboracion());

        ResponseEntity<?> response = recetaService.editarReceta(id, recetaEdit, tokenStore.getToken());

        // Verificar si la solicitud fue exitosa
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Receta Editada con éxito");
        } else {
            model.addAttribute("error", "Hubo un problema al editar la receta");
        }

        // Obtener recetas del usuario (Reemplazar ocn id de usuario)
        List<Receta> recetas = recetaService.obtenerRecetasUsuario(tokenStore.getUserModel().getId(), tokenStore.getToken());
        model.addAttribute("id", id);
        model.addAttribute("recetasCreadas", recetas);
        model.addAttribute("token", tokenStore.getToken());
        model.addAttribute("user", tokenStore.getUserModel());

        return "recetas/vermisrecetas";
    }
    

}
