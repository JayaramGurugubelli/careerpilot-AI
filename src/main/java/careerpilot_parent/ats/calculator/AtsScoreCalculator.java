package careerpilot_parent.ats.calculator;

import careerpilot_parent.ats.model.AtsAnalysisResult;
import careerpilot_parent.ats.model.ExtractedSkill;
import careerpilot_parent.ats.extractor.JobKeyword;

import java.util.List;

public interface AtsScoreCalculator {

    AtsAnalysisResult calculateScore(

            List<ExtractedSkill> resumeSkills,

            List<JobKeyword> jobKeywords

    );

}