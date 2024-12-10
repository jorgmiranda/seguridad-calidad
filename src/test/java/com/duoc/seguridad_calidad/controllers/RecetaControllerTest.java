package com.duoc.seguridad_calidad.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import com.duoc.seguridad_calidad.dto.CrearReceta;
import com.duoc.seguridad_calidad.dto.Ingrediente;
import com.duoc.seguridad_calidad.dto.Receta;
import com.duoc.seguridad_calidad.models.UserModel;
import com.duoc.seguridad_calidad.security.TokenStore;
import com.duoc.seguridad_calidad.services.RecetaService;

class RecetaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TokenStore tokenStore;

    @Mock
    private RecetaService recetaService;

    private RecetaController recetaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recetaController = new RecetaController(tokenStore, recetaService);

        // Configuración de MockMvc con un ViewResolver simulado
        mockMvc = MockMvcBuilders
                .standaloneSetup(recetaController)
                .setViewResolvers((viewName, locale) -> {
                    InternalResourceView view = new InternalResourceView("/WEB-INF/views/" + viewName + ".jsp");
                    return view;
                })
                .build();
    }

    @Test
    void testHome() throws Exception {
        // Arrange
        Ingrediente ingrediente1 = new Ingrediente(1L, "Tomate");
        Ingrediente ingrediente2 = new Ingrediente(2L, "Queso");

        when(recetaService.obtenIngredientes(anyString())).thenReturn(Arrays.asList(ingrediente1, ingrediente2));
        when(tokenStore.getToken()).thenReturn("mockToken");

        // Act & Assert
        mockMvc.perform(get("/crearreceta").param("name", "Test Name"))
                .andExpect(status().isOk())
                .andExpect(view().name("crearreceta"))
                .andExpect(model().attributeExists("ingredientesback"))
                .andExpect(model().attribute("name", "Test Name"));

        verify(recetaService, times(1)).obtenIngredientes(anyString());
    }

  
    @Test
    void testPublicarReceta() throws Exception {
        // Arrange
        MockMultipartFile imagen = new MockMultipartFile(
                "imagen", "imagen.jpg", "image/jpeg", "imagen mock".getBytes());

        // Mock de UserModel
        UserModel mockUserModel = new UserModel();
        mockUserModel.setId(1L);
        mockUserModel.setNombre("Test User");
        mockUserModel.setEmail("testuser@example.com");

        // Configurar mocks
        when(tokenStore.getToken()).thenReturn("mockToken");
        when(tokenStore.getUserModel()).thenReturn(mockUserModel);
        when(recetaService.publicarReceta(any(CrearReceta.class), anyString())).thenReturn(ResponseEntity.ok().build());
        when(recetaService.obtenIngredientes(anyString())).thenReturn(Arrays.asList(new Ingrediente(1L, "Tomate")));

        // Act & Assert
        mockMvc.perform(multipart("/publicar")
                        .file(imagen)
                        .param("nombre", "Pizza")
                        .param("tipoCocina", "Italiana")
                        .param("paisOrigen", "Italia")
                        .param("dificultad", "Media")
                        .param("instruccionesPreparacion", "Instrucciones")
                        .param("tiempoCoccion", "30")
                        .param("ingredientes", "Tomate", "Queso", "Albahaca"))
                .andExpect(status().isOk())
                .andExpect(view().name("crearreceta"))
                .andExpect(model().attributeExists("message"));

        // Verificar invocaciones
        verify(recetaService, times(1)).publicarReceta(any(CrearReceta.class), anyString());
        verify(tokenStore, times(2)).getUserModel();
    }

    @Test
    void testVerMisRecetas() throws Exception {
        // Arrange
        Receta receta1 = new Receta(1L, "Pizza", "Italiana", "Italia", "Media",
                "Instrucciones", "https://video.url", 30, "https://imagen.url", new Date(), 100, null);

        when(recetaService.obtenerRecetasUsuario(anyLong(), anyString())).thenReturn(Arrays.asList(receta1));
        when(tokenStore.getToken()).thenReturn("mockToken");

        // Act & Assert
        mockMvc.perform(get("/vermisrecetas/1").param("name", "Test Name"))
                .andExpect(status().isOk())
                .andExpect(view().name("recetas/vermisrecetas"))
                .andExpect(model().attributeExists("recetasCreadas"))
                .andExpect(model().attribute("name", "Test Name"));

        verify(recetaService, times(1)).obtenerRecetasUsuario(anyLong(), anyString());
    }

    @Test
    void testVerReceta() throws Exception {
        // Arrange
        Receta receta = new Receta(1L, "Pizza", "Italiana", "Italia", "Media",
                "Instrucciones", "https://video.url", 30, "https://imagen.url", new Date(), 100, null);

        when(recetaService.obtenerRecetasByID(anyLong(), anyString())).thenReturn(receta);
        when(tokenStore.getToken()).thenReturn("mockToken");

        // Act & Assert
        mockMvc.perform(get("/verreceta/1").param("name", "Test Name"))
                .andExpect(status().isOk())
                .andExpect(view().name("recetas/verreceta"))
                .andExpect(model().attributeExists("receta"))
                .andExpect(model().attribute("name", "Test Name"));

        verify(recetaService, times(1)).obtenerRecetasByID(anyLong(), anyString());
    }

    @Test
    void testEditarReceta() throws Exception {
        // Arrange
        MockMultipartFile imagen = new MockMultipartFile(
                "imagen", "imagen.jpg", "image/jpeg", "imagen mock".getBytes());

        // Mock de UserModel
        UserModel mockUserModel = new UserModel();
        mockUserModel.setId(1L);
        mockUserModel.setNombre("Test User");
        mockUserModel.setEmail("testuser@example.com");

        // Mock de RecetaService
        Receta receta = new Receta(1L, "Pizza", "Italiana", "Italia", "Media",
                "Instrucciones", "https://video.url", 30, "https://imagen.url", new Date(), 100, null);
        when(recetaService.editarReceta(anyLong(), any(CrearReceta.class), anyString()))
                .thenReturn(ResponseEntity.ok().build());
        when(recetaService.obtenerRecetasUsuario(anyLong(), anyString()))
                .thenReturn(Arrays.asList(receta));

        // Configurar tokenStore para devolver el mock de UserModel
        when(tokenStore.getToken()).thenReturn("mockToken");
        when(tokenStore.getUserModel()).thenReturn(mockUserModel);

        // Act & Assert
        mockMvc.perform(multipart("/editar/1")
                        .file(imagen)
                        .param("ingredientes", "Tomate", "Queso", "Albahaca"))
                .andExpect(status().isOk())
                .andExpect(view().name("recetas/vermisrecetas"))
                .andExpect(model().attributeExists("message"));

        // Verificaciones
        verify(recetaService, times(1)).editarReceta(anyLong(), any(CrearReceta.class), anyString());
        verify(tokenStore, times(2)).getUserModel();
    }

    @Test
    void testEditarmireceta() throws Exception {
        // Arrange
        Long recetaId = 1L;
        String url = "/editarmireceta/" + recetaId;

        // Mock de `Receta`
        Receta receta = new Receta();
        receta.setId(recetaId);
        receta.setNombre("Pizza");
        receta.setTipoDeCocina("Italiana");
        receta.setPaisDeOrigen("Italia");
        receta.setDificultadElaboracion("Media");
        receta.setInstruccionesPreparacion("Instrucciones detalladas");
        receta.setTiempoCoccion(30);
        receta.setRecetaIngredientes(Arrays.asList(
                new Ingrediente(1L, "Tomate"),
                new Ingrediente(2L, "Queso")
        ));

        // Mock de `Ingrediente`
        List<Ingrediente> ingredientes = Arrays.asList(
                new Ingrediente(1L, "Tomate"),
                new Ingrediente(2L, "Queso"),
                new Ingrediente(3L, "Albahaca")
        );

        // Mock de `UserModel`
        UserModel mockUserModel = new UserModel();
        mockUserModel.setId(1L);
        mockUserModel.setNombre("Test User");
        mockUserModel.setEmail("testuser@example.com");

        // Configurar los mocks
        when(recetaService.obtenerRecetasByID(anyLong(), anyString())).thenReturn(receta);
        when(recetaService.obtenIngredientes(anyString())).thenReturn(ingredientes);
        when(tokenStore.getToken()).thenReturn("mockToken");
        when(tokenStore.getUserModel()).thenReturn(mockUserModel);

        // Act & Assert
        mockMvc.perform(get(url).param("name", "Test Name"))
                .andExpect(status().isOk())
                .andExpect(view().name("recetas/editarmireceta"))
                .andExpect(model().attributeExists("ingredientesback"))
                .andExpect(model().attributeExists("ingredientesSeleccionados"))
                .andExpect(model().attributeExists("receta"))
                .andExpect(model().attribute("name", "Test Name"));

        // Verificar invocaciones
        verify(recetaService, times(1)).obtenerRecetasByID(eq(recetaId), anyString());
        verify(recetaService, times(1)).obtenIngredientes(anyString());
        verify(tokenStore, times(3)).getToken();
        verify(tokenStore, times(1)).getUserModel();
    }

}
