package careerpilot_parent.auth.repository;

import careerpilot_parent.auth.entity.VerificationToken;
import careerpilot_parent.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    Optional<VerificationToken> findByUser(User user);

    void deleteByExpiryDateBefore(LocalDateTime dateTime);
    Optional<VerificationToken> findByTokenAndUsedFalse(String token);

}