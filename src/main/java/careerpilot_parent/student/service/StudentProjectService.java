package careerpilot_parent.student.service;

import careerpilot_parent.student.dto.request.CreateStudentProjectRequest;
import careerpilot_parent.student.dto.request.UpdateStudentProjectRequest;
import careerpilot_parent.student.dto.response.StudentProjectResponse;

import java.util.List;

public interface StudentProjectService {

    /**
     * Create Project
     */
    StudentProjectResponse addProject(CreateStudentProjectRequest request);

    /**
     * Get All Projects
     */
    List<StudentProjectResponse> getProjects();

    /**
     * Get Project By Id
     */
    StudentProjectResponse getProjectById(Long projectId);

    /**
     * Update Project
     */
    StudentProjectResponse updateProject(Long projectId, UpdateStudentProjectRequest request);

    /**
     * Delete Project
     */
    void deleteProject(Long projectId);

}