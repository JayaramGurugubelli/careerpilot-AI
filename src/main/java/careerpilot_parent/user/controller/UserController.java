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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Logged-in user details
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @GetMapping("/me/profile")
    public ResponseEntity<UserProfileResponse> getCurrentUserProfile() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }
    /**
     * Update profile
     */
    @PutMapping("/me/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateCurrentUserProfile(request));
    }

    /**
     * Update social links
     */
    @PutMapping("/me/social-links")
    public ResponseEntity<UserProfileResponse> updateSocialLinks(@AuthenticationPrincipal(expression = "user.id") Long userId, @Valid @RequestBody UpdateSocialLinksRequest request) {
        return ResponseEntity.ok(
                userService.updateCurrentUserSocialLinks( request)
        );
    }

    /**
     * Upload profile picture
     */
    @PostMapping(value = "/me/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("file") String file) {

        userService.uploadProfilePicture(file);

        return ResponseEntity.ok("Profile picture uploaded successfully.");
    }

    /**
     * Delete profile picture
     */
    @DeleteMapping("/me/profile-picture")
    public ResponseEntity<String> deleteProfilePicture() {

        userService.deleteProfilePicture();

        return ResponseEntity.ok("Profile picture deleted successfully.");
    }

    /**
     * Change password
     */
    @PutMapping("/me/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest request) {

        userService.changePassword(request);

        return ResponseEntity.ok("Password changed successfully.");
    }

    /**
     * Deactivate account
     */
    @PutMapping("/me/deactivate")
    public ResponseEntity<String> deactivateAccount(@Valid @RequestBody DeactivateAccountRequest request) {

        userService.deactivateAccount(request);

        return ResponseEntity.ok("Account deactivated successfully.");
    }
}