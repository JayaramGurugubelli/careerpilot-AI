package careerpilot_parent.user.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadProfilePictureRequest {
    private String profilePicture;
}
