package careerpilot_parent.student.service.impl;

import careerpilot_parent.common.exception.BadRequestException;
import careerpilot_parent.common.exception.ResourceNotFoundException;
import careerpilot_parent.security.util.SecurityUtils;
import careerpilot_parent.student.dto.request.CreateStudentLanguageRequest;
import careerpilot_parent.student.dto.request.UpdateStudentLanguageRequest;
import careerpilot_parent.student.dto.response.StudentLanguageResponse;
import careerpilot_parent.student.entity.Student;
import careerpilot_parent.student.entity.StudentLanguage;
import careerpilot_parent.student.mapper.StudentLanguageMapper;
import careerpilot_parent.student.repository.StudentLanguageRepository;
import careerpilot_parent.student.repository.StudentRepository;
import careerpilot_parent.student.service.StudentLanguageService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentLanguageServiceImpl implements StudentLanguageService {

    private final StudentLanguageRepository languageRepository;
    private final StudentRepository studentRepository;
    private final StudentLanguageMapper languageMapper;
    private final SecurityUtils securityUtils;

    @Override
    public StudentLanguageResponse createLanguage(
            CreateStudentLanguageRequest request
    ) {

        Long userId = securityUtils.getCurrentUserId();

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."
                        )
                );

        if (languageRepository.existsByStudentIdAndLanguageNameIgnoreCase(
                student.getId(),
                request.getLanguageName()
        )) {

            throw new BadRequestException(
                    "Language already exists."
            );
        }

        StudentLanguage language =
                languageMapper.toEntity(
                        request,
                        student
                );

        StudentLanguage saved =
                languageRepository.save(language);

        return languageMapper.toResponse(saved);
    }


    @Override
    public StudentLanguageResponse updateLanguage(
            Long languageId,
            UpdateStudentLanguageRequest request
    ) {

        Long userId = securityUtils.getCurrentUserId();

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."
                        )
                );

        StudentLanguage language =
                languageRepository.findByIdAndStudentId(
                        languageId,
                        student.getId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Language not found."
                        )
                );

        languageMapper.updateEntity(
                language,
                request
        );

        StudentLanguage updated =
                languageRepository.save(language);

        return languageMapper.toResponse(updated);
    }


    @Override
    @Transactional(readOnly = true)
    public StudentLanguageResponse getLanguageById(
            Long languageId
    ) {

        Long userId = securityUtils.getCurrentUserId();

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."
                        )
                );

        StudentLanguage language =
                languageRepository.findByIdAndStudentId(
                        languageId,
                        student.getId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Language not found."
                        )
                );

        return languageMapper.toResponse(language);
    }


    @Override
    @Transactional(readOnly = true)
    public List<StudentLanguageResponse> getAllLanguages() {

        Long userId = securityUtils.getCurrentUserId();

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."
                        )
                );

        return languageRepository
                .findByStudentIdOrderByLanguageNameAsc(student.getId())
                .stream()
                .map(languageMapper::toResponse)
                .toList();
    }


    @Override
    public void deleteLanguage(
            Long languageId
    ) {

        Long userId = securityUtils.getCurrentUserId();

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."
                        )
                );

        StudentLanguage language =
                languageRepository.findByIdAndStudentId(
                        languageId,
                        student.getId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Language not found."
                        )
                );

        languageRepository.delete(language);
    }

}