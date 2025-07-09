package com.abdoatiia542.GraduationProject.mapper;

import com.abdoatiia542.GraduationProject.dto.UserDetailsResponse;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.User;

public class UserDetailsResponseMapper {


    public static UserDetailsResponse toUserDetailResponse(User user) {

        String firstName = null;
        String lastName = null;
        Boolean isMeasurementsSet = null;
        if(user instanceof Trainee){
            firstName = ((Trainee) user).getFirstName();
            lastName = ((Trainee) user).getLastName();
            isMeasurementsSet = ((Trainee) user).isMeasurementsSet();
        }
        return UserDetailsResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .firstName(firstName)
                .lastName(lastName)
                .gender(user.getGender().name())
                .birthYear(user.getBirthYear())
                .image(user.getImage())
                .isMeasurementsSet(isMeasurementsSet)
                .build();
    }


}
