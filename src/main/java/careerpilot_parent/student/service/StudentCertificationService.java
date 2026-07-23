package careerpilot_parent.student.service;


import careerpilot_parent.student.dto.request.CreateStudentCertificationRequest;
import careerpilot_parent.student.dto.request.UpdateStudentCertificationRequest;
import careerpilot_parent.student.dto.response.StudentCertificationResponse;

import java.util.List;


public interface StudentCertificationService {


    /**
     * Create Student Certification
     */
    StudentCertificationResponse createCertification(CreateStudentCertificationRequest request);



    /**
     * Update Student Certification
     */
    StudentCertificationResponse updateCertification(Long certificationId, UpdateStudentCertificationRequest request);



    /**
     * Get Certification By Id
     */
    StudentCertificationResponse getCertificationById(Long certificationId);



    /**
     * Get All Certifications Of Logged In Student
     */
    List<StudentCertificationResponse> getAllCertifications();



    /**
     * Delete Certification
     */
    void deleteCertification(Long certificationId);

}