package com.example.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Serdeable
@JsonInclude(JsonInclude.Include.NON_NULL)
@MappedEntity
public class Service {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private Long id;

    @NotBlank(message = "Description is required!")
    private String description;

    @NotBlank(message = "Type is required!")
    private String type;

    @NotNull(message = "Value is required!")
    private Double value;

    @Nullable
    private Date scheduled_for;

    @Nullable
    private Date opened_at;

    public Service(Long id, String description, String type, Double value, Date scheduled_for, @Nullable Date opened_at) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.value = value;
        this.scheduled_for = scheduled_for;
        this.opened_at = opened_at;
    }

    public Service(Long id, String description, String type, Double value, Date scheduled_for) {
        this(id, description, type, value, scheduled_for, null);
    }

    // Construtor sem ID (para INSERT)
    public Service(String description, String type, Double value, Date scheduled_for, Date opened_at) {
        this(null, description, type, value, scheduled_for, opened_at);
    }
    public Service(String description, String type, Double value, Date scheduled_for) {
        this(null, description, type, value, scheduled_for);
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

    public Date getScheduled_for() {
        return scheduled_for;
    }

    public Date getOpened_at() {
        return opened_at;
    }
}
