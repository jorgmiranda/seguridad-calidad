package com.duoc.seguridad_calidad.config;

import com.duoc.seguridad_calidad.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()).disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/._darcs", "/.bzr", "/.hg", "/BitKeeper").denyAll()
                        //Quitar 
                        .requestMatchers(HttpMethod.GET, "/crearreceta", "/vermisrecetas/**", "/editarmireceta/**","/verreceta/**", "/detallereceta").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/publicar","/receta/comentar/**" , "/editar/**").hasRole("USER")
                        //Cierre
                        .requestMatchers("/", "/home", "/login", "/testing", "/ingresar", "/listadousuario", "/crearusuario").permitAll()
                        .requestMatchers(HttpMethod.POST, "/filtrar").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll())
                .logout((logout) -> logout.permitAll())
                // .headers(headers -> headers
                //         .contentSecurityPolicy(csp -> csp
                //                 .policyDirectives("default-src 'self'; " +
                //                                 "script-src 'self'  https://cdnjs.cloudflare.com https://unpkg.com; " +
                //                                 "img-src 'self' https://i.blogs.es https://www.gourmet.cl https://comedera.com https://www.nestleprofessional-latam.com https://assets.tmecosys.com; " +
                //                                 "style-src 'self'  https://cdnjs.cloudflare.com; " +
                //                                 "font-src 'self'; " +
                //                                 "connect-src 'self'; " +
                //                                 "frame-ancestors 'none';" +
                //                                 "form-action 'self' http://localhost:8080  http://localhost:8081;"))


                // )
                ;

                
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
