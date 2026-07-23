package careerpilot_parent.student.dto.response;

import careerpilot_parent.student.enums.LanguageProficiency;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentLanguageResponse {

    private Long id;

    private String languageName;

    private LanguageProficiency proficiency;

}