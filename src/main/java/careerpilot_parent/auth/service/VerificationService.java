package careerpilot_parent.auth.service;


import careerpilot_parent.auth.entity.PasswordResetToken;
import careerpilot_parent.auth.entity.VerificationToken;
import careerpilot_parent.user.entity.User;


public interface VerificationService {
    VerificationToken getByToken(String token);
    void markAsUsed(VerificationToken token);

    void createVerificationToken(User user);


    void verifyEmail(String token);


    void resendVerificationToken(String email);

    void createPasswordResetToken(User user);
    PasswordResetToken getPasswordResetToken(String token);
    void markPasswordResetTokenAsUsed(PasswordResetToken token);
}