package careerpilot_parent.auth.service;


import careerpilot_parent.auth.dto.request.LoginRequest;
import careerpilot_parent.auth.dto.request.RegisterRequest;
import careerpilot_parent.auth.dto.response.AuthResponse;
import careerpilot_parent.auth.dto.response.RegisterResponse;


public interface AuthService {


    RegisterResponse register(
            RegisterRequest request
    );


    AuthResponse login(
            LoginRequest request
    );


    void logout(
            String refreshToken
    );

}