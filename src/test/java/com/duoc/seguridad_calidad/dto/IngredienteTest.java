package com.duoc.seguridad_calidad.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class IngredienteTest {
    @Test
    public void testIngrediente(){
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setId(1L);
        ingrediente.setNombreIngrediente("Tomate");

        assertEquals(1L, ingrediente.getId());
        assertEquals("Tomate", ingrediente.getNombreIngrediente());

    }
}
