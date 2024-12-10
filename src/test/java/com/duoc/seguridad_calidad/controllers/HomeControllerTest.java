package com.duoc.seguridad_calidad.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import com.duoc.seguridad_calidad.dto.Receta;
import com.duoc.seguridad_calidad.dto.RecetaParcial;
import com.duoc.seguridad_calidad.security.TokenStore;
import com.duoc.seguridad_calidad.services.RecetaService;

public class HomeControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TokenStore tokenStore;

    @Mock
    private RecetaService recetaService;

    private HomeController homeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar el controlador
        homeController = new HomeController(tokenStore, recetaService);

        // Configurar MockMvc con un ViewResolver
        mockMvc = MockMvcBuilders
                .standaloneSetup(homeController)
                .setViewResolvers((viewName, locale) -> {
                    InternalResourceView view = new InternalResourceView("/WEB-INF/views/" + viewName + ".jsp");
                    return view;
                })
                .build();
    }

    @Test
    void testHome() throws Exception {
        // Arrange
        RecetaParcial receta1 = new RecetaParcial(1, "Pizza", "Italiana", "Media", "30 minutos", "url1");
        receta1.setPopularidad(100);
        receta1.setFechaCreacion(new Date());

        RecetaParcial receta2 = new RecetaParcial(2, "Tarta", "Postres", "Alta", "60 minutos", "url2");
        receta2.setPopularidad(50);
        receta2.setFechaCreacion(new Date());

        when(recetaService.obtenerRecetas()).thenReturn(Arrays.asList(receta1, receta2));
        when(tokenStore.getToken()).thenReturn("mockToken");
        when(tokenStore.getUserModel()).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/home").param("name", "Test Name"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("recetasPorPopularidad"))
                .andExpect(model().attributeExists("recetasPorFecha"))
                .andExpect(model().attribute("name", "Test Name"));

        verify(recetaService, times(1)).obtenerRecetas();
        verify(tokenStore, times(2)).getToken();
    }

    @Test
    void testRoot() throws Exception {
        // Arrange
        RecetaParcial receta1 = new RecetaParcial(1, "Pizza", "Italiana", "Media", "30 minutos", "url1");
        receta1.setPopularidad(100);
        receta1.setFechaCreacion(new Date());

        RecetaParcial receta2 = new RecetaParcial(2, "Tarta", "Postres", "Alta", "60 minutos", "url2");
        receta2.setPopularidad(50);
        receta2.setFechaCreacion(new Date());

        when(recetaService.obtenerRecetas()).thenReturn(Arrays.asList(receta1, receta2));
        when(tokenStore.getToken()).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/").param("name", "Test Name"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("recetasPorPopularidad"))
                .andExpect(model().attributeExists("recetasPorFecha"))
                .andExpect(model().attribute("name", "Test Name"));

        verify(recetaService, times(1)).obtenerRecetas();
        verify(tokenStore, times(1)).getToken();
    }

    @Test
    void testFiltrar() throws Exception {
        // Arrange
        Receta recetaFiltrada = new Receta(1L, "Pizza Filtrada", "Italiana", "Italia", "Media",
                "Instrucciones detalladas", "https://video.url", 30, "https://imagen.url", new Date(), 100, null);

        when(recetaService.filrarRecetas(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Arrays.asList(recetaFiltrada));
        when(tokenStore.getToken()).thenReturn("mockToken");

        // Act & Assert
        mockMvc.perform(post("/filtrar")
                        .param("name", "Test Name")
                        .param("nombre", "Pizza")
                        .param("tipoCocina", "Italiana")
                        .param("ingredientes", "Tomate")
                        .param("paisOrigen", "Italia")
                        .param("dificultad", "Media"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("recetasPorFiltro"))
                .andExpect(model().attributeExists("recetasPorPopularidad"))
                .andExpect(model().attributeExists("recetasPorFecha"))
                .andExpect(model().attribute("name", "Test Name"));

        verify(recetaService, times(1)).filrarRecetas(anyString(), anyString(), anyString(), anyString(), anyString());
        verify(tokenStore, times(2)).getToken();
    }
}
