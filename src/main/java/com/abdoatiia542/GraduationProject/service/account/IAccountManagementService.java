package com.abdoatiia542.GraduationProject.service.account;

import com.abdoatiia542.GraduationProject.dto.TraineeMeasurementsRequest;
import com.abdoatiia542.GraduationProject.dto.account.ProfileRequest;
import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.account.ChangePasswordRequest;
import org.springframework.web.multipart.MultipartFile;

public interface IAccountManagementService {

//    ApiResponse getUserProfile();
//
//    ApiResponse updateUserProfile(UpdateUserProfileRequest request);
//
    ApiResponse changePassword(ChangePasswordRequest request);

    ApiResponse uploadProfilePicture(MultipartFile picture);

    ApiResponse removeProfilePicture();

    ApiResponse getAuthorities();

    ApiResponse getUserProfile() ;

    ApiResponse editeProfile( ProfileRequest request) ;


}
