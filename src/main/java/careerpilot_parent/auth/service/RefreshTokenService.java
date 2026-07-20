package careerpilot_parent.auth.service;

import careerpilot_parent.auth.dto.response.RefreshTokenResponse;
import careerpilot_parent.auth.entity.RefreshToken;
import careerpilot_parent.user.entity.User;


public interface RefreshTokenService {


    RefreshToken createRefreshToken(User user);

    RefreshToken verifyExpiration(RefreshToken refreshToken);


    RefreshToken getByToken(String token);


    RefreshTokenResponse refreshAccessToken(String refreshToken);


    void revokeToken(String refreshToken);


    void revokeAllUserTokens(User user);

}