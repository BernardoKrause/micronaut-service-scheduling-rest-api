package com.example.authprovider;

import io.micronaut.context.annotation.Value;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.AuthenticationProvider;
import jakarta.inject.Singleton;

@Singleton
public class BasicAuthProvider implements AuthenticationProvider<HttpRequest<?>, String, String> {

    @Value("${AUTH_USER:admin}")
    protected String validUser;

    @Value("${AUTH_SECRET}")
    protected String validPassword;

    public BasicAuthProvider(@Value("${AUTH_USER:admin}") String validUser,
                             @Value("${AUTH_SECRET}") String validPassword) {
        this.validUser = validUser;
        this.validPassword = validPassword;
    }

    @Override
    public @NonNull AuthenticationResponse authenticate(HttpRequest<?> httpRequest,
                                                        AuthenticationRequest<String, String> authenticationRequest) {

        String username = authenticationRequest.getIdentity();
        String password = authenticationRequest.getSecret();

        if (username.equals(validUser) && password.equals(validPassword)) {
            return AuthenticationResponse.success(username);
        }

        return AuthenticationResponse.failure("Acesso negado!");
    }
}