package com.example.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Serdeable
@JsonInclude(JsonInclude.Include.NON_NULL)
@MappedEntity("requester")
public class Requester {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Name is Required!")
    private String name;

    @NotNull(message = "Email is Required!")
    private String email;

    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "requester")
    private Set<Service> services;


    public Requester() {
    }

    public Requester(Long id, String name, String email, Set<Service> services) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.services = services;
    }

    public Requester(String name, String email, Set<Service> services) {
        this.name = name;
        this.email = email;
        this.services = services;
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

    public Set<Service> getServices() {
        return services;
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

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    public void addService(Service service) {
        this.services.add(service);
    }
}
