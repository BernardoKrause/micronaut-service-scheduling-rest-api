package com.example.repository;

import com.example.entity.Service;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;


@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ServiceRepository extends PageableRepository<Service, Long> {
    // Exemplo de criação de métodos customizado
    // @Query("SELECT * FROM service WHERE description = :description")
    // Service findByDescription(@NonNull @NotNull String description);
}
