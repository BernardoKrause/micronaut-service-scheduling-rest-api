package com.example.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.data.annotation.*;
import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDate;

@Serdeable
@JsonInclude(JsonInclude.Include.NON_NULL)
@MappedEntity("service")
public class Service {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private String type;

    private Double value;

    private LocalDate scheduledFor;

    private LocalDate openedAt;

    @Relation(value = Relation.Kind.MANY_TO_ONE)
    private Requester requester;

    public Service() {
    }

    public Service(Long id, String description, String type, Double value, LocalDate scheduledFor, LocalDate openedAt, Requester requester) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.value = value;
        this.scheduledFor = scheduledFor;
        this.openedAt = openedAt;
        this.requester = requester;
    }
    
    public Service(String description, String type, Double value, LocalDate scheduledFor, LocalDate openedAt, Requester requester) {
        this.description = description;
        this.type = type;
        this.value = value;
        this.scheduledFor = scheduledFor;
        this.openedAt = openedAt;
        this.requester = requester;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Double getValue() {
        return value;
    }

    public LocalDate getScheduledFor() {
        return scheduledFor;
    }

    public LocalDate getOpenedAt() {
        return openedAt;
    }

    public Requester getRequester() {
        return requester;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setScheduledFor(LocalDate scheduled_for) {
        this.scheduledFor = scheduled_for;
    }

    public void setOpenedAt(LocalDate opened_at) {
        this.openedAt = opened_at;
    }

    public void setRequester(Requester requester) {
        this.requester = requester;
    }
}
