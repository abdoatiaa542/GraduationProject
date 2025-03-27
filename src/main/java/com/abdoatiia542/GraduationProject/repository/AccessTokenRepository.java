package com.abdoatiia542.GraduationProject.repository;

import com.abdoatiia542.GraduationProject.model.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {
}
