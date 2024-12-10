package com.duoc.seguridad_calidad.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.RestTemplate;

import com.duoc.seguridad_calidad.models.UserModel;
import com.google.gson.Gson;

@SpringBootTest
class CustomAuthenticationProviderTest {

    @MockBean
    private TokenStore tokenStore;

    @MockBean
    private RestTemplate restTemplate;

    private String mockToken;

    private CustomAuthenticationProvider authenticationProvider;

    @BeforeEach
    void setUp() {
        mockToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VXNlciIsImV4cCI6MTU5MjU4MjU2MCwiaXNzIjoiS2V5d29yZCIsImF1ZCI6IkFwcGxpY2F0aW9uIn0.8_6y-F_1592507800504";
    
        authenticationProvider = new CustomAuthenticationProvider(tokenStore, restTemplate);
    
        when(tokenStore.getToken()).thenReturn(mockToken);
    
        // Mock User
        UserModel mockUser = new UserModel();
        mockUser.setId(1);
        mockUser.setEmail("admin@test.com");
        mockUser.setPassword("securePassword");
        mockUser.setNombre("Admin");
    
        // Crear un JSONObject para representar la estructura de respuesta esperada
        JSONObject mockData = new JSONObject();
        mockData.put("token", mockToken);
        mockData.put("user", new JSONObject(new Gson().toJson(mockUser))); // Convertir UserModel a JSONObject
    
        JSONObject mockResponse = new JSONObject();
        mockResponse.put("messageResponse", "Success");
        mockResponse.put("data", mockData); // Aquí "data" es un objeto JSON
        mockResponse.put("error", JSONObject.NULL);
    
        // Configurar RestTemplate para devolver esta estructura
        when(restTemplate.postForEntity(
                contains("/api/login?correo=admin@test.com&password=securePassword"),
                any(),
                eq(String.class)))
            .thenReturn(new ResponseEntity<>(mockResponse.toString(), HttpStatus.OK));
    }

    @Test
    void testAuthenticate_ValidCredentials() {
        // Arrange
        Authentication authentication = new UsernamePasswordAuthenticationToken("admin@test.com", "securePassword");

        // Act
        Authentication result = authenticationProvider.authenticate(authentication);

        // Assert
        assertNotNull(result);
        assertEquals("admin@test.com", result.getName());
        assertTrue(result.isAuthenticated());

        verify(tokenStore, times(1)).setToken(mockToken);
        verify(tokenStore, times(1)).setUserModel(any(UserModel.class));
    }

    @Test
    public void testSupports() {
        // Act & Assert
        assertTrue(authenticationProvider.supports(UsernamePasswordAuthenticationToken.class));
        assertFalse(authenticationProvider.supports(Authentication.class));
    }

    
}
