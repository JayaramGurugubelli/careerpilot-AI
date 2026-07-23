package careerpilot_parent.student.dto.request;

import careerpilot_parent.student.enums.EmploymentType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStudentExperienceRequest {

    @NotBlank
    @Size(max = 150)
    private String companyName;

    @NotBlank
    @Size(max = 120)
    private String jobTitle;

    @NotNull
    private EmploymentType employmentType;

    @Size(max = 120)
    private String location;

    @NotNull
    private Boolean currentlyWorking;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 500)
    private String technologies;

    @Size(max = 2000)
    private String description;
}