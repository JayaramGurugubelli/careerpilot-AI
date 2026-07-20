package careerpilot_parent.user.repository;

import careerpilot_parent.user.entity.Role;
import careerpilot_parent.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
