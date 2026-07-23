package careerpilot_parent.ats.repository;


import careerpilot_parent.ats.entity.AtsScanResult;

import careerpilot_parent.student.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AtsScanResultRepository
        extends JpaRepository<AtsScanResult, Long> {


    List<AtsScanResult> findByStudent(Student student);


    List<AtsScanResult> findByStudentId(Long studentId);


}