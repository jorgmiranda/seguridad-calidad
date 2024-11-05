package com.duoc.seguridad_calidad.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private TokenStore tokenStore;

    public CustomAuthenticationProvider(TokenStore tokenStore) {
        super();
        this.tokenStore = tokenStore;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();


        final MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

//        requestBody.add("user", name);
//        requestBody.add("encryptedPass", password);

        System.out.println("Request Body: " + requestBody);

        final var restTemplate = new RestTemplate();
        final var responseEntity = restTemplate.postForEntity("http://localhost:8080/login", requestBody, String.class);

        tokenStore.setToken(responseEntity.getBody());

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new BadCredentialsException("Invalid username or password");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(name, password, authorities);

    }

    @Override
    public boolean supports(Class authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

