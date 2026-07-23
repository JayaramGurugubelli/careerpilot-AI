package careerpilot_parent.ats.extractor;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobKeyword {

    private String keyword;

    private Integer frequency;

}