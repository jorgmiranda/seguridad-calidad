package com.duoc.seguridad_calidad.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
