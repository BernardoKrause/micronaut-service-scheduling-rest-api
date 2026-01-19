package com.example.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.core.annotation.Nullable;
import com.example.entity.Requester;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

@Serdeable
@JsonInclude(JsonInclude.Include.NON_NULL)
@MappedEntity("service")
public class Service {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private Long id;

    private String description;

    private String type;

    private Double value;

    private LocalDate scheduled_for;

    private LocalDate opened_at;

    @Relation(value = Relation.Kind.MANY_TO_ONE)
    private Requester requester;

    public Service() {
    }

    public Service(Long id, String description, String type, Double value, LocalDate scheduled_for, LocalDate opened_at, Requester requester) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.value = value;
        this.scheduled_for = scheduled_for;
        this.opened_at = opened_at;
        this.requester = requester;
    }
    
    public Service(String description, String type, Double value, LocalDate scheduled_for, LocalDate opened_at, Requester requester) {
        this.description = description;
        this.type = type;
        this.value = value;
        this.scheduled_for = scheduled_for;
        this.opened_at = opened_at;
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

    public LocalDate getScheduled_for() {
        return scheduled_for;
    }

    public LocalDate getOpened_at() {
        return opened_at;
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

    public void setScheduled_for(LocalDate scheduled_for) {
        this.scheduled_for = scheduled_for;
    }

    public void setOpened_at(LocalDate opened_at) {
        this.opened_at = opened_at;
    }

    public void setRequester(Requester requester) {
        this.requester = requester;
    }
}
