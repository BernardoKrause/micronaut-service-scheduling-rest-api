package com.example;

import io.micronaut.serde.annotation.Serdeable;

import java.util.Date;
import java.util.UUID;

@Serdeable.Serializable
public record Service(UUID id, String type, String description, Double value, Date opened_at) {
}
