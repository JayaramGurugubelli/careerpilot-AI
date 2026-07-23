package careerpilot_parent.student.service.impl;

import careerpilot_parent.common.exception.DuplicateResourceException;
import careerpilot_parent.common.exception.ResourceNotFoundException;
import careerpilot_parent.security.util.SecurityUtils;
import careerpilot_parent.student.dto.request.CreateStudentSkillRequest;
import careerpilot_parent.student.dto.request.UpdateStudentSkillRequest;
import careerpilot_parent.student.dto.response.StudentSkillResponse;
import careerpilot_parent.student.entity.Student;
import careerpilot_parent.student.entity.StudentSkill;
import careerpilot_parent.student.mapper.StudentSkillMapper;
import careerpilot_parent.student.repository.StudentRepository;
import careerpilot_parent.student.repository.StudentSkillRepository;
import careerpilot_parent.student.service.StudentSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentSkillServiceImpl implements StudentSkillService {

    private final StudentRepository studentRepository;
    private final StudentSkillRepository skillRepository;
    private final StudentSkillMapper skillMapper;
    private final SecurityUtils securityUtils;

    /**
     * Returns currently logged-in student's profile.
     */
    private Student getCurrentStudent() {

        Long userId = securityUtils.getCurrentUserId();

        return studentRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student profile not found."));
    }

    @Override
    public StudentSkillResponse addSkill(CreateStudentSkillRequest request) {

        Student student = getCurrentStudent();

        if (skillRepository.existsByStudentIdAndSkillNameIgnoreCase(student.getId(), request.getSkillName())) {
            throw new DuplicateResourceException(
                    "Skill already exists."
            );
        }

        StudentSkill skill = skillMapper.toEntity(request, student);

        skill = skillRepository.save(skill);

        return skillMapper.toResponse(skill);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentSkillResponse> getSkills() {

        Student student = getCurrentStudent();

        return skillRepository.findByStudentId(student.getId())
                .stream()
                .map(skillMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentSkillResponse getSkillById(Long skillId) {

        Student student = getCurrentStudent();

        StudentSkill skill =
                skillRepository.findByIdAndStudentId(skillId, student.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Skill not found."
                                ));

        return skillMapper.toResponse(skill);
    }

    @Override
    public StudentSkillResponse updateSkill(Long skillId, UpdateStudentSkillRequest request) {

        Student student = getCurrentStudent();

        StudentSkill skill =
                skillRepository.findByIdAndStudentId(
                                skillId,
                                student.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Skill not found."
                                ));

        if (!skill.getSkillName().equalsIgnoreCase(request.getSkillName()) && skillRepository.existsByStudentIdAndSkillNameIgnoreCase(
                student.getId(),
                request.getSkillName())) {

            throw new DuplicateResourceException(
                    "Skill already exists."
            );
        }

        skillMapper.update(skill, request);

        skill = skillRepository.save(skill);

        return skillMapper.toResponse(skill);
    }

    @Override
    public void deleteSkill(Long skillId) {

        Student student = getCurrentStudent();

        StudentSkill skill =
                skillRepository.findByIdAndStudentId(
                                skillId,
                                student.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Skill not found."
                                ));

        skillRepository.delete(skill);
    }

}