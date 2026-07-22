package careerpilot_parent.auth.service.impl;


import careerpilot_parent.auth.dto.request.*;
import careerpilot_parent.auth.dto.response.AuthResponse;
import careerpilot_parent.auth.dto.response.LoginResponse;
import careerpilot_parent.auth.dto.response.RegisterResponse;
import careerpilot_parent.auth.entity.PasswordResetToken;
import careerpilot_parent.auth.entity.RefreshToken;
import careerpilot_parent.auth.entity.VerificationToken;
import careerpilot_parent.auth.exception.EmailAlreadyVerifiedException;
import careerpilot_parent.auth.exception.EmailNotVerifiedException;
import careerpilot_parent.auth.exception.PasswordMismatchException;
import careerpilot_parent.auth.exception.TokenExpiredException;
import careerpilot_parent.auth.service.AuthService;
import careerpilot_parent.auth.service.RefreshTokenService;
import careerpilot_parent.auth.service.VerificationService;

import careerpilot_parent.common.exception.InvalidCredentialsException;
import careerpilot_parent.common.exception.InvalidTokenException;
import careerpilot_parent.common.exception.UserAlreadyExistsException;
import careerpilot_parent.common.exception.UserNotFoundException;
import careerpilot_parent.security.jwt.JwtService;

import careerpilot_parent.user.entity.User;
import careerpilot_parent.user.entity.UserProfile;
import careerpilot_parent.user.mapper.UserMapper;
import careerpilot_parent.user.repository.UserProfileRepository;
import careerpilot_parent.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final VerificationService verificationService;

    private final UserMapper userMapper;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .emailVerified(false)
                .build();

        User savedUser = userRepository.save(user);
        UserProfile profile = UserProfile.builder()
                .user(savedUser)
                .build();

        userProfileRepository.save(profile);
        verificationService.createVerificationToken(savedUser);

        return RegisterResponse.builder()
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .emailVerificationRequired(true)
                .message("Registration successful. Please check your email to verify your account.")
                .build();
    }
    @Override
    public LoginResponse login(LoginRequest request) {
        User user;
        if(request.getUsernameOrEmail().contains("@")) {
            user = userRepository.findByEmail(request.getUsernameOrEmail()).orElseThrow(() -> new InvalidCredentialsException(
                                            "Invalid credentials"));
        }else {
            user = userRepository.findByUsername(request.getUsernameOrEmail()).orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));
        }if(!Boolean.TRUE.equals(user.getEmailVerified())) {
            throw new EmailNotVerifiedException("Please verify your email first");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String accessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return LoginResponse.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .accountStatus(user.getAccountStatus())
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTokenExpiration())
                .user(userMapper.toUserResponse(user))
                .build();
    }
    @Override
    public void logout(String refreshToken) {
        refreshTokenService.revokeToken(refreshToken);
    }
    @Override
    @Transactional
    public void verifyEmail(VerifyEmailRequest request) {

        VerificationToken verificationToken = verificationService.getByToken(request.getToken());
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Verification token has expired.");
        }
        if (Boolean.TRUE.equals(verificationToken.getUsed())) {
            throw new InvalidTokenException("Verification token has already been used.");
        }
        User user = verificationToken.getUser();
        if (Boolean.TRUE.equals(user.getEmailVerified())) {
            throw new EmailAlreadyVerifiedException("Email is already verified.");
        }
        user.setEmailVerified(true);
        userRepository.save(user);
        verificationService.markAsUsed(verificationToken);
    }
    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("No account found with this email."));
        verificationService.createPasswordResetToken(user);
    }
    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new PasswordMismatchException("New password and confirm password do not match.");
        }

        PasswordResetToken resetToken = verificationService.getPasswordResetToken(request.getResetToken());

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Password reset token has expired.");
        }

        if (Boolean.TRUE.equals(resetToken.getUsed())) {
            throw new InvalidTokenException("Password reset token has already been used.");
        }

        User user = resetToken.getUser();

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        verificationService.markPasswordResetTokenAsUsed(resetToken);
    }
}