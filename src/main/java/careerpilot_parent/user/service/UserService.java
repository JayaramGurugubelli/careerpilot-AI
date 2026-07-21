package careerpilot_parent.user.service;

import careerpilot_parent.user.dto.request.ChangePasswordRequest;
import careerpilot_parent.user.dto.request.DeactivateAccountRequest;
import careerpilot_parent.user.dto.request.UpdateProfileRequest;
import careerpilot_parent.user.dto.request.UpdateSocialLinksRequest;
import careerpilot_parent.user.dto.request.UploadProfilePictureRequest;
import careerpilot_parent.user.dto.response.UserProfileResponse;
import careerpilot_parent.user.dto.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void changePassword(ChangePasswordRequest request);

    UserResponse getCurrentUser();
    UserProfileResponse getCurrentUserProfile();
    UserProfileResponse getUserProfile(Long userId);

    UserProfileResponse updateProfile(UpdateProfileRequest request);
    UserProfileResponse updateCurrentUserSocialLinks(UpdateSocialLinksRequest request);
    UserProfileResponse updateSocialLinks( UpdateSocialLinksRequest request);
    UserProfileResponse updateCurrentUserProfile(UpdateProfileRequest request);
    void uploadProfilePicture(MultipartFile file);

    void deleteProfilePicture();

    void deactivateAccount( DeactivateAccountRequest request);
}