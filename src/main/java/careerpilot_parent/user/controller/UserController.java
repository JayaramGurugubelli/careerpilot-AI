package careerpilot_parent.user.controller;
import careerpilot_parent.user.dto.request.ChangePasswordRequest;
import careerpilot_parent.user.dto.request.DeactivateAccountRequest;
import careerpilot_parent.user.dto.request.UpdateProfileRequest;
import careerpilot_parent.user.dto.request.UpdateSocialLinksRequest;
import careerpilot_parent.user.dto.request.UploadProfilePictureRequest;
import careerpilot_parent.user.dto.response.UserProfileResponse;
import careerpilot_parent.user.dto.response.UserResponse;
import careerpilot_parent.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Logged-in user details
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            @AuthenticationPrincipal(expression = "user.id") Long userId) {

        return ResponseEntity.ok(
                userService.getUserById(userId)
        );
    }

    /**
     * User profile
     */
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(
            @AuthenticationPrincipal(expression = "user.id") Long userId) {

        return ResponseEntity.ok(
                userService.getUserProfile(userId)
        );
    }

    /**
     * Update profile
     */
    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @AuthenticationPrincipal(expression = "user.id") Long userId,
            @Valid @RequestBody UpdateProfileRequest request) {

        return ResponseEntity.ok(
                userService.updateProfile(userId, request)
        );
    }

    /**
     * Update social links
     */
    @PutMapping("/social-links")
    public ResponseEntity<UserProfileResponse> updateSocialLinks(
            @AuthenticationPrincipal(expression = "user.id") Long userId,
            @Valid @RequestBody UpdateSocialLinksRequest request) {

        return ResponseEntity.ok(
                userService.updateSocialLinks(userId, request)
        );
    }

    /**
     * Upload profile picture
     */
    @PostMapping("/profile-picture")
    public ResponseEntity<String> uploadProfilePicture(
            @AuthenticationPrincipal(expression = "user.id") Long userId,
            @Valid @RequestBody UploadProfilePictureRequest request) {

        userService.uploadProfilePicture(userId, request);

        return ResponseEntity.ok("Profile picture uploaded successfully.");
    }

    /**
     * Delete profile picture
     */
    @DeleteMapping("/profile-picture")
    public ResponseEntity<String> deleteProfilePicture(
            @AuthenticationPrincipal(expression = "user.id") Long userId) {

        userService.deleteProfilePicture(userId);

        return ResponseEntity.ok("Profile picture deleted successfully.");
    }

    /**
     * Change password
     */
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @AuthenticationPrincipal(expression = "user.id") Long userId, @Valid @RequestBody ChangePasswordRequest request) {

        userService.changePassword(userId, request);

        return ResponseEntity.ok("Password changed successfully.");
    }

    /**
     * Deactivate account
     */
    @PutMapping("/deactivate")
    public ResponseEntity<String> deactivateAccount(
            @AuthenticationPrincipal(expression = "user.id") Long userId,
            @Valid @RequestBody DeactivateAccountRequest request) {

        userService.deactivateAccount(userId, request);

        return ResponseEntity.ok("Account deactivated successfully.");
    }
}