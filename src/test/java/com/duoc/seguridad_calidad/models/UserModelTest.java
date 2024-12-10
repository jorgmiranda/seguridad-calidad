package com.duoc.seguridad_calidad.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class UserModelTest {
    @Test
    public void userModelTest() {
        UserModel userModel = new UserModel();

        userModel.setId(1L);
        userModel.setEmail("john.doe@example.com");
        userModel.setPassword("securepassword");
        userModel.setNombre("John Doe");

        assertEquals(1L, userModel.getId());
        assertEquals("john.doe@example.com", userModel.getEmail());
        assertEquals("securepassword", userModel.getPassword());
        assertEquals("John Doe", userModel.getNombre());
    }

    @Test
    public void testConstructorWithParameters() {
        // Arrange
        long expectedId = 1L;
        String expectedCorreo = "test@domain.com";
        String expectedContrasena = "securePassword";
        String expectedNombre = "Test User";

        // Act
        UserModel userModel = new UserModel(expectedId, expectedCorreo, expectedContrasena, expectedNombre);

        // Assert
        assertNotNull(userModel); // Verificar que el objeto no sea null
        assertEquals(expectedId, userModel.getId()); // Verificar el id
        assertEquals(expectedCorreo, userModel.getEmail()); // Verificar el correo
        assertEquals(expectedContrasena, userModel.getPassword()); // Verificar la contraseña
        assertEquals(expectedNombre, userModel.getNombre()); // Verificar el nombre
    }
}
