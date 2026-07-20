package careerpilot_parent.auth.repository;

import careerpilot_parent.auth.entity.LoginHistory;
import careerpilot_parent.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    List<LoginHistory> findByUser(User user);

    List<LoginHistory> findTop20ByUserOrderByCreatedAtDesc(User user);
}