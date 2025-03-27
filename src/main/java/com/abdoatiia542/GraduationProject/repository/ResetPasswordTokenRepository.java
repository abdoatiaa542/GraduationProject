package com.abdoatiia542.GraduationProject.repository;

import com.abdoatiia542.GraduationProject.model.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResetPasswordTokenRepository
        extends JpaRepository<ResetPasswordToken, UUID> {

    void deleteAllByEmailIgnoreCase(final String email);

    Optional<ResetPasswordToken> findByEmailIgnoreCase(final String email);

    boolean existsByCode(final int code);

}
