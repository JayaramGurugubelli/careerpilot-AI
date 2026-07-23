package careerpilot_parent.student.controller;
import careerpilot_parent.student.dto.request.CreateStudentRequest;
import careerpilot_parent.student.dto.request.UpdateStudentRequest;
import careerpilot_parent.student.dto.response.StudentResponse;
import careerpilot_parent.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody CreateStudentRequest request) {
        return ResponseEntity.ok(studentService.create(request));
    }

    @GetMapping("/me")
    public ResponseEntity<StudentResponse> getCurrentStudent() {
        return ResponseEntity.ok(studentService.getCurrentStudent());
    }

    @PutMapping("/me")
    public ResponseEntity<StudentResponse> update(@Valid @RequestBody UpdateStudentRequest request) {
        return ResponseEntity.ok(studentService.update(request));
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> delete() {
        studentService.delete();
        return ResponseEntity.ok("Student profile deleted successfully.");
    }
}