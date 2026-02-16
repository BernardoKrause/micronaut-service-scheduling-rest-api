package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private LocalDate scheduledFor;

    @NotNull(message = "Opening date is required")
    private LocalDate openedAt;

    @NotNull(message = "Requester is required!")
    private Long requesterId;

    public ServiceDTO() {
    }

    public ServiceDTO(String description, String type, Double value, LocalDate scheduledFor, LocalDate openedAt, Long requesterId) {
        this.description = description;
        this.type = type;
        this.value = value;
        this.scheduledFor = scheduledFor;
        this.openedAt = openedAt;
        this.requesterId = requesterId;
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
        return scheduledFor;
    }

    public LocalDate getOpenedAt() {
        return openedAt;
    }

    public Long getRequesterId() {
        return requesterId;
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

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }
}

