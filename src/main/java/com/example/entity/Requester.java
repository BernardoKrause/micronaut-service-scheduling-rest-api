package com.example.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Serdeable
@JsonInclude(JsonInclude.Include.NON_NULL)
@MappedEntity
public class Requester {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private Long id;

    @NotBlank(message = "Name is Required!")
    private String name;

    @NotNull(message = "Email is Required!")
    private String email;


    public Requester() {
    }

    public Requester(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Requester(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
