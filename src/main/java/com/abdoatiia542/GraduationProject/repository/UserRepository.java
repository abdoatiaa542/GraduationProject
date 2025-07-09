package com.abdoatiia542.GraduationProject.repository;


import com.abdoatiia542.GraduationProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = """
            SELECT u
            FROM User u
            WHERE LOWER(u.username) LIKE LOWER(:username)
            OR LOWER(u.email) LIKE LOWER(:username)
            """)
    Optional<User> findByUsernameOrEmail(@Param("username") String username);

    @Query(value = """
            SELECT u
            FROM User u
            WHERE LOWER(u.username) LIKE LOWER(:username)
            OR LOWER(u.email) LIKE LOWER(:username)""")
    Optional<User> loadByUsernameOrEmail(@Param("username") String username);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUsernameIgnoreCase(String username);

    Optional<User> findByUsername(String username);


}
