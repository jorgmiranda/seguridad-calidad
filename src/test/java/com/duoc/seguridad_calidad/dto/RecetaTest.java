package com.duoc.seguridad_calidad.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class RecetaTest {
    @Test
    public void recetaTest() {
       
        Receta receta = new Receta();
        Date fechaCreacion = new Date();
        
        receta.setId(1L);
        receta.setNombre("Paella");
        receta.setTipoDeCocina("Española");
        receta.setPaisDeOrigen("España");
        receta.setDificultadElaboracion("Alta");
        receta.setInstruccionesPreparacion("Cocinar el arroz con el caldo y añadir los ingredientes.");
        receta.setUrlVideo("example_video_url");
        receta.setTiempoCoccion(60);
        receta.setUrlImagen("example_image_url");
        receta.setFechaCreacion(fechaCreacion);
        receta.setPopularidad(5);
        receta.setRecetaIngredientes(Arrays.asList(
            new Ingrediente(1L,"Tomate"),
            new Ingrediente(2L,"Cebolla"),
            new Ingrediente(3L,"Ajo")
        ));

        assertEquals(1L, receta.getId());
        assertEquals("Paella", receta.getNombre());
        assertEquals("Española", receta.getTipoDeCocina());
        assertEquals("España", receta.getPaisDeOrigen());
        assertEquals("Alta", receta.getDificultadElaboracion());
        assertEquals("Cocinar el arroz con el caldo y añadir los ingredientes.", receta.getInstruccionesPreparacion());
        assertEquals("example_video_url", receta.getUrlVideo());
        assertEquals(60, receta.getTiempoCoccion());
        assertEquals("example_image_url", receta.getUrlImagen());
        assertEquals(fechaCreacion, receta.getFechaCreacion());
        assertEquals(5, receta.getPopularidad());
        assertEquals(3, receta.getRecetaIngredientes().size());
        assertEquals("Tomate", receta.getRecetaIngredientes().get(0).getNombreIngrediente());
        assertEquals("Cebolla", receta.getRecetaIngredientes().get(1).getNombreIngrediente());
        assertEquals("Ajo", receta.getRecetaIngredientes().get(2).getNombreIngrediente());
    }
}
