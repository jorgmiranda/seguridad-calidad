package com.duoc.seguridad_calidad.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
