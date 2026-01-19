package com.example;

import com.example.authprovider.BasicAuthProvider;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
@Property(name = "AUTH_USER", value = "test-user")
@Property(name = "AUTH_SECRET", value = "test-pass")
class BasicAuthProviderTest {

    @Inject
    BasicAuthProvider authProvider;

    @Test
    void testAutenticacaoComSucesso() {
        AuthenticationRequest<String, String> authRequest = new AuthenticationRequest<>() {
            @Override public String getIdentity() { return "test-user"; }
            @Override public String getSecret() { return "test-pass"; }
        };

        AuthenticationResponse response = authProvider.authenticate(null, authRequest);

        Assertions.assertTrue(response.isAuthenticated());
    }

    @Test
    void testAutenticacaoFalha() {

        AuthenticationRequest<String, String> authRequest = new AuthenticationRequest<>() {
            @Override public String getIdentity() { return "errado"; }
            @Override public String getSecret() { return "senha"; }
        };

        AuthenticationResponse response = authProvider.authenticate(null, authRequest);

        Assertions.assertFalse(response.isAuthenticated());
    }
}