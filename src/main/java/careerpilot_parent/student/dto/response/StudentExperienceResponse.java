package careerpilot_parent.student.dto.response;

import careerpilot_parent.student.enums.EmploymentType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentExperienceResponse {

    private Long id;

    private String companyName;

    private String jobTitle;

    private EmploymentType employmentType;

    private String location;

    private Boolean currentlyWorking;

    private LocalDate startDate;

    private LocalDate endDate;

    private String technologies;

    private String description;
}