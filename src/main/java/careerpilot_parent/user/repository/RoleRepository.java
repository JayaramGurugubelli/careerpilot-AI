package careerpilot_parent.user.repository;

import careerpilot_parent.shared.enums.RoleName;
import careerpilot_parent.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);

}
