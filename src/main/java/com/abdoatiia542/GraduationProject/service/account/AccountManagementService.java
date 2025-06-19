package com.abdoatiia542.GraduationProject.service.account;


import com.abdoatiia542.GraduationProject.dto.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.account.ChangePasswordRequest;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.abdoatiia542.GraduationProject.utils.ContextHolderUtils;
import com.abdoatiia542.GraduationProject.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountManagementService implements IAccountManagementService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

//    @Override
//    public ApiResponse getUserProfile() {
//        User user = ContextHolderUtils.getUser();
//        var response = userProfileResponseMapper.apply(user);
//        return ApiResponse.of("User profile retrieved successfully.", response);
//    }
//
//    @Override
//    public ApiResponse updateUserProfile(UpdateUserProfileRequest request) {
//        User user = ContextHolderUtils.getUser();
//        updateUserProfileRequestUpdater.accept(user, request);
//        return ApiResponse.of("User profile updated successfully.");
//    }

    @Override
    public ApiResponse changePassword(ChangePasswordRequest request) {
        User user = ContextHolderUtils.getUser();

        if (passwordEncoder.matches(request.password(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.newPassword()));
            userRepository.save(user);

            return ApiResponse.success("Password changed successfully.");
        }

        throw new IllegalArgumentException("Invalid password");
    }


    @Override
    public ApiResponse uploadProfilePicture(MultipartFile picture) {
        User user = ContextHolderUtils.getUser();

        if (!FileUtils.isSupportedImage(picture)) {
            throw new IllegalArgumentException("Unsupported image format");
        }

        if (Objects.nonNull(user.getPicture())) {
//            userPictureDeleter.accept(user.getPicture().getId());
        }

//        userPictureSaver.accept(picture, user);

        return ApiResponse.success("User profile picture uploaded successfully.");

    }

    @Override
    public ApiResponse removeProfilePicture() {
        User user = ContextHolderUtils.getUser();

        if (Objects.isNull(user.getPicture())) {
            throw new IllegalArgumentException("Not found profile picture");
        }

//        userPictureDeleter.accept(user.getPicture().getId());

        return ApiResponse.success("User profile picture deleted successfully.");
    }


    @Transactional
    @Override
    public ApiResponse getAuthorities() {
        User user = ContextHolderUtils.getUser();
        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return ApiResponse.success("Authorities retrieved successfully.", authorities);
    }
}