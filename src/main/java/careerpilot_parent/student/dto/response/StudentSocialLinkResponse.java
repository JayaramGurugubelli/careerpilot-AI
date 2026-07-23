package careerpilot_parent.student.dto.response;

import careerpilot_parent.student.enums.SocialPlatform;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentSocialLinkResponse {

    private Long id;

    private SocialPlatform platform;

    private String url;

    private Integer displayOrder;

    private Boolean visible;

}