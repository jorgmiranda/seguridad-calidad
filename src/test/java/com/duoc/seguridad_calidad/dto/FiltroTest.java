package com.duoc.seguridad_calidad.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class FiltroTest {
    @Test
    public void testFiltro(){
        Filtro filtro = new Filtro();
        filtro.setNombre("Tacos");
        filtro.setPaisOrigen("España");
        filtro.setDificultad("Media");
        filtro.setTipoCocina("Española");
        filtro.setIngredientes("Tomate");

        assertEquals("Tacos", filtro.getNombre());
        assertEquals("España", filtro.getPaisOrigen());
        assertEquals("Media", filtro.getDificultad());
        assertEquals("Española", filtro.getTipoCocina());
        assertEquals("Tomate", filtro.getIngredientes());

    }

    @Test
    public void testConstructorWithParameters() {
        // Arrange
        String expectedNombre = "Pizza Margherita";
        String expectedPaisOrigen = "Italia";
        String expectedDificultad = "Fácil";
        String expectedTipoCocina = "Italiana";
        String expectedIngredientes = "Tomate, Queso, Albahaca";

        // Act
        Filtro filtro = new Filtro(expectedNombre, expectedPaisOrigen, expectedDificultad, expectedTipoCocina, expectedIngredientes);

        // Assert
        assertNotNull(filtro); // Verificar que el objeto no sea null
        assertEquals(expectedNombre, filtro.getNombre()); // Verificar el nombre
        assertEquals(expectedPaisOrigen, filtro.getPaisOrigen()); // Verificar el país de origen
        assertEquals(expectedDificultad, filtro.getDificultad()); // Verificar la dificultad
        assertEquals(expectedTipoCocina, filtro.getTipoCocina()); // Verificar el tipo de cocina
        assertEquals(expectedIngredientes, filtro.getIngredientes()); // Verificar los ingredientes
    }
}
