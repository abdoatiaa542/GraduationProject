package com.abdoatiia542.GraduationProject.service.account;


import com.abdoatiia542.GraduationProject.cloudnairy.CloudinaryService;
import com.abdoatiia542.GraduationProject.dto.UserDetailsResponse;
import com.abdoatiia542.GraduationProject.dto.account.ChangePasswordRequest;
import com.abdoatiia542.GraduationProject.dto.account.ImageUpdateRequest;
import com.abdoatiia542.GraduationProject.dto.account.ProfileRequest;
import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.abdoatiia542.GraduationProject.utils.context.ContextHolderUtils;
import com.abdoatiia542.GraduationProject.utils.file.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountManagementService implements IAccountManagementService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TraineeRepository traineeRepository;
    private final CloudinaryService cloudinaryService;
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


    public ApiResponse getUserProfile() {

        User user = ContextHolderUtils.getTrainee();

        String firstName = null;
        String lastName = null;
        Boolean isMeasurementsSet = null;
        if (user instanceof Trainee trainee) {
            firstName = trainee.getFirstName();
            lastName = trainee.getLastName();
            isMeasurementsSet = trainee.isMeasurementsSet();
        }

        UserDetailsResponse response = new UserDetailsResponse(
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                firstName,
                lastName,
                user.getGender() != null ? user.getGender().name() : null,
                user.getBirthYear() ,
                user.getImage(),
                isMeasurementsSet
        );



        return ApiResponse.of("User profile retrieved successfully.", response);
    }

    @Override
    public ApiResponse editeProfile(ProfileRequest request) {
        Trainee trainee = ContextHolderUtils.getTrainee();
        trainee.setLastName(request.lastName());
        trainee.setFirstName(request.firstName());
        trainee.setEmail(request.email());
        trainee.setBirthYear(request.birthYear());
        traineeRepository.save(trainee);
        return ApiResponse.of("User profile updated successfully.");
    }


@Override
    public Object updateUserImage (ImageUpdateRequest request) {
        Trainee trainee = (Trainee) ContextHolderUtils.getUser();

        if (request.image() != null && !request.image().isEmpty()) {
            try {
                Map uploadResult = cloudinaryService.upload(request.image());
                String imageUrl = (String) uploadResult.get("secure_url");
                trainee.setImage(imageUrl);
            } catch (IOException e) {
                return ApiResponse.of("Image upload failed.");
            }
        } else {
            return ApiResponse.of("Image is required.");
        }

        traineeRepository.save(trainee);

        return ApiResponse.success("Profile image updated successfully.",Map.of("imageUrl", trainee.getImage()));
    }

}

