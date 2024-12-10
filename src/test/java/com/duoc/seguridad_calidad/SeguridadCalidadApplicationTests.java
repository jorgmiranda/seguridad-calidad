package com.duoc.seguridad_calidad;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
class SeguridadCalidadApplicationTest {

    @Test
    void testMainMethod() {
        // Simula los argumentos de línea de comando
        String[] args = new String[] {};

        // Ejecuta el método main
        SeguridadCalidadApplication.main(args);

    }
}