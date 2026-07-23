package careerpilot_parent.student.entity;

import careerpilot_parent.common.entity.BaseEntity;
import careerpilot_parent.student.enums.EmploymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "student_experiences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentExperience extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "student_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_student_experience_student")
    )
    private Student student;

    @NotBlank
    @Size(max = 150)
    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @NotBlank
    @Size(max = 120)
    @Column(name = "job_title", nullable = false, length = 120)
    private String jobTitle;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", nullable = false)
    private EmploymentType employmentType;

    @Size(max = 120)
    @Column(length = 120)
    private String location;

    @NotNull
    @Column(name = "currently_working", nullable = false)
    private Boolean currentlyWorking;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Size(max = 500)
    @Column(length = 500)
    private String technologies;

    @Size(max = 2000)
    @Column(length = 2000)
    private String description;
}