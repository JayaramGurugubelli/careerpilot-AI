package careerpilot_parent.user.dto.response;

import careerpilot_parent.shared.enums.AccountStatus;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String phoneNumber;

    private AccountStatus accountStatus;

    private Boolean emailVerified;

    private Boolean mobileVerified;

    private Boolean enabled;

    private String profilePicture;

    private String headline;

    private String bio;

}
