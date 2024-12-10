package com.duoc.seguridad_calidad.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class CrearRecetaTest {
    
    @Test
    public void testCrearReceta(){
        CrearReceta crearReceta = new CrearReceta();

        crearReceta.setNombre("Paella");
        crearReceta.setTipoDeCocina("Española");
        crearReceta.setPaisDeOrigen("España");
        crearReceta.setDificultadElaboracion("Alta");
        crearReceta.setInstruccionesPreparacion("Cocinar el arroz con el caldo y añadir los ingredientes. Cocinar a fuego lento hasta que el arroz esté tierno.");
        crearReceta.setTiempoCoccion(60);
        crearReceta.setUrlImagen("example url img");
        crearReceta.setUrlVideo("example url video");
        crearReceta.setPopularidad(1);
        crearReceta.setIngredientes(Arrays.asList("Tomate", "Cebolla", "Ajo"));
        crearReceta.setUsuarioId(2L);

        assertEquals("Paella", crearReceta.getNombre());
        assertEquals("Española", crearReceta.getTipoDeCocina());
        assertEquals("España", crearReceta.getPaisDeOrigen());
        assertEquals("Alta", crearReceta.getDificultadElaboracion());
        assertEquals("Cocinar el arroz con el caldo y añadir los ingredientes. Cocinar a fuego lento hasta que el arroz esté tierno.", crearReceta.getInstruccionesPreparacion());
        assertEquals(60, crearReceta.getTiempoCoccion());
        assertEquals("example url img", crearReceta.getUrlImagen());
        assertEquals("example url video", crearReceta.getUrlVideo());
        assertEquals(1, crearReceta.getPopularidad());
        assertEquals(Arrays.asList("Tomate", "Cebolla", "Ajo"), crearReceta.getIngredientes());
        assertEquals(2L, crearReceta.getUsuarioId());
    }
}
