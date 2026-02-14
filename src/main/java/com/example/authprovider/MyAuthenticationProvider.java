package com.example.authprovider;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider;
import jakarta.inject.Singleton;

@Singleton
public class MyAuthenticationProvider implements HttpRequestAuthenticationProvider<Object> {

    @Override
    public AuthenticationResponse authenticate(@Nullable HttpRequest<Object> httpRequest,
                                               AuthenticationRequest<String, String> authenticationRequest) {

        if (authenticationRequest.getIdentity().equals("usuario") &&
                authenticationRequest.getSecret().equals("senha123")) {

            return AuthenticationResponse.success(authenticationRequest.getIdentity());
        }

        return AuthenticationResponse.failure();
    }
}