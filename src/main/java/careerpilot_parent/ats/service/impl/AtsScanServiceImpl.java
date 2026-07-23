package careerpilot_parent.ats.service.impl;


import careerpilot_parent.ats.dto.request.CreateAtsScanRequest;
import careerpilot_parent.ats.dto.response.AtsScanResponse;
import careerpilot_parent.ats.entity.AtsScanResult;
import careerpilot_parent.ats.mapper.AtsScanMapper;
import careerpilot_parent.ats.repository.AtsScanResultRepository;
import careerpilot_parent.ats.service.AtsScanService;

import careerpilot_parent.common.exception.ResourceNotFoundException;

import careerpilot_parent.resume.entity.Resume;
import careerpilot_parent.resume.repository.ResumeRepository;

import careerpilot_parent.security.util.SecurityUtils;

import careerpilot_parent.student.entity.Student;
import careerpilot_parent.student.repository.StudentRepository;


import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;



@Service
@RequiredArgsConstructor
@Transactional
public class AtsScanServiceImpl
        implements AtsScanService {


    private final AtsScanResultRepository atsScanRepository;


    private final ResumeRepository resumeRepository;


    private final StudentRepository studentRepository;


    private final AtsScanMapper atsScanMapper;


    private final SecurityUtils securityUtils;




    @Override
    public AtsScanResponse createScan(
            CreateAtsScanRequest request
    ){


        Long userId =
                securityUtils.getCurrentUserId();



        Student student =
                studentRepository
                        .findByUserId(userId)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Student not found"
                                        )
                        );



        Resume resume =
                resumeRepository
                        .findById(request.getResumeId())
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Resume not found"
                                        )
                        );



        AtsScanResult result =
                AtsScanResult.builder()

                        .student(student)

                        .resume(resume)

                        .jobTitle(
                                request.getJobTitle()
                        )

                        .companyName(
                                request.getCompanyName()
                        )

                        .jobDescription(
                                request.getJobDescription()
                        )

                        /*
                          Temporary ATS calculation
                          Replace with AI engine later
                         */
                        .atsScore(0)

                        .matchedSkills(
                                ""
                        )

                        .missingSkills(
                                ""
                        )

                        .suggestions(
                                "Resume analysis pending"
                        )

                        .build();



        AtsScanResult saved =
                atsScanRepository.save(result);



        return atsScanMapper.toResponse(saved);

    }





    @Override
    @Transactional(readOnly = true)
    public List<AtsScanResponse> getMyScans(){


        Long userId =
                securityUtils.getCurrentUserId();



        Student student =
                studentRepository
                        .findByUserId(userId)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Student not found"
                                        )
                        );



        return atsScanRepository
                .findByStudentId(student.getId())
                .stream()
                .map(atsScanMapper::toResponse)
                .toList();

    }





    @Override
    @Transactional(readOnly = true)
    public AtsScanResponse getScanById(
            Long scanId
    ){


        AtsScanResult result =
                atsScanRepository
                        .findById(scanId)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "ATS Scan not found"
                                        )
                        );


        return atsScanMapper.toResponse(result);

    }


}