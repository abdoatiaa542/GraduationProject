package com.abdoatiia542.GraduationProject.repository;

import com.abdoatiia542.GraduationProject.model.RegistrationOTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationOTPRepository extends JpaRepository<RegistrationOTP, Long> {

    void deleteAllByEmailIgnoreCase(final String email);

    Optional<RegistrationOTP> findByEmailIgnoreCase(final String email);

    boolean existsByCode(final int code);
}