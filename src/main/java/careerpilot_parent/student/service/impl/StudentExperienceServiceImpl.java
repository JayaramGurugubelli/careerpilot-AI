package careerpilot_parent.student.service.impl;


import careerpilot_parent.common.exception.ResourceNotFoundException;
import careerpilot_parent.security.util.SecurityUtils;
import careerpilot_parent.student.dto.request.CreateStudentExperienceRequest;
import careerpilot_parent.student.dto.request.UpdateStudentExperienceRequest;
import careerpilot_parent.student.dto.response.StudentExperienceResponse;
import careerpilot_parent.student.entity.Student;
import careerpilot_parent.student.entity.StudentExperience;
import careerpilot_parent.student.mapper.StudentExperienceMapper;
import careerpilot_parent.student.repository.StudentExperienceRepository;
import careerpilot_parent.student.repository.StudentRepository;
import careerpilot_parent.student.service.StudentExperienceService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class StudentExperienceServiceImpl implements StudentExperienceService {


    private final StudentExperienceRepository experienceRepository;

    private final StudentExperienceMapper experienceMapper;

    private final StudentRepository studentRepository;

    private final SecurityUtils securityUtils;

    @Override
    public StudentExperienceResponse createExperience(CreateStudentExperienceRequest request) {


        Long studentId = securityUtils.getCurrentUserId();





        Student student =
                studentRepository.findByUserId(studentId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Student profile not found for user id : " + studentId
                                )
                        );
        StudentExperience experience =
                experienceMapper.toEntity(
                        request,
                        student
                );


        StudentExperience savedExperience =
                experienceRepository.save(experience);


        return experienceMapper.toResponse(savedExperience);
    }



    @Override
    public StudentExperienceResponse updateExperience(Long experienceId, UpdateStudentExperienceRequest request) {


        StudentExperience experience =
                experienceRepository.findById(experienceId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Student experience not found with id : "
                                                + experienceId
                                )
                        );


        experienceMapper.updateEntity(
                experience,
                request
        );


        StudentExperience updatedExperience =
                experienceRepository.save(experience);


        return experienceMapper.toResponse(updatedExperience);
    }



    @Override
    @Transactional(readOnly = true)
    public StudentExperienceResponse getExperienceById(Long experienceId) {
        StudentExperience experience =
                experienceRepository.findById(experienceId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Student experience not found with id : "
                                                + experienceId
                                )
                        );


        return experienceMapper.toResponse(experience);
    }




    @Override
    @Transactional(readOnly = true)
    public List<StudentExperienceResponse> getAllExperiences() {
        return experienceRepository.findAll()
                .stream()
                .map(experienceMapper::toResponse)
                .toList();
    }




    @Override
    public void deleteExperience(Long experienceId) {
        StudentExperience experience =
                experienceRepository.findById(experienceId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Student experience not found with id : "
                                                + experienceId
                                )
                        );


        experienceRepository.delete(experience);

    }

}