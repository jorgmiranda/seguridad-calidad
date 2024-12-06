package com.duoc.seguridad_calidad.controllers;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import com.duoc.seguridad_calidad.dto.Ingrediente;
import com.duoc.seguridad_calidad.dto.Receta;
import com.duoc.seguridad_calidad.models.UserModel;
import com.duoc.seguridad_calidad.security.TokenStore;
import com.duoc.seguridad_calidad.services.RecetaService;

@WebMvcTest(DetalleRecetaController.class)
public class DetalleRecetaControllerTest {
   
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenStore tokenStore;

    @MockBean
    private RecetaService recetaService;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private AuthenticationManager authenticationManager;

    private static final String BASE_URL = "http://localhost:8080/";

    private static final String MOCK_TOKEN = 
    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VyIiwidXNlcl9pZCI6MSwiaXNzIjoiYXBpLmV4YW1wbGUuY29tIiwiZXhwIjoxNjgwNzgwODAwfQ.BXrK0uJ6N8pz3KT2nT4uZDWKq3U";


    @BeforeEach
    public void setUp() {
        // Simular un token JWT válido
        when(tokenStore.getToken()).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VXNlciIsImV4cCI6MTU5MjU4MjU2MCwiaXNzIjoiS2V5d29yZCIsImF1ZCI6IkFwcGxpY2F0aW9uIn0.8_6y-F_1592507800504");

        UserModel mockUser = this.createMockUser();

        when(restTemplate.exchange(
                ArgumentMatchers.contains(BASE_URL+"detallereceta"), // Specify expected URL
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(HttpEntity.class),
                ArgumentMatchers.<Class<String>>any())).thenReturn(new ResponseEntity<>("Hello Test", HttpStatus.OK));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", MOCK_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        when(tokenStore.getToken()).thenReturn(MOCK_TOKEN);
        when(tokenStore.getUserModel()).thenReturn(mockUser);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

    }

    @Test
    public void testDetalleRecetaConAutenticacion() throws Exception {
        // Simular respuesta del servicio RecetaService3
        String username = "radahn@gmail.com";
        String password = "123456";

        // Create a mock UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken token = Mockito.mock(UsernamePasswordAuthenticationToken.class);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // Mock the authentication process
        Mockito.when(authenticationManager.authenticate(token)).thenReturn(
                new UsernamePasswordAuthenticationToken(username, "hashedPassword", authorities));

        SecurityContextHolder.getContext().setAuthentication(token);

        System.out.println("Llamada a recetas full");

        List<Receta> recetasMock = this.createMockRecetas();

        when(recetaService.obtenerRecetasFull(MOCK_TOKEN)).thenReturn(recetasMock);
        
        // Realizar la solicitud GET a /detallereceta
        mockMvc.perform(get("/detallereceta")
                        .header("Authorization", MOCK_TOKEN)
                        .param("name", "Seguridad y calidad en el desarrollo"))
                .andExpect(status().isOk()) // Validar que el estado HTTP es 200
                .andExpect(view().name("detallereceta")) // Validar que se renderiza la vista correcta
                .andExpect(model().attributeExists("recetas")) // Validar que se pasa el atributo "recetas"
                .andExpect(model().attribute("recetas", recetasMock))
                .andExpect(model().attributeExists("name")) // Validar que se pasa el atributo "name"
                .andExpect(model().attribute("name", "Seguridad y calidad en el desarrollo")) // Validar el valor de "name"
                .andExpect(model().attributeExists("token")) // Validar que se pasa el token
                .andExpect(model().attributeExists("user")) // Validar que se pasa el usuario
                ;
    }

    @Test
    public void testDetalleRecetaSinAuth() throws Exception {
        // Sobrescribe el comportamiento del token en esta prueba
        when(tokenStore.getToken()).thenReturn("InvalidToken");

        // Realizar la solicitud y verificar que el código de estado sea 401
        mockMvc.perform(get("/detallereceta")
                        .header("Authorization", "InvalidToken"))
                .andExpect(status().isUnauthorized()); // Verifica que el estado sea 401
    }


    private UserModel createMockUser() {
        UserModel user = new UserModel();
        user.setId(1);
        user.setNombre("radahn");
        user.setEmail("radahn@gmail.com");
        user.setPassword("123456");
        return user;
    }

    private List<Receta> createMockRecetas() {
        return Arrays.asList(
            new Receta(
                1L, 
                "Receta 1", 
                "Italiana", 
                "Italia", 
                "Media", 
                "Preparar salsa, hervir pasta, mezclar", 
                "https://video.url/receta1", 
                30, 
                "https://imagen.url/receta1", 
                new Date(), 
                5, 
                Arrays.asList(new Ingrediente(1L,"Tomate"), new Ingrediente(2L,"Pasta"))
            ),
            new Receta(
                2L, 
                "Receta 2", 
                "Mexicana", 
                "México", 
                "Fácil", 
                "Preparar tortilla, añadir relleno", 
                "https://video.url/receta2", 
                20, 
                "https://imagen.url/receta2", 
                new Date(), 
                4, 
                Arrays.asList(new Ingrediente(3L,"Tortilla"), new Ingrediente(4L,"Carne"))
            )
        );
    }
    
}
