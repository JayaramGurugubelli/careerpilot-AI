package careerpilot_parent.resume.mapper;


import careerpilot_parent.resume.dto.request.CreateResumeContentRequest;
import careerpilot_parent.resume.dto.request.UpdateResumeContentRequest;
import careerpilot_parent.resume.dto.response.ResumeContentResponse;
import careerpilot_parent.resume.entity.Resume;
import careerpilot_parent.resume.entity.ResumeContent;

import org.springframework.stereotype.Component;


@Component
public class ResumeContentMapper {


    public ResumeContent toEntity(CreateResumeContentRequest request, Resume resume) {

        return ResumeContent.builder()
                .resume(resume)
                .professionalSummary(request.getProfessionalSummary())
                .careerObjective(request.getCareerObjective())
                .additionalInformation(request.getAdditionalInformation())
                .linkedinUrl(request.getLinkedinUrl())
                .githubUrl(request.getGithubUrl())
                .portfolioUrl(request.getPortfolioUrl())
                .build();
    }



    public ResumeContentResponse toResponse(ResumeContent content) {

        return ResumeContentResponse.builder()
                .id(content.getId())
                .resumeId(content.getResume().getId())
                .professionalSummary(content.getProfessionalSummary())
                .careerObjective(content.getCareerObjective())
                .additionalInformation(content.getAdditionalInformation())
                .linkedinUrl(content.getLinkedinUrl())
                .githubUrl(content.getGithubUrl())
                .portfolioUrl(content.getPortfolioUrl())
                .build();
    }



    public void updateEntity(ResumeContent content, UpdateResumeContentRequest request){

        content.setProfessionalSummary(request.getProfessionalSummary());

        content.setCareerObjective(request.getCareerObjective());

        content.setAdditionalInformation(request.getAdditionalInformation());

        content.setLinkedinUrl(request.getLinkedinUrl());

        content.setGithubUrl(request.getGithubUrl());

        content.setPortfolioUrl(request.getPortfolioUrl());

    }

}