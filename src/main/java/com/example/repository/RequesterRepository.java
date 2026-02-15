package com.example.repository;

import com.example.entity.Requester;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;
import io.micronaut.core.annotation.Nullable;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface RequesterRepository extends PageableRepository<Requester, Long> {

    @Query(value = """
        SELECT * FROM requester 
        WHERE (:id IS NULL OR id = :id)
        AND (:fullName IS NULL OR full_name LIKE CONCAT('%', :fullName, '%'))
        AND (:email IS NULL OR email LIKE CONCAT('%', :email, '%'))
        AND (:department IS NULL OR department LIKE CONCAT('%', :department, '%'))
        AND (:userName IS NULL OR user_name LIKE CONCAT('%', :userName, '%'))
        AND (:phoneNumber IS NULL OR phone_number LIKE CONCAT('%', :phoneNumber, '%'))
        """,
        countQuery = """
        SELECT COUNT(*) FROM requester 
        WHERE (:id IS NULL OR id = :id)
        AND (:fullName IS NULL OR full_name LIKE CONCAT('%', :fullName, '%'))
        AND (:email IS NULL OR email LIKE CONCAT('%', :email, '%'))
        AND (:department IS NULL OR department LIKE CONCAT('%', :department, '%'))
        AND (:userName IS NULL OR user_name LIKE CONCAT('%', :userName, '%'))
        AND (:phoneNumber IS NULL OR phone_number LIKE CONCAT('%', :phoneNumber, '%'))
        """)
    Page<Requester> findWithFilters(
        @Nullable Long id,
        @Nullable String fullName,
        @Nullable String email,
        @Nullable String department,
        @Nullable String userName,
        @Nullable String phoneNumber,
        Pageable pageable
    );
}
