package careerpilot_parent.student.service;

import careerpilot_parent.student.dto.request.CreateStudentRequest;
import careerpilot_parent.student.dto.request.UpdateStudentRequest;
import careerpilot_parent.student.dto.response.StudentResponse;

public interface StudentService {

    StudentResponse create(CreateStudentRequest request);

    StudentResponse getCurrentStudent();

    StudentResponse update(UpdateStudentRequest request);

    void delete();
}