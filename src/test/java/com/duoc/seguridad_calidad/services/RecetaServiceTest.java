package com.duoc.seguridad_calidad.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.duoc.seguridad_calidad.dto.CrearReceta;
import com.duoc.seguridad_calidad.dto.Filtro;
import com.duoc.seguridad_calidad.dto.Ingrediente;
import com.duoc.seguridad_calidad.dto.Receta;
import com.duoc.seguridad_calidad.dto.RecetaParcial;

public class RecetaServiceTest {
    private RecetaService recetaService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recetaService = new RecetaService(restTemplate);
    }

    @Test
    void testObtenerRecetas() {
        // Arrange
        String mockUrl = "http://localhost:8081/recetas/parcial";

        // Crear datos simulados
        RecetaParcial receta1 = new RecetaParcial(1, "Tarta de Manzana", "Postres", "Fácil", "45 minutos", "https://imagen.url/tarta.jpg");
        RecetaParcial receta2 = new RecetaParcial(2, "Pizza Margherita", "Italiana", "Media", "30 minutos", "https://imagen.url/pizza.jpg");
        RecetaParcial[] mockResponse = { receta1, receta2 };

        // Configurar el mock de RestTemplate
        when(restTemplate.getForObject(mockUrl, RecetaParcial[].class)).thenReturn(mockResponse);

        // Act
        List<RecetaParcial> result = recetaService.obtenerRecetas();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Tarta de Manzana", result.get(0).getNombre());
        assertEquals("Pizza Margherita", result.get(1).getNombre());

        // Verificar que se realizó la llamada al mock de RestTemplate
        verify(restTemplate, times(1)).getForObject(mockUrl, RecetaParcial[].class);
    }

    @Test
    void testObtenerRecetasFull() {
        // Arrange
        String mockUrl = "http://localhost:8081/recetas/full";
        String mockToken = "Bearer mockJwtToken";

        // Crear encabezados simulados
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", mockToken);

        // Crear HttpEntity con los encabezados
        HttpEntity<String> mockEntity = new HttpEntity<>(headers);

        // Crear datos simulados de respuesta
        Receta receta1 = new Receta(1L, "Tarta de Manzana", "Postres", "Italia", "Fácil", 
                "Instrucciones detalladas", "https://video.url/tarta", 45, 
                "https://imagen.url/tarta.jpg", new Date(), 100, Arrays.asList(new Ingrediente(1L, "Manzana")));
        
        Receta receta2 = new Receta(2L, "Pizza Margherita", "Italiana", "Italia", "Media", 
                "Instrucciones detalladas", "https://video.url/pizza", 30, 
                "https://imagen.url/pizza.jpg", new Date(), 150, Arrays.asList(new Ingrediente(2L, "Queso"), new Ingrediente(3L, "Tomate")));

        Receta[] mockResponse = { receta1, receta2 };

        // Configurar el mock de RestTemplate
        when(restTemplate.exchange(mockUrl, HttpMethod.GET, mockEntity, Receta[].class))
            .thenReturn(ResponseEntity.ok(mockResponse));

        // Act
        List<Receta> result = recetaService.obtenerRecetasFull(mockToken);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Tarta de Manzana", result.get(0).getNombre());
        assertEquals("Pizza Margherita", result.get(1).getNombre());

        // Verificar que se realizó la llamada con los argumentos correctos
        verify(restTemplate, times(1)).exchange(mockUrl, HttpMethod.GET, mockEntity, Receta[].class);
    }

    @Test
    void testFilrarRecetas() {
        // Arrange
        String url = "http://localhost:8081/recetas/filtrar";

        // Crear el objeto Filtro esperado
        Filtro filtro = new Filtro("Pizza", "Italia", "Media", "Italiana", "Queso");

        // Crear una receta simulada como respuesta
        Receta receta = new Receta(
            1L, "Pizza Margherita", "Italiana", "Italia", "Media",
            "Instrucciones detalladas", "https://video.url", 30,
            "https://imagen.url", new Date(), 10, null
        );

        // Configurar el mock de RestTemplate con depuración
        when(restTemplate.postForObject(eq(url), any(Filtro.class), eq(Receta[].class)))
            .thenAnswer(invocation -> {
                // Imprimir la URL y el objeto Filtro recibidos
                System.out.println("URL utilizada en la solicitud: " + invocation.getArgument(0));
                System.out.println("Filtro enviado: " + invocation.getArgument(1));
                return new Receta[] { receta }; // Retornar la respuesta simulada
            });

        // Act
        List<Receta> result = recetaService.filrarRecetas("Pizza", "Italia", "Media", "Italiana", "Queso");

        // Assert
        assertNotNull(result, "La lista de recetas no debe ser nula");
        assertEquals(1, result.size(), "El tamaño de la lista debe ser 1");
        assertEquals("Pizza Margherita", result.get(0).getNombre(), "El nombre de la receta debe coincidir");

        // Verificar que el método del mock fue llamado exactamente una vez
        verify(restTemplate, times(1)).postForObject(eq(url), any(Filtro.class), eq(Receta[].class));
    }


    @Test
    void testObtenerRecetasByID() {
        String url = "http://localhost:8081/recetas/1";
        String token = "Bearer mockToken";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        Receta receta = new Receta(1L, "Tarta de Manzana", "Postres", "Italia", "Fácil", "Instrucciones", "https://video.url", 45, "https://imagen.url", new Date(), 10, null);

        when(restTemplate.exchange(url, HttpMethod.GET, entity, Receta.class)).thenReturn(ResponseEntity.ok(receta));

        Receta result = recetaService.obtenerRecetasByID(1L, token);

        assertNotNull(result);
        assertEquals("Tarta de Manzana", result.getNombre());
        verify(restTemplate, times(1)).exchange(url, HttpMethod.GET, entity, Receta.class);
    }

    @Test
    void testPublicarReceta() {
        // Arrange
        String url = "http://localhost:8081/recetas";
        String token = "Bearer mockToken";
        
        // Crear el objeto CrearReceta con los atributos completos
        CrearReceta crearReceta = new CrearReceta();
        crearReceta.setNombre("Pizza");
        crearReceta.setTipoDeCocina("Italiana");
        crearReceta.setPaisDeOrigen("Italia");
        crearReceta.setDificultadElaboracion("Media");
        crearReceta.setInstruccionesPreparacion("Instrucciones detalladas");
        crearReceta.setTiempoCoccion(30);
        crearReceta.setUrlImagen("https://imagen.url/pizza.jpg");
        crearReceta.setUrlVideo("https://video.url/pizza.mp4");
        crearReceta.setPopularidad(100);
        crearReceta.setIngredientes(Arrays.asList("Tomate", "Queso", "Albahaca"));
        crearReceta.setUsuarioId(1L);

        // Configurar los encabezados simulados
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<CrearReceta> request = new HttpEntity<>(crearReceta, headers);

        // Configurar el mock de RestTemplate
        when(restTemplate.exchange(url, HttpMethod.POST, request, Object.class))
            .thenReturn(ResponseEntity.ok().build());

        // Act
        ResponseEntity<?> response = recetaService.publicarReceta(crearReceta, token);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(restTemplate, times(1)).exchange(url, HttpMethod.POST, request, Object.class);
    }

    @Test
    void testEditarReceta() {
        // Arrange
        Long id = 1L;
        String url = "http://localhost:8081/recetas/" + id;
        String token = "Bearer mockToken";

        CrearReceta crearReceta = new CrearReceta();
        crearReceta.setNombre("Pizza Actualizada");
        crearReceta.setTipoDeCocina("Italiana");
        crearReceta.setPaisDeOrigen("Italia");
        crearReceta.setDificultadElaboracion("Fácil");
        crearReceta.setInstruccionesPreparacion("Instrucciones nuevas");
        crearReceta.setTiempoCoccion(20);
        crearReceta.setUrlImagen("https://imagen.url/pizza.jpg");
        crearReceta.setUrlVideo("https://video.url/pizza.mp4");
        crearReceta.setPopularidad(150);
        crearReceta.setIngredientes(Arrays.asList("Tomate", "Mozzarella", "Albahaca"));
        crearReceta.setUsuarioId(2L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<CrearReceta> request = new HttpEntity<>(crearReceta, headers);

        when(restTemplate.exchange(eq(url), eq(HttpMethod.POST), eq(request), eq(Object.class)))
            .thenReturn(ResponseEntity.ok().build());

        // Act
        ResponseEntity<?> response = recetaService.editarReceta(id, crearReceta, token);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(restTemplate, times(1)).exchange(eq(url), eq(HttpMethod.POST), eq(request), eq(Object.class));
    }

    @Test
    void testObtenerRecetasUsuario() {
        // Arrange
        Long userId = 1L;
        String url = "http://localhost:8081/recetas/usuario/" + userId;
        String token = "Bearer mockToken";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        Receta receta1 = new Receta(1L, "Pizza Margherita", "Italiana", "Italia", "Media",
            "Instrucciones detalladas", "https://video.url/pizza", 30,
            "https://imagen.url/pizza.jpg", new Date(), 100, null);

        Receta receta2 = new Receta(2L, "Tarta de Manzana", "Postres", "Italia", "Fácil",
            "Instrucciones detalladas", "https://video.url/tarta", 45,
            "https://imagen.url/tarta.jpg", new Date(), 150, null);

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), eq(entity), eq(Receta[].class)))
            .thenReturn(ResponseEntity.ok(new Receta[]{receta1, receta2}));

        // Act
        List<Receta> result = recetaService.obtenerRecetasUsuario(userId, token);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pizza Margherita", result.get(0).getNombre());
        verify(restTemplate, times(1)).exchange(eq(url), eq(HttpMethod.GET), eq(entity), eq(Receta[].class));
    }

    @Test
    void testObtenIngredientes() {
        // Arrange
        String url = "http://localhost:8081/ingredientes";
        String token = "Bearer mockToken";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        Ingrediente ingrediente1 = new Ingrediente(1L, "Tomate");
        Ingrediente ingrediente2 = new Ingrediente(2L, "Mozzarella");

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), eq(entity), eq(Ingrediente[].class)))
            .thenReturn(ResponseEntity.ok(new Ingrediente[]{ingrediente1, ingrediente2}));

        // Act
        List<Ingrediente> result = recetaService.obtenIngredientes(token);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Tomate", result.get(0).getNombreIngrediente());
        verify(restTemplate, times(1)).exchange(eq(url), eq(HttpMethod.GET), eq(entity), eq(Ingrediente[].class));
    }

}
