package careerpilot_parent.ats.entity;


import careerpilot_parent.resume.entity.Resume;
import careerpilot_parent.student.entity.Student;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "ats_scan_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtsScanResult {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    /*
        Student who performed ATS scan
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "student_id",
            nullable = false
    )
    private Student student;



    /*
        Resume which is analyzed
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "resume_id",
            nullable = false
    )
    private Resume resume;



    /*
        Target job information
     */
    @Column(nullable = false)
    private String jobTitle;



    @Column(nullable = false)
    private String companyName;



    @Column(
            columnDefinition = "TEXT",
            nullable = false
    )
    private String jobDescription;



    /*
        ATS Result
     */
    @Column(nullable = false)
    private Integer atsScore;



    @Column(
            columnDefinition = "TEXT"
    )
    private String matchedSkills;



    @Column(
            columnDefinition = "TEXT"
    )
    private String missingSkills;



    @Column(
            columnDefinition = "TEXT"
    )
    private String suggestions;



    private LocalDateTime createdAt;



    @PrePersist
    public void onCreate(){

        createdAt = LocalDateTime.now();

    }

}