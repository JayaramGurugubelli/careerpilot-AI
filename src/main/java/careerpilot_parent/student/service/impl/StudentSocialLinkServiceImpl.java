package careerpilot_parent.student.service.impl;

import careerpilot_parent.common.exception.BadRequestException;
import careerpilot_parent.common.exception.ResourceNotFoundException;
import careerpilot_parent.security.util.SecurityUtils;
import careerpilot_parent.student.dto.request.CreateStudentSocialLinkRequest;
import careerpilot_parent.student.dto.request.UpdateStudentSocialLinkRequest;
import careerpilot_parent.student.dto.response.StudentSocialLinkResponse;
import careerpilot_parent.student.entity.Student;
import careerpilot_parent.student.entity.StudentSocialLink;
import careerpilot_parent.student.mapper.StudentSocialLinkMapper;
import careerpilot_parent.student.repository.StudentRepository;
import careerpilot_parent.student.repository.StudentSocialLinkRepository;
import careerpilot_parent.student.service.StudentSocialLinkService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentSocialLinkServiceImpl implements StudentSocialLinkService {

    private final StudentSocialLinkRepository socialLinkRepository;

    private final StudentRepository studentRepository;

    private final StudentSocialLinkMapper socialLinkMapper;

    private final SecurityUtils securityUtils;


    @Override
    public StudentSocialLinkResponse createSocialLink(
            CreateStudentSocialLinkRequest request
    ) {

        Long userId = securityUtils.getCurrentUserId();

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."
                        )
                );

        if (socialLinkRepository.existsByStudentIdAndPlatform(
                student.getId(),
                request.getPlatform()
        )) {

            throw new BadRequestException(
                    "Social link for this platform already exists."
            );
        }

        StudentSocialLink socialLink =
                socialLinkMapper.toEntity(
                        request,
                        student
                );

        StudentSocialLink savedSocialLink =
                socialLinkRepository.save(socialLink);

        return socialLinkMapper.toResponse(savedSocialLink);
    }


    @Override
    public StudentSocialLinkResponse updateSocialLink(
            Long socialLinkId,
            UpdateStudentSocialLinkRequest request
    ) {

        Long userId = securityUtils.getCurrentUserId();

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."
                        )
                );

        StudentSocialLink socialLink =
                socialLinkRepository.findByIdAndStudentId(
                                socialLinkId,
                                student.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student social link not found."
                                )
                        );

        socialLinkMapper.updateEntity(
                socialLink,
                request
        );

        StudentSocialLink updatedSocialLink =
                socialLinkRepository.save(socialLink);

        return socialLinkMapper.toResponse(updatedSocialLink);
    }


    @Override
    @Transactional(readOnly = true)
    public StudentSocialLinkResponse getSocialLinkById(
            Long socialLinkId
    ) {

        Long userId = securityUtils.getCurrentUserId();

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."
                        )
                );

        StudentSocialLink socialLink =
                socialLinkRepository.findByIdAndStudentId(
                                socialLinkId,
                                student.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student social link not found."
                                )
                        );

        return socialLinkMapper.toResponse(socialLink);
    }


    @Override
    @Transactional(readOnly = true)
    public List<StudentSocialLinkResponse> getAllSocialLinks() {

        Long userId = securityUtils.getCurrentUserId();

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."
                        )
                );

        return socialLinkRepository
                .findByStudentIdOrderByDisplayOrderAsc(
                        student.getId()
                )
                .stream()
                .map(socialLinkMapper::toResponse)
                .toList();
    }


    @Override
    public void deleteSocialLink(
            Long socialLinkId
    ) {

        Long userId = securityUtils.getCurrentUserId();

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."
                        )
                );

        StudentSocialLink socialLink =
                socialLinkRepository.findByIdAndStudentId(
                                socialLinkId,
                                student.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student social link not found."
                                )
                        );

        socialLinkRepository.delete(socialLink);
    }

}