package careerpilot_parent.auth.service.impl;


import careerpilot_parent.auth.dto.request.LoginRequest;
import careerpilot_parent.auth.dto.request.RegisterRequest;
import careerpilot_parent.auth.dto.response.AuthResponse;
import careerpilot_parent.auth.dto.response.RegisterResponse;
import careerpilot_parent.auth.entity.RefreshToken;
import careerpilot_parent.auth.exception.EmailNotVerifiedException;
import careerpilot_parent.auth.service.AuthService;
import careerpilot_parent.auth.service.RefreshTokenService;
import careerpilot_parent.auth.service.VerificationService;

import careerpilot_parent.common.exception.InvalidCredentialsException;
import careerpilot_parent.common.exception.UserAlreadyExistsException;
import careerpilot_parent.security.jwt.JwtService;

import careerpilot_parent.user.entity.User;
import careerpilot_parent.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final VerificationService verificationService;



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

        verificationService.createVerificationToken(savedUser);

        return RegisterResponse.builder()
                .message("Registration successful. Please check your email to verify your account.")
                .build();
    }
    @Override
    public AuthResponse login(
            LoginRequest request
    ) {


        User user;



        if(request.getUsernameOrEmail()
                .contains("@")) {


            user =
                    userRepository.findByEmail(
                                    request.getUsernameOrEmail()
                            )

                            .orElseThrow(
                                    () -> new InvalidCredentialsException(
                                            "Invalid credentials"
                                    )
                            );


        }
        else {


            user =
                    userRepository.findByUsername(
                                    request.getUsernameOrEmail()
                            )

                            .orElseThrow(
                                    () -> new InvalidCredentialsException(
                                            "Invalid credentials"
                                    )
                            );

        }




        if(!Boolean.TRUE.equals(
                user.getEmailVerified()
        )) {

            throw new EmailNotVerifiedException(
                    "Please verify your email first"
            );
        }





        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        user.getUsername(),

                        request.getPassword()

                )

        );





        UserDetails userDetails =
                userDetailsService
                        .loadUserByUsername(
                                user.getUsername()
                        );





        String accessToken =
                jwtService.generateAccessToken(
                        userDetails
                );





        RefreshToken refreshToken =
                refreshTokenService
                        .createRefreshToken(user);





        return AuthResponse.builder()

                .accessToken(accessToken)

                .refreshToken(
                        refreshToken.getToken()
                )

                .tokenType("Bearer")

                .build();

    }





    @Override
    public void logout(
            String refreshToken
    ) {


        refreshTokenService
                .revokeToken(refreshToken);

    }

}