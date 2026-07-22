package careerpilot_parent.user.service.impl;
import careerpilot_parent.auth.dto.request.*;
import careerpilot_parent.auth.exception.PasswordMismatchException;
import careerpilot_parent.common.exception.ResourceNotFoundException;
import careerpilot_parent.common.exception.UserAlreadyExistsException;
import careerpilot_parent.common.exception.UserNotFoundException;
import careerpilot_parent.security.jwt.JwtService;
import careerpilot_parent.security.model.CustomUserDetails;
import careerpilot_parent.security.util.SecurityUtils;
import careerpilot_parent.shared.enums.AccountStatus;
import careerpilot_parent.shared.enums.RoleName;
import careerpilot_parent.user.dto.request.*;
import careerpilot_parent.auth.dto.response.LoginResponse;
import careerpilot_parent.auth.dto.response.RegisterResponse;
import careerpilot_parent.user.dto.response.UserProfileResponse;
import careerpilot_parent.user.dto.response.UserResponse;
import careerpilot_parent.user.entity.Role;
import careerpilot_parent.user.entity.User;
import careerpilot_parent.user.entity.UserProfile;
import careerpilot_parent.user.entity.UserRole;
import careerpilot_parent.user.mapper.UserMapper;
import careerpilot_parent.user.repository.RoleRepository;
import careerpilot_parent.user.repository.UserProfileRepository;
import careerpilot_parent.user.repository.UserRepository;
import careerpilot_parent.user.repository.UserRoleRepository;
import careerpilot_parent.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtils securityUtils;
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
//    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        Long userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));

        if (!passwordEncoder.matches(
                changePasswordRequest.getCurrentPassword(),
                user.getPassword())) {

            throw new PasswordMismatchException(
                    "Current password is incorrect.");
        }

        if (!changePasswordRequest.getNewPassword()
                .equals(changePasswordRequest.getConfirmPassword())) {

            throw new PasswordMismatchException(
                    "New password and confirm password do not match.");
        }

        user.setPassword(
                passwordEncoder.encode(changePasswordRequest.getNewPassword()));

        userRepository.save(user);
    }

    @Override
    public UserProfileResponse getUserProfile(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User profile not found."));

        return userMapper.toUserProfileResponse(profile);
    }

    @Override
    @Transactional
    public UserProfileResponse updateProfile(UpdateProfileRequest updateProfileRequest) {
        Long userId = securityUtils.getCurrentUserId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() ->
                            new UserNotFoundException("User not found."));

            UserProfile profile = userProfileRepository.findByUserId(userId)
                    .orElseThrow(() ->
                            new UserNotFoundException("Profile not found."));

            // Update User table
            userMapper.updateUser(user, updateProfileRequest);

            // Update UserProfile table
            userMapper.updateUserProfile(profile, updateProfileRequest);

            userRepository.save(user);
            userProfileRepository.save(profile);

            return userMapper.toUserProfileResponse(profile);
    }

    @Override
    @Transactional
    public UserProfileResponse updateSocialLinks(UpdateSocialLinksRequest updateSocialLinksRequest) {
        Long userId = securityUtils.getCurrentUserId();
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("Profile not found."));

        userMapper.updateSocialLinks(profile, updateSocialLinksRequest);

        userProfileRepository.save(profile);

        return userMapper.toUserProfileResponse(profile);
    }

    @Override
    @Transactional
    public void uploadProfilePicture(String file) {

        Long userId = securityUtils.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));

        user.setProfilePicture(file);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteProfilePicture() {

        Long userId = securityUtils.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));

        user.setProfilePicture(null);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deactivateAccount( DeactivateAccountRequest request) {
        Long userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new PasswordMismatchException(
                    "Invalid password.");
        }

        user.setEnabled(false);
        user.setAccountStatus(AccountStatus.DEACTIVATED);

        userRepository.save(user);
    }
    @Override
    public UserResponse getCurrentUser() {

        User user = securityUtils.getCurrentUser();

        return userMapper.toUserResponse(user);
    }
    @Override
    public UserProfileResponse getCurrentUserProfile() {

        Long userId = securityUtils.getCurrentUserId();

        UserProfile profile = userProfileRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("User profile not found."));

        return userMapper.toUserProfileResponse(profile);
    }
    @Override
    @Transactional
    public UserProfileResponse updateCurrentUserProfile(UpdateProfileRequest request) {

        Long userId = securityUtils.getCurrentUserId();

        User user = userRepository.findById(userId).orElseThrow(() ->
                        new UserNotFoundException("User not found."));

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserProfile newProfile = UserProfile.builder()
                            .user(user)
                            .build();

                    return userProfileRepository.save(newProfile);
                });

        userMapper.updateUser(user, request);
        userMapper.updateUserProfile(profile, request);

        userRepository.save(user);
        userProfileRepository.save(profile);

        return userMapper.toUserProfileResponse(profile);
    }
    @Override
    @Transactional
    public UserProfileResponse updateCurrentUserSocialLinks(UpdateSocialLinksRequest request) {

        Long userId = securityUtils.getCurrentUserId();

        UserProfile profile = userProfileRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("Profile not found."));

        userMapper.updateSocialLinks(profile, request);

        userProfileRepository.save(profile);

        return userMapper.toUserProfileResponse(profile);
    }
}
