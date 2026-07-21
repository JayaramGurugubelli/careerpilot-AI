package careerpilot_parent.auth.controller;


import careerpilot_parent.auth.dto.request.*;
import careerpilot_parent.auth.dto.response.AuthResponse;
import careerpilot_parent.auth.dto.response.LoginResponse;
import careerpilot_parent.auth.dto.response.RefreshTokenResponse;
import careerpilot_parent.auth.dto.response.RegisterResponse;

import careerpilot_parent.auth.service.AuthService;
import careerpilot_parent.auth.service.PasswordResetService;
import careerpilot_parent.auth.service.RefreshTokenService;
import careerpilot_parent.auth.service.VerificationService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {



    private final AuthService authService;

    private final VerificationService verificationService;

    private final PasswordResetService passwordResetService;

    private final RefreshTokenService refreshTokenService;



    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {


        RegisterResponse response =
                authService.register(request);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }





    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                authService.login(request)
        );

    }





    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @Valid @RequestBody LogoutRequest request
    ) {


        authService.logout(
                request.getRefreshToken()
        );


        return ResponseEntity.ok(
                "Logout successful"
        );

    }





    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(
            @Valid @RequestBody VerifyEmailRequest request
    ) {


        verificationService.verifyEmail(
                request.getToken()
        );


        return ResponseEntity.ok(
                "Email verified successfully"
        );

    }





    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {


        RefreshTokenResponse response =
                refreshTokenService.refreshAccessToken(
                        request.getRefreshToken()
                );


        return ResponseEntity.ok(response);

    }





    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request
    ) {


        passwordResetService.createResetToken(
                request.getEmail()
        );


        return ResponseEntity.ok(
                "Password reset link sent successfully"
        );

    }





    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {


        if(!request.getNewPassword()
                .equals(request.getConfirmPassword())) {


            throw new RuntimeException(
                    "Passwords do not match"
            );

        }



        passwordResetService.resetPassword(

                request.getResetToken(),

                request.getNewPassword()

        );


        return ResponseEntity.ok(
                "Password reset successfully"
        );

    }
    @PostMapping("/resend-verification")
    public ResponseEntity<String> resendVerification(
            @Valid @RequestBody ResendVerificationRequest request) {

        verificationService.resendVerificationToken(request.getEmail());

        return ResponseEntity.ok("Verification email sent successfully.");
    }
    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(
            @RequestParam String token) {

        verificationService.verifyEmail(token);

        return ResponseEntity.ok("Email verified successfully.");
    }
}