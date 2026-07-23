package careerpilot_parent.resume.service;

import careerpilot_parent.resume.dto.request.CreateResumeSectionRequest;
import careerpilot_parent.resume.dto.request.UpdateResumeSectionRequest;
import careerpilot_parent.resume.dto.response.ResumeSectionResponse;

import java.util.List;

public interface ResumeSectionService {

    ResumeSectionResponse createSection(Long resumeId, CreateResumeSectionRequest request);

    ResumeSectionResponse updateSection(Long resumeId, Long sectionId, UpdateResumeSectionRequest request);

    ResumeSectionResponse getSection(Long resumeId, Long sectionId);

    List<ResumeSectionResponse> getAllSections(Long resumeId);

    void deleteSection(Long resumeId, Long sectionId);

}