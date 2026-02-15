package com.example.dto.filter;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.annotation.QueryValue;

import java.time.LocalDate;

public class ServiceFiltersDTO implements Pageable {
    @Nullable
    @QueryValue
    private String description;

    @Nullable
    @QueryValue
    private String type;

    @Nullable
    @QueryValue
    private Double value;

    @Nullable
    @QueryValue
    private LocalDate scheduledFor;

    @Nullable
    @QueryValue
    private LocalDate openedAt;

    @Nullable
    @QueryValue
    private Integer size;

    @Nullable
    @QueryValue
    private Integer page;

    public ServiceFiltersDTO() {
    }

    public ServiceFiltersDTO(@Nullable String description, @Nullable String type, @Nullable Double value, @Nullable LocalDate scheduledFor, @Nullable LocalDate openedAt, @Nullable Integer size, @Nullable Integer page) {
        this.description = description;
        this.type = type;
        this.value = value;
        this.scheduledFor = scheduledFor;
        this.openedAt = openedAt;
        this.size = size;
        this.page = page;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setScheduledFor(LocalDate scheduledFor) {
        this.scheduledFor = scheduledFor;
    }

    public void setOpenedAt(LocalDate openedAt) {
        this.openedAt = openedAt;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
    @Override
    public int getNumber() {
        return page != null ? page : 0;
    }

    @Override
    public int getSize() {
        return size != null ? size : 10;
    }
}
