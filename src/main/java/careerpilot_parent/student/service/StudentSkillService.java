package careerpilot_parent.student.service;

import careerpilot_parent.student.dto.request.CreateStudentSkillRequest;
import careerpilot_parent.student.dto.request.UpdateStudentSkillRequest;
import careerpilot_parent.student.dto.response.StudentSkillResponse;

import java.util.List;

public interface StudentSkillService {

    StudentSkillResponse addSkill(CreateStudentSkillRequest request);

    List<StudentSkillResponse> getSkills();

    StudentSkillResponse getSkillById(Long skillId);

    StudentSkillResponse updateSkill(Long skillId, UpdateStudentSkillRequest request);

    void deleteSkill(Long skillId);

}