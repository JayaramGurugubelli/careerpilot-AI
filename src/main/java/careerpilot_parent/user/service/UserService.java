package careerpilot_parent.user.service;

import careerpilot_parent.auth.dto.request.*;
import careerpilot_parent.user.dto.request.*;
import careerpilot_parent.auth.dto.response.LoginResponse;
import careerpilot_parent.auth.dto.response.RegisterResponse;
import careerpilot_parent.user.dto.response.UserProfileResponse;
import careerpilot_parent.user.dto.response.UserResponse;

public interface UserService {
    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);

    void logout(LogoutRequest logoutRequest);

    void verifyEmail(VerifyEmailRequest verifyEmailRequest);

    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

    void changePassword(Long userId,ChangePasswordRequest changePasswordRequest);

    UserResponse getUserById(Long userId);

    UserProfileResponse getUserProfile(Long userId);

    UserProfileResponse updateProfile(Long userId,UpdateProfileRequest updateProfileRequest);

    UserProfileResponse updateSocialLinks(Long userId,UpdateSocialLinksRequest updateSocialLinksRequest);

    void uploadProfilePicture(Long userId, UploadProfilePictureRequest request);

    void deleteProfilePicture(Long userId);

    void deactivateAccount(Long userId, DeactivateAccountRequest request);

}
