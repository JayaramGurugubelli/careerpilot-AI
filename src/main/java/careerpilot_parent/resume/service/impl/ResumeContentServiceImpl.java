package careerpilot_parent.resume.service.impl;


import careerpilot_parent.common.exception.ResourceNotFoundException;
import careerpilot_parent.resume.dto.request.*;
import careerpilot_parent.resume.dto.response.ResumeContentResponse;
import careerpilot_parent.resume.entity.Resume;
import careerpilot_parent.resume.entity.ResumeContent;
import careerpilot_parent.resume.mapper.ResumeContentMapper;
import careerpilot_parent.resume.repository.ResumeContentRepository;
import careerpilot_parent.resume.repository.ResumeRepository;
import careerpilot_parent.resume.service.ResumeContentService;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ResumeContentServiceImpl implements ResumeContentService {



    private final ResumeRepository resumeRepository;

    private final ResumeContentRepository contentRepository;

    private final ResumeContentMapper contentMapper;



    @Override
    public ResumeContentResponse createContent(Long resumeId, CreateResumeContentRequest request){


        Resume resume = resumeRepository.findById(resumeId).orElseThrow(() -> new ResourceNotFoundException("Resume not found : "+resumeId));


        ResumeContent content =
                contentMapper.toEntity(request, resume);


        return contentMapper.toResponse(contentRepository.save(content));

    }



    @Override
    public ResumeContentResponse updateContent(Long resumeId, UpdateResumeContentRequest request){


        ResumeContent content = contentRepository.findByResumeId(resumeId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Resume content not found"
                                )
                        );


        contentMapper.updateEntity(content, request);


        return contentMapper.toResponse(contentRepository.save(content));

    }



    @Override
    @Transactional(readOnly = true)
    public ResumeContentResponse getContent(
            Long resumeId
    ){


        ResumeContent content =
                contentRepository.findByResumeId(resumeId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Resume content not found"
                                )
                        );


        return contentMapper.toResponse(content);

    }



    @Override
    public void deleteContent(
            Long resumeId
    ){


        ResumeContent content =
                contentRepository.findByResumeId(resumeId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Resume content not found"
                                )
                        );


        contentRepository.delete(content);

    }

}