package com.example.entity;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public class Requester {

    private UUID id;
    private String email;

}
