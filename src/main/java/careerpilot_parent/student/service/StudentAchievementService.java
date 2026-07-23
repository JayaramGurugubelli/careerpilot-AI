package careerpilot_parent.student.service;


import careerpilot_parent.student.dto.request.CreateStudentAchievementRequest;
import careerpilot_parent.student.dto.request.UpdateStudentAchievementRequest;
import careerpilot_parent.student.dto.response.StudentAchievementResponse;

import java.util.List;


public interface StudentAchievementService {


    /**
     * Create Student Achievement
     */
    StudentAchievementResponse createAchievement(CreateStudentAchievementRequest request);



    /**
     * Update Student Achievement
     */
    StudentAchievementResponse updateAchievement(Long achievementId, UpdateStudentAchievementRequest request);



    /**
     * Get Achievement By Id
     */
    StudentAchievementResponse getAchievementById(Long achievementId);



    /**
     * Get All Achievements Of Logged In Student
     */
    List<StudentAchievementResponse> getAllAchievements();



    /**
     * Delete Achievement
     */
    void deleteAchievement(Long achievementId);

}