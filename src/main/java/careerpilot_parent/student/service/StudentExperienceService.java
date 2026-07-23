package careerpilot_parent.student.service;

import careerpilot_parent.student.dto.request.CreateStudentExperienceRequest;
import careerpilot_parent.student.dto.request.UpdateStudentExperienceRequest;
import careerpilot_parent.student.dto.response.StudentExperienceResponse;

import java.util.List;

public interface StudentExperienceService {

    StudentExperienceResponse createExperience(CreateStudentExperienceRequest request);

    StudentExperienceResponse updateExperience(Long experienceId, UpdateStudentExperienceRequest request);

    StudentExperienceResponse getExperienceById(Long experienceId);

    List<StudentExperienceResponse> getAllExperiences();

    void deleteExperience(Long experienceId);
}