package com.example.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.data.annotation.*;
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

    @NotBlank(message = "Full name is Required!")
    private String fullName;

    @NotNull(message = "Email is Required!")
    private String email;

    @NotNull(message = "Department is Required!")
    private String department;

    @NotNull(message = "User name is Required!")
    private String userName;

    @NotNull(message = "Phone number is Required!")
    private String phoneNumber;

    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "requester")
    private Set<Service> services;


    public Requester() {
    }

    public Requester(Long id, String name, String department, String userName, String phoneNumber, String email, Set<Service> services) {
        this.id = id;
        this.fullName = name;
        this.email = email;
        this.department = department;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.services = services;
    }

    public Requester(String name, String email, String department, String userName, String phoneNumber, Set<Service> services) {
        this.fullName = name;
        this.email = email;
        this.department = department;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.services = services;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    public void addService(Service service) {
        this.services.add(service);
    }
}
