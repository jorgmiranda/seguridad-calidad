package com.duoc.seguridad_calidad.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.duoc.seguridad_calidad.dto.CrearReceta;
import com.duoc.seguridad_calidad.dto.Ingrediente;
import com.duoc.seguridad_calidad.dto.Receta;

@Controller
public class RecetaController {

    @GetMapping("/crearreceta")
    public String home(
            @RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name,
            Model model) {

        List<Ingrediente> ingredientes = obtenIngredientes();

        model.addAttribute("ingredientesback", ingredientes);
        model.addAttribute("name", name);

        return "crearreceta";
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
                e.printStackTrace();
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

        ResponseEntity<?> response = publicarReceta(receta);

        // Verificar si la solicitud fue exitosa
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Receta publicada con éxito");
        } else {
            model.addAttribute("error", "Hubo un problema al publicar la receta");
        }

        List<Ingrediente> ingredientesselect = obtenIngredientes();
        model.addAttribute("ingredientesback", ingredientesselect);

        return "crearreceta";
    }

    @GetMapping("/vermisrecetas/{id}")
    public String vermisrecetas(
            @RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name,
            @PathVariable Long id, Model model) {

        List<Receta> recetas = obtenerRecetasUsuario(id);

        model.addAttribute("recetasCreadas", recetas);
        model.addAttribute("name", name);

        return "recetas/vermisrecetas";
    }

    @GetMapping("/editarmireceta/{id}")
    public String editarmireceta(
            @RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name,
            @PathVariable Long id, Model model) {

        Receta receta = obtenerRecetasByID(id);
        List<Ingrediente> ingredientes = obtenIngredientes();

        List<String> ingredientesSeleccionados = receta.getRecetaIngredientes().stream()
                .map(Ingrediente::getNombreIngrediente) // Extrae los nombres
                .collect(Collectors.toList());

        model.addAttribute("ingredientesback", ingredientes);
        model.addAttribute("ingredientesSeleccionados", ingredientesSeleccionados);
        model.addAttribute("receta", receta);
        model.addAttribute("name", name);

        return "recetas/editarmireceta";
    }

    @GetMapping("/verreceta/{id}")
    public String verReceta(
            @RequestParam(name = "name", required = false, defaultValue = "Seguridad y calidad en el desarrollo") String name,
            @PathVariable Long id,
            Model model) {
        Receta receta = obtenerRecetasByID(id);

        model.addAttribute("receta", receta);
        model.addAttribute("name", name);

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
                e.printStackTrace();
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

        ResponseEntity<?> response = editarReceta(id, recetaEdit);

        // Verificar si la solicitud fue exitosa
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Receta Editada con éxito");
        } else {
            model.addAttribute("error", "Hubo un problema al editar la receta");
        }

        // Obtener recetas del usuario (Reemplazar ocn id de usuario)
        List<Receta> recetas = obtenerRecetasUsuario((long) 1);
        model.addAttribute("id", id);
        model.addAttribute("recetasCreadas", recetas);

        return "recetas/vermisrecetas";
    }

    // Consumo de serivicios
    // Metodo que consume el servicio de creación de receta
    private ResponseEntity<?> publicarReceta(CrearReceta crearReceta) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CrearReceta> request = new HttpEntity<>(crearReceta, headers);

        ResponseEntity<?> response = restTemplate.exchange(
                "http://localhost:8081/recetas", HttpMethod.POST, request, Object.class);

        return response;
    }

    // Metodo que consume el servicio de edición de receta
    private ResponseEntity<?> editarReceta(Long id, CrearReceta crearReceta) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CrearReceta> request = new HttpEntity<>(crearReceta, headers);

        ResponseEntity<?> response = restTemplate.exchange(
                "http://localhost:8081/recetas/" + id, HttpMethod.POST, request, Object.class);

        return response;
    }

    private List<Ingrediente> obtenIngredientes() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/ingredientes";
        Ingrediente[] ingredientes = restTemplate.getForObject(url, Ingrediente[].class);

        return Arrays.asList(ingredientes);
    }

    private List<Receta> obtenerRecetasUsuario(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/recetas/usuario/" + id;

        Receta[] recetas = restTemplate.getForObject(url, Receta[].class);

        return Arrays.asList(recetas);
    }

    private Receta obtenerRecetasByID(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/recetas/" + id;

        // Realizar la solicitud GET y mapear la respuesta a la clase Receta
        try {
            ResponseEntity<Receta> response = restTemplate.getForEntity(url, Receta.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Error al obtener la receta, código de estado: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException.NotFound e) {

            System.err.println("Receta no encontrada para ID: " + id);
            return null;
        } catch (Exception e) {

            System.err.println("Error al realizar la solicitud: " + e.getMessage());
            return null;
        }
    }

}