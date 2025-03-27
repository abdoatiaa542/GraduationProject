package com.abdoatiia542.GraduationProject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public record UpdateUserProfileRequest(

        @NotBlank(message = "Username is required") String username,

        @NotBlank(message = "Email is required") String email,

//        @NotBlank(message = "National ID is required")
        String nid,

//        @NotBlank(message = "About is required")
        String about,

//        @NotNull(message = "Gender id is required")
//        Gender gender,

//        @NotNull(message = "Nationality id is required") Nationality nationality,

//        @NotNull(message = "Religion id is required")
//        Religion religion,

//        @NotNull(message = "Birth date id is required")
        LocalDate birthDate,

//        @NotNull(message = "Region id is required")
        Integer regionId,

        @NotBlank(message = "English name is required") String name_en,

        @NotBlank(message = "Arabic name is required") String name_ar,

        @NotBlank(message = "French name is required") String name_fr

) implements Serializable {

//    public static UpdateUserProfileRequest of(User user) {
//        UserDetails userDetails_en = LanguageUtils.getDetails(user.getUserDetails(), Language.ENGLISH);
//        UserDetails userDetails_ar = LanguageUtils.getDetails(user.getUserDetails(), Language.ARABIC);
//        UserDetails userDetails_fr = LanguageUtils.getDetails(user.getUserDetails(), Language.FRENCH);
//        return new UpdateUserProfileRequest(
//                user.getUsername(), user.getEmail(), user.getNid(), user.getAbout(), user.getGender(), user.getNationality(), user.getReligion(), user.getBirthDate(), Objects.nonNull(user.getRegion()) ? user.getRegion().getId() : null, userDetails_en.getName(), userDetails_ar.getName(), userDetails_fr.getName());
//    }

}
