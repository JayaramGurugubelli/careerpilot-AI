package careerpilot_parent.resume.service;

import careerpilot_parent.resume.dto.request.CreateResumeRequest;
import careerpilot_parent.resume.dto.request.UpdateResumeRequest;
import careerpilot_parent.resume.dto.response.ResumeResponse;

import java.util.List;


public interface ResumeService {


    ResumeResponse createResume(
            CreateResumeRequest request
    );


    ResumeResponse updateResume(
            Long resumeId,
            UpdateResumeRequest request
    );


    ResumeResponse getResumeById(
            Long resumeId
    );


    List<ResumeResponse> getAllResumes();


    void deleteResume(
            Long resumeId
    );

}
