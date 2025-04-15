package com.abdoatiia542.GraduationProject.specification;


import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.model.enumerations.Role;
import io.jsonwebtoken.lang.Assert;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

public abstract class UserSpecification {

    public static Specification<User> hasAnyRole(Collection<Role> roles) {
        Assert.notNull(roles, "'roles' must not be null");
        return (root, query, criteriaBuilder) -> roles.isEmpty() ? criteriaBuilder.conjunction() : root.get("role").in(roles);
    }

    public static Specification<User> notSuperAdmin() {
        List<Role> roles = List.of(Role.TRAINEE);
        return (root, query, criteriaBuilder) -> root.get("role").in(roles);
    }

    public static Specification<User> isLocked(boolean isLocked) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("accountNonLocked"), !isLocked);
    }

    public static Specification<User> isNotifyOfNewAnnouncement(boolean isNotifyOfNewAnnouncements) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("notifyOfNewAnnouncements"), isNotifyOfNewAnnouncements);
    }

    public static Specification<User> isNotifyOfNewMessages(boolean isNotifyOfNewMessages) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("notifyOfNewMessages"), isNotifyOfNewMessages);
    }

    public static Specification<User> nameContains(String searchWord) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            return criteriaBuilder.like(root.get("userDetails").get("name"), "%" + searchWord + "%");
        };
    }

    public static Specification<User> hasRole(Role role) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("role"), role);
    }


}
