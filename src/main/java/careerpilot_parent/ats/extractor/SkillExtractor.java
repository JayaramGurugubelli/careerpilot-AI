package careerpilot_parent.ats.extractor;

import careerpilot_parent.ats.model.ExtractedSkill;
import careerpilot_parent.ats.model.ExtractedSkill;

import java.util.List;

public interface SkillExtractor {

    List<ExtractedSkill> extractSkills(
            String resumeText
    );

}