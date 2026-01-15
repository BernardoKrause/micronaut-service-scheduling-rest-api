package com.example;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public class Requester {

    private UUID id;
    private String email;

}
