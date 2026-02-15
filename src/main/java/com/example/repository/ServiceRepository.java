package com.example.repository;

import com.example.entity.Service;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

import java.time.LocalDate;


@JdbcRepository(dialect = Dialect.MYSQL)
public interface ServiceRepository extends PageableRepository<Service, Long> {
    // Exemplo de criação de métodos customizado
    // @Query("SELECT * FROM service WHERE description = :description")
    // Service findByDescription(@NonNull @NotNull String description);

    @Query(value = """
        SELECT * FROM service 
        WHERE (:description IS NULL OR description LIKE CONCAT('%', :description, '%'))
        AND (:type IS NULL OR type LIKE CONCAT('%', :type, '%'))
        AND (:value IS NULL OR value <= :value)
        AND (:scheduledFor IS NULL OR scheduled_for = :scheduledFor)
        AND (:openedAt IS NULL OR opened_at = :openedAt)
        """,
        countQuery = """
        SELECT COUNT(*) FROM service 
        WHERE (:description IS NULL OR description LIKE CONCAT('%', :description, '%'))
        AND (:type IS NULL OR type LIKE CONCAT('%', :type, '%'))
        AND (:value IS NULL OR value <= :value)
        AND (:scheduledFor IS NULL OR scheduled_for = :scheduledFor)
        AND (:openedAt IS NULL OR opened_at = :openedAt)

    """)
    Page<Service> FindWithFilters(
        @Nullable String description,
        @Nullable String type,
        @Nullable Double value,
        @Nullable LocalDate scheduledFor,
        @Nullable LocalDate openedAt,
        Pageable pageable
    );
}
