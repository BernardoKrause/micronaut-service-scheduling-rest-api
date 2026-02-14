package com.example.authprovider;

import io.micronaut.context.annotation.Value;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider;
import jakarta.inject.Singleton;

@Singleton
public class MyAuthenticationProvider implements HttpRequestAuthenticationProvider<Object> {

    @Value("${auth.username}")
    protected String user;

    @Value("${auth.password}")
    protected String secret;

    @Override
    public AuthenticationResponse authenticate(@Nullable HttpRequest<Object> httpRequest,
                                               AuthenticationRequest<String, String> authenticationRequest) {

        // Safety check to prevent null pointers if injection fails
        if (user == null || secret == null) {
            return AuthenticationResponse.failure("Configuration error");
        }

        if (authenticationRequest.getIdentity().equals(user) &&
                authenticationRequest.getSecret().equals(secret)) {
            return AuthenticationResponse.success(authenticationRequest.getIdentity());
        }

        return AuthenticationResponse.failure();
    }
}