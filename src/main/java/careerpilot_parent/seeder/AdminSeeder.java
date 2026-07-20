package careerpilot_parent.seeder;

import careerpilot_parent.shared.enums.RoleName;
import careerpilot_parent.user.entity.Role;
import careerpilot_parent.user.entity.User;
import careerpilot_parent.user.entity.UserRole;
import careerpilot_parent.user.repository.RoleRepository;
import careerpilot_parent.user.repository.UserRepository;
import careerpilot_parent.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.existsByEmail("admin@careerpilot.com")) {
            return;
        }

        User admin = User.builder()
                .firstName("System")
                .lastName("Admin")
                .username("admin")
                .email("admin@careerpilot.com")
                .phoneNumber("9999999999")
                .password(passwordEncoder.encode("Admin@123"))
                .enabled(true)
                .build();

        User savedAdmin = userRepository.save(admin);

        Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                .orElseThrow();

        userRoleRepository.save(
                UserRole.builder()
                        .user(savedAdmin)
                        .role(adminRole)
                        .build()
        );
    }
}