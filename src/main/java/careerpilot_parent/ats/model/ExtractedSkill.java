package careerpilot_parent.ats.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtractedSkill {

    private String skill;

    private Integer frequency;

}