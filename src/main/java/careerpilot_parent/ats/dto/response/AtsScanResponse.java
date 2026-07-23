package careerpilot_parent.ats.dto.response;


import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtsScanResponse {


    private Long id;


    private Long resumeId;


    private String jobTitle;


    private String companyName;


    private Integer atsScore;


    private String matchedSkills;


    private String missingSkills;


    private String suggestions;


    private LocalDateTime createdAt;


}