package careerpilot_parent.user.service.impl;
import careerpilot_parent.auth.dto.request.*;
import careerpilot_parent.auth.exception.PasswordMismatchException;
import careerpilot_parent.common.exception.ResourceNotFoundException;
import careerpilot_parent.common.exception.UserAlreadyExistsException;
import careerpilot_parent.common.exception.UserNotFoundException;
import careerpilot_parent.security.jwt.JwtService;
import careerpilot_parent.security.model.CustomUserDetails;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
//    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Transactional
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new UserAlreadyExistsException("Username already exists.");
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new UserAlreadyExistsException("Email already exists.");
        }
        if(userRepository.existsByPhoneNumber(registerRequest.getPhoneNumber())){
            throw new UserAlreadyExistsException("Phone number already exists.");
        }
        User user = userMapper.toEntity(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User savedUser = userRepository.save(user);
        Role studentRole=roleRepository.findByName(RoleName.STUDENT).orElseThrow(
                ()-> new ResourceNotFoundException("Default role STUDENT not found."));
        UserRole userRole = UserRole.builder()
                .user(savedUser)
                .role(studentRole)
                .build();

        userRoleRepository.save(userRole);
        UserProfile profile = UserProfile.builder()
                .user(savedUser)
                .build();

        userProfileRepository.save(profile);
        return RegisterResponse.builder()
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .emailVerificationRequired(true)
                .message("Registration successful. Please verify your email.")
                .build();

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        String accessToken = jwtService.generateAccessToken(userDetails);

        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .user(userMapper.toUserResponse(user))
                .build();
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {

    }

    @Override
    public void verifyEmail(VerifyEmailRequest verifyEmailRequest) {

    }

    @Override
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {

    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {

    }

    @Override
    public void changePassword(Long userId,ChangePasswordRequest changePasswordRequest) {
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
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new UserNotFoundException("User not found with id : " + userId));
        return userMapper.toUserResponse(user);
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
    public UserProfileResponse updateProfile(Long userId, UpdateProfileRequest updateProfileRequest) {
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
    public UserProfileResponse updateSocialLinks(Long userId, UpdateSocialLinksRequest updateSocialLinksRequest) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("Profile not found."));

        userMapper.updateSocialLinks(profile, updateSocialLinksRequest);

        userProfileRepository.save(profile);

        return userMapper.toUserProfileResponse(profile);
    }

    @Override
    public void uploadProfilePicture(Long userId, UploadProfilePictureRequest request) {

    }

    @Override
    public void deleteProfilePicture(Long userId) {

    }

    @Override
    @Transactional
    public void deactivateAccount(Long userId, DeactivateAccountRequest request) {
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
}
