package careerpilot_parent.ats.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtsAnalysisResult {

    private Double atsScore;

    private List<String> matchedSkills;

    private List<String> missingSkills;

    private List<String> extraSkills;

}