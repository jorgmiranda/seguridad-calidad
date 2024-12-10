package com.duoc.seguridad_calidad.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    public void testConstructorWithParameters() {
        // Arrange
        int expectedId = 1;
        String expectedNombre = "Tarta de Manzana";
        String expectedTipoDeCocina = "Postres";
        String expectedDificultadElaboracion = "Media";
        String expectedTiempoCoccion = "45 minutos";
        String expectedUrlImagen = "https://imagen.url/tarta_manzana.jpg";

        // Act
        RecetaParcial recetaParcial = new RecetaParcial(
            expectedId, 
            expectedNombre, 
            expectedTipoDeCocina, 
            expectedDificultadElaboracion, 
            expectedTiempoCoccion, 
            expectedUrlImagen
        );

        // Assert
        assertNotNull(recetaParcial); // Verificar que el objeto no sea null
        assertEquals(expectedId, recetaParcial.getId()); // Verificar el ID
        assertEquals(expectedNombre, recetaParcial.getNombre()); // Verificar el nombre
        assertEquals(expectedTipoDeCocina, recetaParcial.getTipoDeCocina()); // Verificar el tipo de cocina
        assertEquals(expectedDificultadElaboracion, recetaParcial.getDificultadElaboracion()); // Verificar la dificultad
        assertEquals(expectedTiempoCoccion, recetaParcial.getTiempoCoccion()); // Verificar el tiempo de cocción
        assertEquals(expectedUrlImagen, recetaParcial.getUrlImagen()); // Verificar la URL de la imagen
    }
}
