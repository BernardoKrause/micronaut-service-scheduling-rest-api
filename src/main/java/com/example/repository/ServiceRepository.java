package com.example.repository;

import com.example.entity.Service;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ServiceRepository extends PageableRepository<Service, Long> { 
    // Exemplo de criação de método customizado
    // @Query("SELECT * FROM service WHERE description = :description")
    // Service findByDescription(@NonNull @NotNull String description);
}
