package com.duoc.seguridad_calidad.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class RecetaParcialTest {
    @Test
    public void recetaParcialTest(){
        RecetaParcial parcial = new RecetaParcial();
        Date fechaCreacion = new Date();
        parcial.setId(1);
        parcial.setNombre("Paella");
        parcial.setTipoDeCocina("Española");
        parcial.setDificultadElaboracion("Alta");
        parcial.setTiempoCoccion("60");
        parcial.setUrlImagen("example");
        parcial.setPopularidad(5);
        parcial.setFechaCreacion(fechaCreacion);

        assertEquals(1, parcial.getId());
        assertEquals("Paella", parcial.getNombre());
        assertEquals("Española", parcial.getTipoDeCocina());
        assertEquals("Alta", parcial.getDificultadElaboracion());
        assertEquals("60", parcial.getTiempoCoccion());
        assertEquals("example", parcial.getUrlImagen());
        assertEquals(5, parcial.getPopularidad());
        assertEquals(fechaCreacion, parcial.getFechaCreacion());
    }
}
