package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Serdeable
@Introspected
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceDTO {

    @NotBlank(message = "Description is required!")
    private String description;

    @NotBlank(message = "Type is required!")
    private String type;

    @NotNull(message = "Value is required!")
    private Double value;

    @NotNull(message = "Date of Schedule is required!")
    private LocalDate scheduled_for;

    @NotNull(message = "Opening date is required")
    private LocalDate opened_at;

    @NotNull(message = "Requester is required!")
    @JsonProperty("requester_id")
    private Long requester_id;

    public ServiceDTO() {
    }

    public ServiceDTO(String description, String type, Double value, LocalDate scheduled_for, LocalDate opened_at, Long requester_id) {
        this.description = description;
        this.type = type;
        this.value = value;
        this.scheduled_for = scheduled_for;
        this.opened_at = opened_at;
        this.requester_id = requester_id;
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

    public Long getRequester_id() {
        return requester_id;
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

    public void setRequester_id(Long requester_id) {
        this.requester_id = requester_id;
    }
}

