package com.example.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller("/api")
public class StatusController {
    @Get(value = "/status", produces = MediaType.TEXT_PLAIN)
    @Secured(SecurityRule.IS_AUTHENTICATED) // This allows the test to pass once logged in
    public String status() {
        return "OK";
    }
}
