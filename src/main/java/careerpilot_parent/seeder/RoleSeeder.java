package careerpilot_parent.seeder;

import careerpilot_parent.shared.enums.RoleName;
import careerpilot_parent.user.entity.Role;
import careerpilot_parent.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        for (RoleName roleName : RoleName.values()) {

            roleRepository.findByName(roleName)
                    .orElseGet(() ->
                            roleRepository.save(
                                    Role.builder()
                                            .name(roleName)
                                            .build()
                            )
                    );
        }

        System.out.println("Default roles seeded successfully.");
    }
}