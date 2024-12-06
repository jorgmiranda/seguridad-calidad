package com.duoc.seguridad_calidad.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.duoc.seguridad_calidad.dto.CrearReceta;
import com.duoc.seguridad_calidad.dto.Filtro;
import com.duoc.seguridad_calidad.dto.Ingrediente;
import com.duoc.seguridad_calidad.dto.Receta;
import com.duoc.seguridad_calidad.dto.RecetaParcial;

@Service
public class RecetaService {

    private final RestTemplate restTemplate;
    private final String URL = "http://localhost:8081/";

    public RecetaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RecetaParcial> obtenerRecetas() {
        String url = URL+"recetas/parcial";

        RecetaParcial[] recetas = restTemplate.getForObject(url, RecetaParcial[].class);

        return Arrays.asList(recetas);
    }

    public List<Receta> obtenerRecetasFull(String token){
        String url = URL+"recetas/full";

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

    public List<Receta> filrarRecetas(String nombre, String pais, String dificultad, String tipo, String ingrediente) {
        String url = URL+"recetas/filtrar";

        // Valores
        Filtro filtro = new Filtro(nombre, pais, dificultad, tipo, ingrediente);

        Receta[] recetas = restTemplate.postForObject(url, filtro, Receta[].class);

        return Arrays.asList(recetas);
    }

    public Receta obtenerRecetasByID(Long id, String token) {
        
        String url = URL+"recetas/" + id;

        // Crear los encabezados y agregar el token de autorización
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        // Crear el HttpEntity con los encabezados (sin cuerpo, ya que es una solicitud GET)
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Realizar la solicitud GET con los encabezados
        try {
            ResponseEntity<Receta> response = restTemplate.exchange(url, HttpMethod.GET, entity, Receta.class);

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

    public ResponseEntity<?> publicarReceta(CrearReceta crearReceta, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<CrearReceta> request = new HttpEntity<>(crearReceta, headers);

        ResponseEntity<?> response = restTemplate.exchange(
                URL+"recetas", HttpMethod.POST, request, Object.class);

        return response;
    }

    // Metodo que consume el servicio de edición de receta
    public ResponseEntity<?> editarReceta(Long id, CrearReceta crearReceta, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);


        HttpEntity<CrearReceta> request = new HttpEntity<>(crearReceta, headers);

        ResponseEntity<?> response = restTemplate.exchange(
                URL+"recetas/" + id, HttpMethod.POST, request, Object.class);

        return response;
    }

    public List<Receta> obtenerRecetasUsuario(Long id, String token) {
        String url = URL+"recetas/usuario/" + id;

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


    public List<Ingrediente> obtenIngredientes(String token) {
        String url = URL+"ingredientes";

         // Crear los encabezados y agregar el token
         HttpHeaders headers = new HttpHeaders();
         headers.set("Authorization", token);
 
         // Crear el HttpEntity con los encabezados
         HttpEntity<String> entity = new HttpEntity<>(headers);
 
         // Hacer la solicitud GET con los encabezados
         ResponseEntity<Ingrediente[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Ingrediente[].class);


        Ingrediente[] ingredientes = response.getBody();;

        return Arrays.asList(ingredientes);
    }

}


