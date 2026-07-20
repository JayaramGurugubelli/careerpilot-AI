package careerpilot_parent.auth.service;

public interface EmailService {

    void sendVerificationEmail(
            String to,
            String fullName,
            String verificationLink
    );

}