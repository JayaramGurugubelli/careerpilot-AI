package careerpilot_parent.auth.dto.request;
import careerpilot_parent.common.constants.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForgotPasswordRequest {
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    @Size(max = ValidationConstants.EMAIL_MAX)
    private String email;
}
