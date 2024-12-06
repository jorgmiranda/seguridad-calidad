package com.duoc.seguridad_calidad.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.catalina.Context;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;

public class TomcatConfigurationTest {
    
    @Test
    void testCustomize() {
        // Crea un mock del Context
        Context mockContext = mock(Context.class);

        // Crea una instancia del TomcatContextCustomizer
        TomcatContextCustomizer customizer = new TomcatConfiguration().sameSiteCookiesConfig();

        // Llama al método customize con el mockContext
        customizer.customize(mockContext);

        // Verifica que el método setCookieProcessor fue llamado en el mockContext
        verify(mockContext).setCookieProcessor(any(Rfc6265CookieProcessor.class));
    }
}
