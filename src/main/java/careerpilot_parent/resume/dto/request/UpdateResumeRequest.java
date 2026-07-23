package careerpilot_parent.resume.dto.request;

import careerpilot_parent.resume.enums.ResumeTemplate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateResumeRequest {


    @NotBlank
    private String resumeTitle;


    @NotNull
    private ResumeTemplate template;


    @NotNull
    private Boolean defaultResume;

}