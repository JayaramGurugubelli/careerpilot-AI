package careerpilot_parent.auth.service;


import careerpilot_parent.user.entity.User;


public interface VerificationService {


    void createVerificationToken(User user);


    void verifyEmail(String token);


    void resendVerificationToken(String email);


}