package careerpilot_parent.auth.repository;

import careerpilot_parent.auth.entity.DeviceSession;
import careerpilot_parent.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceSessionRepository extends JpaRepository<DeviceSession, Long> {

    List<DeviceSession> findByUser(User user);

    Optional<DeviceSession> findByRefreshToken(String refreshToken);

    List<DeviceSession> findByUserAndExpiredFalse(User user);

    void deleteByUser(User user);
}