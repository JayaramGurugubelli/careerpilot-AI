package careerpilot_parent.resume.repository;



import careerpilot_parent.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ResumeRepository extends JpaRepository<Resume, Long> {


    /**
     * Get all resumes of a student
     */
    List<Resume> findByStudentId(Long studentId);
    Optional<Resume> findByIdAndStudentId(Long resumeId, Long studentId);


    /**
     * Find default resume of student
     */
    Optional<Resume> findByStudentIdAndDefaultResumeTrue(Long studentId);

}
