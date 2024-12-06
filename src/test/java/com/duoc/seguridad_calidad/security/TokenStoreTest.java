package com.duoc.seguridad_calidad.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.duoc.seguridad_calidad.models.UserModel;

public class TokenStoreTest {
    @Test
    public void tokenStoreTest() {
        // Preparar objeto
        TokenStore tokenStore = new TokenStore();

        // Configurar propiedades
        String testToken = "test-token-123";
        UserModel testUser = new UserModel();
        testUser.setId(1L);
        testUser.setEmail("user@example.com");
        testUser.setPassword("securepassword");
        testUser.setNombre("Test User");

        tokenStore.setToken(testToken);
        tokenStore.setUserModel(testUser);

        // Verificar valores configurados
        assertEquals(testToken, tokenStore.getToken());
        assertEquals(testUser, tokenStore.getUserModel());

        // Verificar valores del UserModel interno
        UserModel userModelFromToken = tokenStore.getUserModel();
        assertEquals(1L, userModelFromToken.getId());
        assertEquals("user@example.com", userModelFromToken.getEmail());
        assertEquals("securepassword", userModelFromToken.getPassword());
        assertEquals("Test User", userModelFromToken.getNombre());
    }
}
