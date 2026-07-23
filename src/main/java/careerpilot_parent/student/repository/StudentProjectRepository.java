package careerpilot_parent.student.repository;

import careerpilot_parent.student.entity.StudentProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentProjectRepository extends JpaRepository<StudentProject, Long> {

    List<StudentProject> findByStudentIdOrderByStartDateDesc(Long studentId);

    Optional<StudentProject> findByIdAndStudentId(Long projectId, Long studentId);

    boolean existsByStudentIdAndProjectTitleIgnoreCase(Long studentId, String projectTitle);

}