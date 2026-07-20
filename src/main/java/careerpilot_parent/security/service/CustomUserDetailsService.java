package careerpilot_parent.security.service;

import careerpilot_parent.security.model.CustomUserDetails;
import careerpilot_parent.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import careerpilot_parent.user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository
                .findByEmailOrUsername(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(
                                "User not found with email or username : " + usernameOrEmail
                        ));

        return new CustomUserDetails(user);
    }
}
