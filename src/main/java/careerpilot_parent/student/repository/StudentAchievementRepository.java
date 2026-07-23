package careerpilot_parent.student.repository;


import careerpilot_parent.student.entity.StudentAchievements;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface StudentAchievementRepository extends JpaRepository<StudentAchievements, Long> {


    List<StudentAchievements> findByStudentId(Long studentId);
    Optional<StudentAchievements> findByIdAndStudentId(Long achievementId, Long studentId);

}