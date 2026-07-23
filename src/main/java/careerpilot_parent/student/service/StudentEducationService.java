package careerpilot_parent.student.service;

import careerpilot_parent.student.dto.request.StudentEducationRequest;
import careerpilot_parent.student.dto.response.StudentEducationResponse;

import java.util.List;

public interface StudentEducationService {
    StudentEducationResponse addEducation(StudentEducationRequest request);

    List<StudentEducationResponse> getEducations();

    StudentEducationResponse getEducationById(Long educationId);

    StudentEducationResponse updateEducation(Long educationId, StudentEducationRequest request);

    void deleteEducation(Long educationId);
}
