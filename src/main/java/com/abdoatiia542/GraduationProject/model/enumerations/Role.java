package com.abdoatiia542.GraduationProject.model.enumerations;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    SUPER_ADMIN, TRAINEE;


    //    private static final Set<Role> ROLES = EnumSet.of(USER, ADMIN); // <1>
    @Override
    public String getAuthority() {
        return name();
    }
}
