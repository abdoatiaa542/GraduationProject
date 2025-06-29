package com.abdoatiia542.GraduationProject.model;

import com.abdoatiia542.GraduationProject.model.enumerations.Gender;
import com.abdoatiia542.GraduationProject.model.enumerations.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "USERS", uniqueConstraints =
        {@UniqueConstraint(name = "USER_USERNAME_UNIQUE_CONSTRAINT", columnNames = "username"),
                @UniqueConstraint(name = "USER_EMAIL_UNIQUE_CONSTRAINT", columnNames = "email")})
public class User implements org.springframework.security.core.userdetails.UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;


    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    private Gender gender;

    @Column(nullable = true)
    private LocalDate birthDate;


    @CreationTimestamp
    @Column(nullable = true, updatable = true)
    private LocalDateTime createdAt;


    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @Builder.Default
    @Column(nullable = true)
    private boolean accountNonLocked = true;


    @Builder.Default
    @Column(nullable = false)
    private boolean enabled = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "device_tokens",
            joinColumns = @JoinColumn(name = "user_id", nullable = false)
    )
    @Column(name = "device_token", nullable = false)
    private Set<String> deviceTokens = new HashSet<>();


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserPicture picture;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}