package careerpilot_parent.resume.service.impl;


import careerpilot_parent.common.exception.ResourceNotFoundException;
import careerpilot_parent.resume.dto.request.UpdateResumeUploadRequest;
import careerpilot_parent.resume.dto.response.ResumeUploadResponse;
import careerpilot_parent.resume.entity.ResumeUpload;
import careerpilot_parent.resume.mapper.ResumeUploadMapper;
import careerpilot_parent.resume.repository.ResumeUploadRepository;
import careerpilot_parent.resume.service.ResumeUploadService;
import careerpilot_parent.resume.service.FileStorageService;
import careerpilot_parent.security.util.SecurityUtils;
import careerpilot_parent.student.entity.Student;
import careerpilot_parent.student.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ResumeUploadServiceImpl implements ResumeUploadService {


    private final ResumeUploadRepository resumeUploadRepository;

    private final StudentRepository studentRepository;

    private final ResumeUploadMapper resumeUploadMapper;

    private final FileStorageService fileStorageService;

    private final SecurityUtils securityUtils;



    @Override
    public ResumeUploadResponse uploadResume(
            MultipartFile file
    ) {


        Student student = getCurrentStudent();


        String storedFileName =
                fileStorageService.storeFile(file);



        Integer version =
                resumeUploadRepository
                        .findByStudentIdOrderByVersionDesc(student.getId())
                        .stream()
                        .findFirst()
                        .map(resume -> resume.getVersion() + 1)
                        .orElse(1);



        ResumeUpload resumeUpload =
                resumeUploadMapper.toEntity(
                        student,
                        file.getOriginalFilename(),
                        storedFileName,
                        file.getContentType(),
                        file.getSize(),
                        storedFileName,
                        version
                );


        ResumeUpload savedResume =
                resumeUploadRepository.save(resumeUpload);



        return resumeUploadMapper.toResponse(savedResume);

    }




    @Override
    public ResumeUploadResponse replaceResume(
            Long uploadId,
            MultipartFile file
    ) {


        Student student = getCurrentStudent();


        ResumeUpload existingResume =
                resumeUploadRepository.findByIdAndStudentId(
                                uploadId,
                                student.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resume upload not found."
                                )
                        );



        fileStorageService.deleteFile(
                existingResume.getStoredFileName()
        );



        String storedFileName =
                fileStorageService.storeFile(file);



        existingResume.setOriginalFileName(
                file.getOriginalFilename()
        );


        existingResume.setStoredFileName(
                storedFileName
        );


        existingResume.setFileType(
                file.getContentType()
        );


        existingResume.setFileSize(
                file.getSize()
        );


        existingResume.setStoragePath(
                storedFileName
        );



        ResumeUpload updated =
                resumeUploadRepository.save(existingResume);



        return resumeUploadMapper.toResponse(updated);

    }





    @Override
    public ResumeUploadResponse updateResume(
            Long uploadId,
            UpdateResumeUploadRequest request
    ) {


        Student student = getCurrentStudent();



        ResumeUpload resumeUpload =
                resumeUploadRepository.findByIdAndStudentId(
                                uploadId,
                                student.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resume upload not found."
                                )
                        );



        resumeUploadMapper.updateEntity(
                resumeUpload,
                request.getActive()
        );



        ResumeUpload updated =
                resumeUploadRepository.save(resumeUpload);



        return resumeUploadMapper.toResponse(updated);

    }





    @Override
    @Transactional(readOnly = true)
    public ResumeUploadResponse getResumeById(
            Long uploadId
    ) {


        Student student = getCurrentStudent();



        ResumeUpload resumeUpload =
                resumeUploadRepository.findByIdAndStudentId(
                                uploadId,
                                student.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resume upload not found."
                                )
                        );



        return resumeUploadMapper.toResponse(
                resumeUpload
        );

    }





    @Override
    @Transactional(readOnly = true)
    public List<ResumeUploadResponse> getAllResumes() {


        Student student = getCurrentStudent();



        return resumeUploadRepository
                .findByStudentIdOrderByVersionDesc(
                        student.getId()
                )
                .stream()
                .map(resumeUploadMapper::toResponse)
                .toList();

    }





    @Override
    @Transactional(readOnly = true)
    public Resource downloadResume(
            Long uploadId
    ) {


        Student student = getCurrentStudent();



        ResumeUpload resumeUpload =
                resumeUploadRepository.findByIdAndStudentId(
                                uploadId,
                                student.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resume upload not found."
                                )
                        );



        return fileStorageService.loadFile(
                resumeUpload.getStoredFileName()
        );

    }





    @Override
    public void deleteResume(
            Long uploadId
    ) {


        Student student = getCurrentStudent();



        ResumeUpload resumeUpload =
                resumeUploadRepository.findByIdAndStudentId(
                                uploadId,
                                student.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resume upload not found."
                                )
                        );



        fileStorageService.deleteFile(
                resumeUpload.getStoredFileName()
        );



        resumeUploadRepository.delete(
                resumeUpload
        );

    }





    private Student getCurrentStudent() {


        Long userId =
                securityUtils.getCurrentUserId();



        return studentRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."
                        )
                );

    }
    private final ResumeUploadRepository repository;

    @Override
    public ResumeUpload getActiveResume(Long studentId) {

        return repository
                .findByStudentIdAndActiveTrue(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No active resume found"
                        ));
    }

}