    package com.abdoatiia542.GraduationProject.utils.context;


    import com.abdoatiia542.GraduationProject.model.Trainee;
    import com.abdoatiia542.GraduationProject.model.User;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;

    import java.util.Objects;

    public class ContextHolderUtils {

        public static Authentication getAuthentication() {
            return SecurityContextHolder.getContext().getAuthentication();
        }

        public static Object getPrincipal() {
            Objects.requireNonNull(getAuthentication());
            return getAuthentication().getPrincipal();
        }

        public static User getUser() {
            Objects.requireNonNull(getPrincipal());
            if (getPrincipal() instanceof User user) {
                return user;
            }
            throw new IllegalStateException();
        }

        // get trainee
        public static Trainee getTrainee() {
            if (getUser() instanceof Trainee trainee) {
                return trainee;
            }
            throw new IllegalArgumentException("User is not trainee");
        }


    }